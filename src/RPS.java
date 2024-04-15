import java.util.HashMap;

import edu.macalester.graphics.CanvasWindow;

public class RPS {
    private CanvasWindow canvas;
    private HashMap<GamePiece.PieceType, Integer> teamCounts;
    private int teamSize;

    public RPS() {
        canvas = new CanvasWindow("Rock Paper Scissors", 600, 600);
        teamSize = 3;
        teamCounts = new HashMap<>();
        addPieces();
    }

    public void addPieces() {
        
    }

    public void run() {

    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.run();
    }
}
