import java.util.HashMap;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class UI extends GraphicsGroup {
    
    private TextField rockInput;
    private TextField paperInput;
    private TextField scissorsInput;

    private Button startButton;
    private boolean running;

    private double width;
    private double height;


    public UI(double width, double height){
        this.add(new Rectangle(0, 0, width, height));
        this.width = width;
        this.height = height;
        running = false;

        setupGraphics();
    }

    private void setupGraphics() {
        rockInput = new TextField();
        rockInput.setText("5");
        add(rockInput, width * .5, height * .2);

        paperInput = new TextField();
        paperInput.setText("5");
        add(paperInput, width * .5, height * .3);

        scissorsInput = new TextField();
        scissorsInput.setText("5");
        add(scissorsInput, width * .5, height * .4);

        startButton = new Button("START");
        add(startButton, width / 2 - startButton.getWidth() / 2, height * .05);

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

    public void toggleButton() {
        if (!running) {
            startButton = new Button("STOP");
            running = true;
        }
        else {
            startButton = new Button("START");
            running = false;
        }
    }

    public void updateTeamCounts(HashMap<GamePiece.PieceType, Integer> counts) {
        rockInput.setText(counts.get(GamePiece.PieceType.ROCK).toString());
        paperInput.setText(counts.get(GamePiece.PieceType.PAPER).toString());
        scissorsInput.setText(counts.get(GamePiece.PieceType.SCISSORS).toString());
    }

    public Button getButton() {
        return startButton;
    }

    public boolean isRunning() {
        return running;
    }
}
