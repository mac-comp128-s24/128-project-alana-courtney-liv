import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

public class GamePiece {
    public enum PieceType {
        ROCK,
        PAPER, 
        SCISSORS; 
    }
    private Image image;
    private PieceType type;
    private double maxX;
    private double maxY;
    

    public GamePiece(PieceType type, CanvasWindow canvas, double minY) {
        this.type = type;
        setImage();
        canvas.add(image);
    }

    private void setImage() {
        switch (type) {
            case ROCK :
                image = new Image("rock.png");
                break;
            case PAPER :
                image = new Image("paper.png");
                break;
            case SCISSORS :
                image = new Image("scissors.png");
                break;
        }
    }

    public void changeType(PieceType newType) {
        type = newType;
        setImage();
    }
}

