import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class GamePiece extends Image {
    public enum PieceType {
        ROCK,
        PAPER, 
        SCISSORS; 
    }
    private static final double WANDER_FROM_CENTER = 60000;
    private static final double WIGGLINESS = 0.2;
    private double direction = 0;
    private PieceType type;

    public GamePiece(PieceType type, CanvasWindow canvas, double minY) {
        super(0,0);
        this.type = type;
        setPath();
    }

    private void setPath() {
        switch (type) {
            case ROCK :
                setImagePath("rock.png");
                break;
            case PAPER :
                setImagePath("paper.png");
                break;
            case SCISSORS :
                setImagePath("scissors.png");
                break;
        }
    }

    public void updatePosition(Point centerOfGravity, double speed) {
        moveBy(Math.cos(direction) * speed, Math.sin(direction) * speed);

        double distToCenter = getCenter().distance(centerOfGravity);
        double angleToCenter = centerOfGravity.subtract(getCenter()).angle();
        double turnTowardCenter = normalizeRadians(angleToCenter - direction);

        direction = normalizeRadians(
            direction
                + (Math.random() - 0.5) * (WIGGLINESS * (speed / 10))
                + turnTowardCenter * Math.tanh(distToCenter / (WANDER_FROM_CENTER / (speed / 2))));
    }

    private static double normalizeRadians(double theta) {
        double pi2 = Math.PI * 2;
        return ((theta + Math.PI) % pi2 + pi2) % pi2 - Math.PI;
    }

    public void changeType(PieceType newType) {
        type = newType;
        setPath();
    }

    public PieceType getType() {
        return type;
    }
}

