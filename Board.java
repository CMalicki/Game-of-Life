package application;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Board {

    private Rectangle[][] pola;
    private GridPane layout;

    public Board(Rectangle[][] pola, GridPane layout) {
        this.pola = pola;
        this.layout = layout;
    }

    public void printBoard() {
        int boardLength = pola.length; // square anyway
        for(int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                Rectangle rectangle = new Rectangle();
                pola[i][j] = rectangle;
                rectangle.setWidth(15);
                rectangle.setHeight(15);
                int whatColor;
                if (Math.random() <= 0.5) {
                    whatColor = 0;
                } else whatColor = 1;
                if(whatColor == 1) {
                    rectangle.setFill(Color.BLACK);
                } else rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.BLACK);
                GridPane.setRowIndex(rectangle,i);
                GridPane.setColumnIndex(rectangle,j);
                layout.getChildren().addAll(rectangle);
            }
        }

        for(int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (!(i != 0 && j != 0  && i != boardLength - 1 && j != boardLength - 1)) {
                    pola[i][j].setFill(Color.AQUA);
                }
            }
        }
    }

    public GridPane getBoard() {
        return layout;
    }

    public Rectangle[][] getRectangles() {
        return pola;
    }

    public void updateRectangles(Rectangle[][] rectangles) {
        for (int i = 0; i < pola.length; i++) {
            for (int j = 0; j < pola.length; j++) {
                pola[i][j].setWidth(rectangles[i][j].getWidth());
                pola[i][j].setHeight(rectangles[i][j].getHeight());
                pola[i][j].setFill(rectangles[i][j].getFill());
                pola[i][j].setStroke(Color.BLACK);
                GridPane.setRowIndex(pola[i][j],i);
                GridPane.setColumnIndex(pola[i][j],j);
            }
        }
    }

}
