import java.util.HashMap;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class UI extends GraphicsGroup {
    
    private TextField rockInput;
    private TextField paperInput;
    private TextField scissorsInput;

    public Button startButton;

    private double width;
    private double height;


    public UI(double width, double height){
        this.add(new Rectangle(0, 0, width, height));
        this.width = width;
        this.height = height;

        setupGraphics();
    }

    private void setupGraphics() {
        rockInput = new TextField();
        rockInput.setText("10");
        add(rockInput, width * .5, height * .2);

        paperInput = new TextField();
        paperInput.setText("10");
        add(paperInput, width * .5, height * .3);

        scissorsInput = new TextField();
        scissorsInput.setText("10");
        add(scissorsInput, width * .5, height * .4);

        startButton = new Button("SIMULATE");
        add(startButton);

        GamePiece rockIcon = new GamePiece(GamePiece.PieceType.ROCK);
        rockIcon.setMaxWidth(.2 * width);
        add(rockIcon, width * .1, height * .175);

        GamePiece paperIcon = new GamePiece(GamePiece.PieceType.PAPER);
        paperIcon.setMaxWidth(.2 * width);
        add(paperIcon, width * .1, height * .275);

        GamePiece scissorsIcon = new GamePiece(GamePiece.PieceType.SCISSORS);
        scissorsIcon.setMaxWidth(.2 * width);
        add(scissorsIcon, width * .1, height * .375);
    }

    public HashMap<GamePiece.PieceType, Integer> getTeamCounts() {
        HashMap<GamePiece.PieceType, Integer> counts = new HashMap<>();

        counts.put(GamePiece.PieceType.ROCK, Integer.parseInt(rockInput.getText()));
        counts.put(GamePiece.PieceType.PAPER, Integer.parseInt(paperInput.getText()));
        counts.put(GamePiece.PieceType.SCISSORS, Integer.parseInt(scissorsInput.getText()));

        return counts;
    }
}
