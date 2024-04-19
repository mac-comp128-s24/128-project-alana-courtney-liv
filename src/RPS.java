import java.awt.Toolkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class RPS {
    private final int WINDOW_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final int WINDOW_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private final GamePieceComparator gpc = new GamePieceComparator();
    private final Random r = new Random();

    private CanvasWindow canvas;
    private GraphicsGroup pieceField;
    private HashMap<GamePiece.PieceType, Integer> teamCounts;
    private HashSet<GamePiece> pieces;
    private UI ui;

    public RPS() {
        canvas = new CanvasWindow("Rock Paper Scissors", WINDOW_WIDTH, WINDOW_HEIGHT);
        pieceField = new GraphicsGroup();
        canvas.add(pieceField);
        ui = new UI((int) (WINDOW_WIDTH * .2), WINDOW_HEIGHT);
        canvas.add(ui, 0, 50);
        teamCounts = new HashMap<>();
        teamCounts.put(GamePiece.PieceType.ROCK, 15);
        teamCounts.put(GamePiece.PieceType.PAPER, 7);
        teamCounts.put(GamePiece.PieceType.SCISSORS, 3);
        pieces = new HashSet<>();
        addPieces();
    }

    public void addPieces() {
        int pieceCount = teamCounts.get(GamePiece.PieceType.ROCK) + teamCounts.get(GamePiece.PieceType.PAPER) + teamCounts.get(GamePiece.PieceType.SCISSORS);
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.ROCK); i++) {
            GamePiece tempRock = new GamePiece(GamePiece.PieceType.ROCK, canvas, 0);
            pieceField.add(tempRock, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempRock.setMaxHeight(WINDOW_HEIGHT / pieceCount);
            pieces.add(tempRock);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.PAPER); i++) {
            GamePiece tempPaper = new GamePiece(GamePiece.PieceType.PAPER, canvas, 0);
            pieceField.add(tempPaper, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempPaper.setMaxHeight(WINDOW_HEIGHT / pieceCount);
            pieces.add(tempPaper);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.SCISSORS); i++) {
            GamePiece tempScissors = new GamePiece(GamePiece.PieceType.SCISSORS, canvas, 0);
            pieceField.add(tempScissors, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempScissors.setMaxHeight(WINDOW_HEIGHT / pieceCount);
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
        Point centerPoint = new Point(r.nextDouble(WINDOW_WIDTH / 10, 9 * (WINDOW_WIDTH / 10)), r.nextDouble(WINDOW_HEIGHT / 10, 9 * (WINDOW_HEIGHT / 10)));
        for (GamePiece piece : pieces) {
            piece.updatePosition(centerPoint, 5);
        }
    }

    private void handleCollisions() {
        for (GamePiece piece : pieces) {
            GamePiece piece2 = (GamePiece) pieceField.getElementAt(piece.getPosition());
            if (piece2 != null && !piece2.equals(piece)) {
                int result = gpc.compare(piece, piece2);
                if (result == -1) {
                    piece.changeType(piece2.getType());
                }
            }
        }
    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.run();
    }
}
