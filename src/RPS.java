import java.awt.Toolkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;

public class RPS {
    private final int WINDOW_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 50;
    private final int WINDOW_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50;

    private CanvasWindow canvas;
    private GraphicsGroup pieceField;
    private HashMap<GamePiece.PieceType, Integer> teamCounts;
    private HashSet<GamePiece> pieces;
    private UI ui;

    public RPS() {
        canvas = new CanvasWindow("Rock Paper Scissors", WINDOW_WIDTH, WINDOW_HEIGHT);
        pieceField = new GraphicsGroup();
        canvas.add(pieceField);
        ui = new UI();
        canvas.add(ui, 0, 50);
        teamCounts = new HashMap<>();
        teamCounts.put(GamePiece.PieceType.ROCK, 3);
        teamCounts.put(GamePiece.PieceType.PAPER, 3);
        teamCounts.put(GamePiece.PieceType.SCISSORS, 3);
        pieces = new HashSet<>();
        addPieces();
    }

    public void addPieces() {
        Random r = new Random();
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.ROCK); i++) {
            GamePiece tempRock = new GamePiece(GamePiece.PieceType.ROCK, canvas, 0);
            pieceField.add(tempRock, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempRock.setMaxHeight(WINDOW_HEIGHT / (4 * teamCounts.get(GamePiece.PieceType.ROCK)));
            pieces.add(tempRock);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.PAPER); i++) {
            GamePiece tempPaper = new GamePiece(GamePiece.PieceType.PAPER, canvas, 0);
            pieceField.add(tempPaper, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempPaper.setMaxHeight(WINDOW_HEIGHT / (4 * teamCounts.get(GamePiece.PieceType.PAPER)));
            pieces.add(tempPaper);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.SCISSORS); i++) {
            GamePiece tempScissors = new GamePiece(GamePiece.PieceType.SCISSORS, canvas, 0);
            pieceField.add(tempScissors, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempScissors.setMaxHeight(WINDOW_HEIGHT / (4 * teamCounts.get(GamePiece.PieceType.SCISSORS)));
            pieces.add(tempScissors);
        }
    }

    public void run() {
        canvas.animate(() -> {
            moveAll();
            handleCollisions();
        });
    }

    private void moveAll() {
        for (GamePiece piece : pieces) {
            piece.updatePosition(canvas.getCenter(), 10);
        }
    }

    private void handleCollisions() {
        for (GamePiece piece : pieces) {
            GraphicsObject touching = pieceField.getElementAt(piece.getPosition());
            if (touching != null && touching.getClass() == GamePiece.class && !touching.equals(piece)) {
                
            }
        }
    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.run();
    }
}
