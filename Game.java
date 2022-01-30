package application;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Game {

    static Rectangle[][] oldRectangles;
    static Rectangle[][] newRectangles;

    public static boolean playingGame(Board board) {

        oldRectangles = board.getRectangles();

        int boardLength = board.getRectangles().length;
        newRectangles = new Rectangle[boardLength][boardLength];

        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                newRectangles[i][j] = new Rectangle();
                newRectangles[i][j].setWidth(board.getRectangles()[i][j].getWidth());
                newRectangles[i][j].setHeight(board.getRectangles()[i][j].getHeight());
                newRectangles[i][j].setFill(board.getRectangles()[i][j].getFill());
                newRectangles[i][j].setStroke(Color.BLACK);
                GridPane.setRowIndex(newRectangles[i][j],i);
                GridPane.setColumnIndex(newRectangles[i][j],j);
            }
        }

        for(int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) { // It is a square anyways. Who cares
                int lifeCounter = 0;
                if ((i != 0 && j != 0  && i != boardLength - 1 && j != boardLength - 1)) {
                    if(board.getRectangles()[i - 1][j - 1].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i - 1][j].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i - 1][j + 1].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i][j - 1].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i][j + 1].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i + 1][j - 1].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i + 1][j].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i + 1][j + 1].getFill() == Color.BLACK) {
                        lifeCounter++;
                    }
                    if(board.getRectangles()[i][j].getFill() == Color.WHITE && lifeCounter == 3) {
                        newRectangles[i][j].setFill(Color.BLACK);
                    }
                    if(board.getRectangles()[i][j].getFill() == Color.BLACK && (lifeCounter < 2 || lifeCounter > 3)) {
                        newRectangles[i][j].setFill(Color.WHITE);
                    }
                }
            }
        }

        boolean end = checkifEnd(board);
        board.updateRectangles(newRectangles);
        return end;
    }

    public static GridPane getNewBoard(Board board) {
        return board.getBoard();
    }

    public static boolean checkifEnd(Board board) {
        boolean end = true;
        for (int i = 0; i < board.getRectangles().length; i++) {
            for (int j = 0; j < board.getRectangles().length; j++) {
                if (oldRectangles[i][j].getFill() != newRectangles[i][j].getFill()) {
                    end = false;
                }
            }
        }
        return end;
    }
}
