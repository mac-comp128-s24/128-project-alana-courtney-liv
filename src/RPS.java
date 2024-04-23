import java.awt.Toolkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class RPS {
    private final double WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private final GamePieceComparator gpc = new GamePieceComparator();
    private final Random r = new Random();

    private CanvasWindow canvas;
    private GraphicsGroup pieceGroup;
    private HashMap<GamePiece.PieceType, Integer> teamCounts;
    private HashSet<GamePiece> pieces;
    private int pieceCount;
    private UI ui;
    private boolean running;

    public RPS() {
        canvas = new CanvasWindow("Rock Paper Scissors", (int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
        pieceGroup = new GraphicsGroup();
        canvas.add(pieceGroup);
        ui = new UI(.2 * WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.add(ui, 0, 0);
        teamCounts = ui.getTeamCounts();
        pieces = new HashSet<>();
        addPieces();
        running = false;

        ui.getButton().onClick(() -> {
            ui.toggleButton();
            running = ui.isRunning();
            if (running) {
                reset();
            }
            canvas.draw();
        });
    }

    public void addPieces() {
        pieceCount = teamCounts.get(GamePiece.PieceType.ROCK) + teamCounts.get(GamePiece.PieceType.PAPER) + teamCounts.get(GamePiece.PieceType.SCISSORS);
        double scale = WINDOW_HEIGHT / (Math.log(pieceCount) / Math.log(1.2));
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.ROCK); i++) {
            GamePiece tempRock = new GamePiece(GamePiece.PieceType.ROCK);
            pieceGroup.add(tempRock, r.nextDouble(ui.getX() + ui.getWidth(), WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempRock.setMaxHeight(scale);
            pieces.add(tempRock);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.PAPER); i++) {
            GamePiece tempPaper = new GamePiece(GamePiece.PieceType.PAPER);
            pieceGroup.add(tempPaper, r.nextDouble(ui.getX() + ui.getWidth(), WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempPaper.setMaxHeight(scale);
            pieces.add(tempPaper);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.SCISSORS); i++) {
            GamePiece tempScissors = new GamePiece(GamePiece.PieceType.SCISSORS);
            pieceGroup.add(tempScissors, r.nextDouble(ui.getX() + ui.getWidth(), WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempScissors.setMaxHeight(scale);
            pieces.add(tempScissors);
        }
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

    public void reset() {
        pieceGroup.removeAll();
        pieces.clear();
        teamCounts = ui.getTeamCounts();
        addPieces();
        running = true;
    }

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

    private void handleCollisions() {
        for (GamePiece piece : pieces) {
            GamePiece piece2 = (GamePiece) pieceGroup.getElementAt(piece.getPosition());
            if (piece2 != null && !piece2.equals(piece)) {
                int result = gpc.compare(piece, piece2);
                if (result == -1) {
                    teamCounts.put(piece.getType(), teamCounts.get(piece.getType()) - 1);
                    piece.changeType(piece2.getType());
                    teamCounts.put(piece.getType(), teamCounts.get(piece.getType()) + 1);
                    ui.updateTeamCounts(teamCounts);
                }
            }
        }
    }
     
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

    private void checkWinner() {
        for (GamePiece.PieceType team : teamCounts.keySet()) {
            if (teamCounts.get(team) == pieceCount) {
                running = false;
                endGame(team);
            }
        }
    }

    private void endGame(GamePiece.PieceType winner) {
        System.out.println(winner + " WON!");
    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.run();
    }
}
