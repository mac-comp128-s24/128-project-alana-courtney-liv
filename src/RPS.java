import java.awt.Toolkit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    private boolean gameOver;

    public RPS() {
        canvas = new CanvasWindow("Rock Paper Scissors", (int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
        pieceGroup = new GraphicsGroup();
        canvas.add(pieceGroup);
        ui = new UI(.2 * WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.add(ui, 0, 0);
        teamCounts = ui.getTeamCounts();
        pieces = new ArrayList<GamePiece>();
        addPieces();
        running = false;
        gameOver = false;
        setStartButton();
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
            running = ui.toggleButton(running);
            if (gameOver) {
                reset();
            }
            setStartButton();
        });
    }

    private void run() {
        canvas.animate(() -> {
            if (running) {
                moveAll();
                handleCollisions();
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
        teamCounts = ui.getTeamCounts();
        addPieces();
        running = true;
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
        }
    }

    /**
     * Changes type of overlapping pieces following the logic of Rock, Paper, Scissors
     */
    private void handleCollisions() {
        for (int i = 0; i < pieces.size(); i += 1) {
            GamePiece piece1 = pieces.get(i);
            for (int j = i + 1; j < pieces.size(); j += 1) {
                GamePiece piece2 = pieces.get(j);
                if (piece1.getRadius() + piece2.getRadius() > piece1.getCenter().distance(piece2.getCenter())) {
                    collision(piece1, piece2);
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
            ui.updateTeamCounts(teamCounts);
        } else if (result == 1) {
            teamCounts.put(piece2.getType(), teamCounts.get(piece2.getType()) - 1);
            piece2.setType(piece1.getType());
            teamCounts.put(piece2.getType(), teamCounts.get(piece2.getType()) + 1);
            ui.updateTeamCounts(teamCounts);
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
            if (teamCounts.get(team) == pieceCount) {
                endGame(team);
            }
        }
    }

    /**
     * Displays winning piece
     * @param winner
     */
    private void endGame(GamePiece.PieceType winner) {
        running = ui.toggleButton(running);
        gameOver = true;
        System.out.println(winner + " WON!");
    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.run();
    }
}