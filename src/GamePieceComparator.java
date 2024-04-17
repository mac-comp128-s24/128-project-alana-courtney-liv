import java.util.Comparator;

public class GamePieceComparator implements Comparator<GamePiece> {

    public int compare(GamePiece o1, GamePiece o2) {
        if (o1.getType() == GamePiece.PieceType.ROCK && o2.getType() == GamePiece.PieceType.PAPER) {
            return 1;
        }
        if (o1.getType() == GamePiece.PieceType.ROCK && o2.getType() == GamePiece.PieceType.SCISSORS) {
            return -1;
        }
        if (o1.getType() == GamePiece.PieceType.PAPER && o2.getType() == GamePiece.PieceType.ROCK) {
            return -1;
        }
        if (o1.getType() == GamePiece.PieceType.PAPER && o2.getType() == GamePiece.PieceType.SCISSORS) {
            return 1;
        }
        if (o1.getType() == GamePiece.PieceType.SCISSORS && o2.getType() == GamePiece.PieceType.ROCK) {
            return 1;
        }
        if (o1.getType() == GamePiece.PieceType.SCISSORS && o2.getType() == GamePiece.PieceType.PAPER) {
            return -1;
        }
        return 0;
    }
    
}
