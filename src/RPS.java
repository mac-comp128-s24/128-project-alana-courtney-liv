import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class RPS {
    private CanvasWindow canvas;
    private HashMap<GamePiece.PieceType, Integer> teamCounts;
    private HashSet<GamePiece> pieces;

    public RPS() {
        canvas = new CanvasWindow("Rock Paper Scissors", 600, 600);
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
            canvas.add(tempRock, r.nextDouble(0, canvas.getWidth()), r.nextDouble(0, canvas.getHeight()));
            tempRock.setMaxHeight(canvas.getHeight() / (4 * teamCounts.get(GamePiece.PieceType.ROCK)));
            pieces.add(tempRock);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.PAPER); i++) {
            GamePiece tempPaper = new GamePiece(GamePiece.PieceType.PAPER, canvas, 0);
            canvas.add(tempPaper, r.nextDouble(0, canvas.getWidth()), r.nextDouble(0, canvas.getHeight()));
            tempPaper.setMaxHeight(canvas.getHeight() / (4 * teamCounts.get(GamePiece.PieceType.PAPER)));
            pieces.add(tempPaper);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.SCISSORS); i++) {
            GamePiece tempScissors = new GamePiece(GamePiece.PieceType.SCISSORS, canvas, 0);
            canvas.add(tempScissors, r.nextDouble(0, canvas.getWidth()), r.nextDouble(0, canvas.getHeight()));
            tempScissors.setMaxHeight(canvas.getHeight() / (4 * teamCounts.get(GamePiece.PieceType.SCISSORS)));
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
            GraphicsObject touching = canvas.getElementAt(piece.getPosition());
            if (touching != null && touching.getClass() == GamePiece.class && !touching.equals(piece)) {
                
            }
        }
    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.run();
    }
}
