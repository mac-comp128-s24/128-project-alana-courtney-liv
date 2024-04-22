import java.awt.Toolkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObserver;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.events.Key;

public class RPS {
    private final double WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private final GamePieceComparator gpc = new GamePieceComparator();
    private final Random r = new Random();

    private CanvasWindow canvas;
    private GraphicsGroup pieceGroup;
    private HashMap<GamePiece.PieceType, Integer> teamCounts;
    private HashSet<GamePiece> pieces;
    private UI ui;

    public RPS() {
        canvas = new CanvasWindow("Rock Paper Scissors", (int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
        pieceGroup = new GraphicsGroup();
        canvas.add(pieceGroup);
        ui = new UI(.2 * WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas.add(ui, 0, 0);
        teamCounts = ui.getTeamCounts();
        pieces = new HashSet<>();
        addPieces();

        ui.startButton.onClick(() -> reset());
    }

    public void addPieces() {
        int pieceCount = teamCounts.get(GamePiece.PieceType.ROCK) + teamCounts.get(GamePiece.PieceType.PAPER) + teamCounts.get(GamePiece.PieceType.SCISSORS);
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.ROCK); i++) {
            GamePiece tempRock = new GamePiece(GamePiece.PieceType.ROCK, canvas, 0);
            pieceGroup.add(tempRock, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempRock.setMaxHeight(WINDOW_HEIGHT / pieceCount);
            pieces.add(tempRock);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.PAPER); i++) {
            GamePiece tempPaper = new GamePiece(GamePiece.PieceType.PAPER, canvas, 0);
            pieceGroup.add(tempPaper, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempPaper.setMaxHeight(WINDOW_HEIGHT / pieceCount);
            pieces.add(tempPaper);
        }
        for (int i = 0; i < teamCounts.get(GamePiece.PieceType.SCISSORS); i++) {
            GamePiece tempScissors = new GamePiece(GamePiece.PieceType.SCISSORS, canvas, 0);
            pieceGroup.add(tempScissors, r.nextDouble(0, WINDOW_WIDTH), r.nextDouble(0, WINDOW_HEIGHT));
            tempScissors.setMaxHeight(WINDOW_HEIGHT / pieceCount);
            pieces.add(tempScissors);
        }
    }

    private void run() {
        canvas.animate(() -> {
            moveAll();
            handleCollisions();
        });
    }

    public void reset() {
        pieceGroup.removeAll();
        pieces.clear();
        teamCounts = ui.getTeamCounts();
        addPieces();
    }

    private void moveAll() {
        Point centerPoint = new Point(r.nextDouble(WINDOW_WIDTH / 10, 9 * (WINDOW_WIDTH / 10)), r.nextDouble(WINDOW_HEIGHT / 10, 9 * (WINDOW_HEIGHT / 10)));
        for (GamePiece piece : pieces) {
            piece.updatePosition(centerPoint, 5);
        }
    }

    private void handleCollisions() {
        for (GamePiece piece : pieces) {
            GamePiece piece2 = (GamePiece) pieceGroup.getElementAt(piece.getPosition());
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
