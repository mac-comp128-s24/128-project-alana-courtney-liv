import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

/**
 * Needlessly complex implementation of the hit game Rock, Paper, Scissors
 * @author Olive Pilling Chappelear, Courtney Brown, Alana Nadolski
 */
public class RPS {
    private final double WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;
    private final GamePieceComparator gpc = new GamePieceComparator();
    private final Random r = new Random();

    private CanvasWindow canvas;
    private GraphicsGroup pieceGroup;
    private HashMap<GamePiece.PieceType, Integer> teamCounts;
    private ArrayList<GamePiece> pieces;
    private int pieceCount;
    private UI ui;
    private boolean running;
    @SuppressWarnings("unchecked")
    private ArrayList<GamePiece>[][] buckets = new ArrayList[3][3];

    public RPS() {
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].length; j++) {
                buckets[i][j] = new ArrayList<GamePiece>();
            }
        }
        canvas = new CanvasWindow("Rock Paper Scissors", (int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
        pieceGroup = new GraphicsGroup();
        canvas.add(pieceGroup);
        ui = new UI(.2 * WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.add(ui, 0, 0);
        teamCounts = ui.getTeamCounts();
        pieces = new ArrayList<GamePiece>();
        addPieces();
        running = false;
        setStartButton();
        setResetButton();
        setupInputs();
    }

    /**
     * Creates and randomly arranges game pieces based on team counts
     */
    public void addPieces() {
        pieceCount = teamCounts.get(GamePiece.PieceType.ROCK) + teamCounts.get(GamePiece.PieceType.PAPER) + teamCounts.get(GamePiece.PieceType.SCISSORS);
        double scale = WINDOW_HEIGHT / (Math.log(pieceCount) / Math.log(1.2));
        
        double minX = ui.getX() + ui.getWidth() + scale;
        double maxX = WINDOW_WIDTH - scale;
        double minY = scale;
        double maxY = WINDOW_HEIGHT - scale;

        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.ROCK); i++) {
            GamePiece tempRock = new GamePiece(GamePiece.PieceType.ROCK);
            pieceGroup.add(tempRock, r.nextDouble(minX, maxX), r.nextDouble(minY, maxY));
            tempRock.setMaxHeight(scale);
            tempRock.setRadius(scale/2);
            pieces.add(tempRock);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.PAPER); i++) {
            GamePiece tempPaper = new GamePiece(GamePiece.PieceType.PAPER);
            pieceGroup.add(tempPaper, r.nextDouble(minX, maxX), r.nextDouble(minY, maxY));
            tempPaper.setMaxHeight(scale);
            tempPaper.setRadius(scale/2);
            pieces.add(tempPaper);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.SCISSORS); i++) {
            GamePiece tempScissors = new GamePiece(GamePiece.PieceType.SCISSORS);
            pieceGroup.add(tempScissors, r.nextDouble(minX, maxX), r.nextDouble(minY, maxY));
            tempScissors.setMaxHeight(scale);
            tempScissors.setRadius(scale/2);
            pieces.add(tempScissors);
        }
    }

    private void setStartButton() {
        ui.startButton.onClick(() -> {
            running = ui.toggleStartButton(running);
            setStartButton();
        });
    }

    private void setResetButton() {
        ui.resetButton.onClick(() -> {
            ui.resetTeamCounts();
            reset();
        });
    }

    private void setupInputs() {
        ui.rockInput.onChange(e -> {
            if (e.length() > 1) {
                if (e.charAt(e.length()-1) == ' ') {
                reset();
                }
            }
        });
        ui.paperInput.onChange(e -> {
            if (e.length() > 1) {
                if (e.charAt(e.length()-1) == ' ') {
                reset();
                }
            }
        });
        ui.scissorsInput.onChange(e -> {
            if (e.length() > 1) {
                if (e.charAt(e.length()-1) == ' ') {
                reset();
                }
            }
        });
    }

    private void run() {
        canvas.animate(() -> {
            if (running) {
                moveAll();
                handleCollisions();
                ui.updateTeamCounts(teamCounts);
                checkWinner();
            }
        });
    }

    /**
     * Restarts the game with new team counts
     */
    public void reset() {
        pieceGroup.removeAll();
        pieces.clear();
        running = false;
        teamCounts = ui.getTeamCounts();
        addPieces();
        setStartButton();
    }

    /**
     * Updates position of all game pieces
     */
    private void moveAll() {
        for (GamePiece piece : pieces) {
            Point centerPoint = new Point(r.nextDouble(WINDOW_WIDTH / 10, WINDOW_WIDTH), WINDOW_HEIGHT / 2);
            piece.updatePosition(centerPoint, 5);
            if (outOfBounds(piece)) {
                piece.switchDirection();
                piece.updatePosition(centerPoint, 10);
                
            }
            updateBucket(piece);
        }
    }

    private void updateBucket(GamePiece piece) {
        if (piece.getX() + piece.getWidth() < WINDOW_WIDTH/3 && piece.getY() + piece.getHeight() < WINDOW_HEIGHT/3) {
            if (!buckets[0][0].contains(piece)) {
                buckets[0][0].add(piece);
            }
        } else if (buckets[0][0].contains(piece)) {
            buckets[0][0].remove(piece);
        }

        if (piece.getX() > WINDOW_WIDTH/3 && piece.getX() + piece.getWidth() < 2*WINDOW_WIDTH/3 && piece.getY() + piece.getHeight() < WINDOW_HEIGHT/3) {
            if (!buckets[0][1].contains(piece)) {
                buckets[0][1].add(piece);
            }
        } else if (buckets[0][1].contains(piece)) {
            buckets[0][1].remove(piece);
        }

        if (piece.getX() > 2*WINDOW_WIDTH/3 && piece.getY() + piece.getHeight() < WINDOW_HEIGHT/3) {
            if (!buckets[0][2].contains(piece)) {
                buckets[0][2].add(piece);
            }
        } else if (buckets[0][2].contains(piece)) {
            buckets[0][2].remove(piece);
        }

        if (piece.getX() + piece.getWidth() < WINDOW_WIDTH/3 && piece.getY() > WINDOW_HEIGHT/3 && piece.getY() + piece.getHeight() < 2*WINDOW_HEIGHT/3) {
            if (!buckets[1][0].contains(piece)) {
                buckets[1][0].add(piece);
            }
        } else if (buckets[1][0].contains(piece)) {
            buckets[1][0].remove(piece);
        }

        if (piece.getX() > WINDOW_WIDTH/3 && piece.getX() + piece.getWidth() < 2*WINDOW_WIDTH/3 && piece.getY() > WINDOW_HEIGHT/3 && piece.getY() + piece.getHeight() < 2*WINDOW_HEIGHT/3) {
            if (!buckets[1][1].contains(piece)) {
                buckets[1][1].add(piece);
            }
        } else if (buckets[1][1].contains(piece)) {
            buckets[1][1].remove(piece);
        }

        if (piece.getX() > 2*WINDOW_WIDTH/3 && piece.getY() > WINDOW_HEIGHT/3 && piece.getY() + piece.getHeight() < 2*WINDOW_HEIGHT/3) {
            if (!buckets[1][2].contains(piece)) {
                buckets[1][2].add(piece);
            }
        } else if (buckets[1][2].contains(piece)) {
            buckets[1][2].remove(piece);
        }

        if (piece.getX() + piece.getWidth() < WINDOW_WIDTH/3 && piece.getY() > 2*WINDOW_HEIGHT/3) {
            if (!buckets[2][0].contains(piece)) {
                buckets[2][0].add(piece);
            }
        } else if (buckets[2][0].contains(piece)) {
            buckets[2][0].remove(piece);
        }

        if (piece.getX() > WINDOW_WIDTH/3 && piece.getX() + piece.getWidth() < 2*WINDOW_WIDTH/3 && piece.getY() > 2*WINDOW_HEIGHT/3) {
            if (!buckets[2][1].contains(piece)) {
                buckets[2][1].add(piece);
            }
        } else if (buckets[2][1].contains(piece)) {
            buckets[2][1].remove(piece);
        }

        if (piece.getX() > 2*WINDOW_WIDTH/3  && piece.getY() > 2*WINDOW_HEIGHT/3) {
            if (!buckets[2][2].contains(piece)) {
                buckets[2][2].add(piece);
            }
        } else if (buckets[2][2].contains(piece)) {
            buckets[2][2].remove(piece);
        }
    }

    /**
     * Changes type of overlapping pieces following the logic of Rock, Paper, Scissors
     */
    private void handleCollisions() {
        for (ArrayList<GamePiece>[] row : buckets) {
            for(ArrayList<GamePiece> bucket : row) {
                for (int i = 0; i < bucket.size(); i += 1) {
                    GamePiece piece1 = bucket.get(i);
                    for (int j = i + 1; j < bucket.size(); j += 1) {
                        GamePiece piece2 = bucket.get(j);
                        if (piece1.getRadius() + piece2.getRadius() > piece1.getCenter().distance(piece2.getCenter())) {
                            collision(piece1, piece2);
                        }
                    }
                }
            }
        }
    }
    
    

    private void collision(GamePiece piece1, GamePiece piece2) {
        int result = gpc.compare(piece1, piece2);
        if (result == -1) {
            teamCounts.put(piece1.getType(), teamCounts.get(piece1.getType()) - 1);
            piece1.setType(piece2.getType());
            teamCounts.put(piece1.getType(), teamCounts.get(piece1.getType()) + 1);
        } else if (result == 1) {
            teamCounts.put(piece2.getType(), teamCounts.get(piece2.getType()) - 1);
            piece2.setType(piece1.getType());
            teamCounts.put(piece2.getType(), teamCounts.get(piece2.getType()) + 1);
        }
    }
    /**
     * Checks whether given piece is out of bounds
     * @param piece GamePiece whose position is being validated
     * @return whether given piece is out of bounds
     */
    private boolean outOfBounds(GamePiece piece) {
        if (piece.getX() <= ui.getX() + ui.getWidth()) {
            return true;
        } 
        if (piece.getX() + piece.getWidth() >= WINDOW_WIDTH) {
            return true;
        } 
        if (piece.getY() <= 0) {
            return true;
        } 
        if (piece.getY() + piece.getHeight() >= WINDOW_HEIGHT) {
            return true;
        }
        return false;
    }

    /**
     * Ends simulation if all pieces are the same type
     */
    private void checkWinner() {
        for (GamePiece.PieceType team : teamCounts.keySet()) {
            if (teamCounts.get(team) == pieces.size()) {
                endGame(team);
            }
        }
    }

    /**
     * Displays winning piece
     * @param winner
     */
    private void endGame(GamePiece.PieceType winner) {
        running = ui.toggleStartButton(running);
        setStartButton();
    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.run();
    }
}