import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

/**
 * Represents a single rock, paper, or scissors in the hit game Rock, Paper, Scissors
 * @author Olive Pilling Chappelear, Courtney Brown, Alana Nadolski
 */
public class GamePiece extends Image {
    public enum PieceType {
        ROCK,
        PAPER, 
        SCISSORS; 
    }
    private static final double WANDER_FROM_CENTER = 60000;
    private static final double WIGGLINESS = 0.2;
    private double direction;
    private PieceType type;

    public GamePiece(PieceType type) {
        super(0,0);
        this.type = type;
        direction = 0;
        setPath();
    }

    /**
     * Sets image to reflect piece type
     */
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

    public void switchDirection() {
        direction -= 180;
    }

    /**
     * Calculates next position of this GamePiece
     * @param centerOfGravity point around which GamePiece moves
     * @param speed movement speed of each GamePiece
     */
    public void updatePosition(Point centerOfGravity, double speed) {
        moveBy(Math.cos(direction) * speed, Math.sin(direction) * speed);

        double distToCenter = getCenter().distance(centerOfGravity);
        double angleToCenter = centerOfGravity.subtract(getCenter()).angle();
        double turnTowardCenter = normalizeRadians(angleToCenter - direction);

        direction = normalizeRadians(
            direction
                + (Math.random() - 0.5) * (WIGGLINESS)
                + turnTowardCenter * Math.tanh(distToCenter / (WANDER_FROM_CENTER)));
    }

    private static double normalizeRadians(double theta) {
        double pi2 = Math.PI * 2;
        return ((theta + Math.PI) % pi2 + pi2) % pi2 - Math.PI;
    }

    public void setType(PieceType newType) {
        type = newType;
        setPath();
    }

    public PieceType getType() {
        return type;
    }
}

