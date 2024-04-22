import java.util.HashMap;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.TextField;

public class UI extends GraphicsGroup {
    
    private TextField rockInput;
    private TextField paperInput;
    private TextField scissorsInput;

    private double width;
    private double height;


    public UI(double width, double height){
        this.add(new Rectangle(0, 0, width, height));
        this.width = width;
        this.height = height;
        setupTextFields();
    }

    private void setupTextFields() {
        rockInput = new TextField();
        rockInput.setText("10");
        this.add(rockInput, width * .5, height * .2);

        paperInput = new TextField();
        paperInput.setText("10");
        this.add(paperInput, width * .5, height * .3);

        scissorsInput = new TextField();
        scissorsInput.setText("10");
        this.add(scissorsInput, width * .5, height * .4);
    }

    public HashMap<GamePiece.PieceType, Integer> getTeamCounts() {
        HashMap<GamePiece.PieceType, Integer> counts = new HashMap<>();

        counts.put(GamePiece.PieceType.ROCK, Integer.parseInt(rockInput.getText()));
        counts.put(GamePiece.PieceType.PAPER, Integer.parseInt(paperInput.getText()));
        counts.put(GamePiece.PieceType.SCISSORS, Integer.parseInt(scissorsInput.getText()));

        return counts;
    }
}
