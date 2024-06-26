import java.util.HashMap;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

/**
 * UI manager for Rock Paper Scissors game
 * @author Olive Pilling Chappelear, Courtney Brown, Alana Nadolski
 */
public class UI extends GraphicsGroup {
    
    public TextField rockInput;
    public TextField paperInput;
    public TextField scissorsInput;

    public Button resetButton;
    public Button startButton;

    private GraphicsText enterTip;

    private double width;
    private double height;


    public UI(double width, double height){
        this.add(new Rectangle(0, 0, width, height));
        this.width = width;
        this.height = height;

        setupGraphics();
    }

    /**
     * Arranges and adds all components to the canvas
     */
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

        resetButton = new Button("RESET");
        add(resetButton, width * 0.25 - resetButton.getWidth() / 2, height * 0.05);

        addStartButton("START");

        GamePiece rockIcon = new GamePiece(GamePiece.PieceType.ROCK);
        rockIcon.setMaxWidth(.2 * width);
        add(rockIcon, width * .1, height * .175);

        GamePiece paperIcon = new GamePiece(GamePiece.PieceType.PAPER);
        paperIcon.setMaxWidth(.2 * width);
        add(paperIcon, width * .1, height * .275);

        GamePiece scissorsIcon = new GamePiece(GamePiece.PieceType.SCISSORS);
        scissorsIcon.setMaxWidth(.2 * width);
        add(scissorsIcon, width * .1, height * .375);

        enterTip = new GraphicsText("Press space to update team size\n(this will move everything)");
        add(enterTip, width * .1, scissorsIcon.getY() + 2 * scissorsIcon.getHeight());
    }

    /**
     * @return HashMap representing the number of pieces on each team
     */
    public HashMap<GamePiece.PieceType, Integer> getTeamCounts() {
        HashMap<GamePiece.PieceType, Integer> counts = new HashMap<>();

        counts.put(GamePiece.PieceType.ROCK, Integer.parseInt(rockInput.getText().strip()));
        counts.put(GamePiece.PieceType.PAPER, Integer.parseInt(paperInput.getText().strip()));
        counts.put(GamePiece.PieceType.SCISSORS, Integer.parseInt(scissorsInput.getText().strip()));

        return counts;
    }

    /**
     * Stops/starts/resets simulation, updates button to reflect state.
     * @return whether the simulation should be running after button click
     */
    public boolean toggleStartButton(boolean running) {
        if (!running) {
            addStartButton("STOP");
            return true;
        } 
        else {
            addStartButton("START");
            return false;
        }
    }

    /**
     * Creates the start button at the correct location given a title
     */
    private void addStartButton(String title) {
        if (startButton != null) {
            remove(startButton);
        }
        startButton = new Button(title);
        add(startButton, width * 0.75 - startButton.getWidth() / 2, height * 0.05);
    }

    /**
     * Updates text boxes to display current team counts
     * @param counts
     */
    public void updateTeamCounts(HashMap<GamePiece.PieceType, Integer> counts) {
        rockInput.setText(counts.get(GamePiece.PieceType.ROCK).toString());
        paperInput.setText(counts.get(GamePiece.PieceType.PAPER).toString());
        scissorsInput.setText(counts.get(GamePiece.PieceType.SCISSORS).toString());
    }

    /**
     * Updates text to reset team counts to 10
     */
    public void resetTeamCounts() {
        rockInput.setText("10");
        paperInput.setText("10");
        scissorsInput.setText("10");
    }
}
