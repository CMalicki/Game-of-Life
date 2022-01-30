package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    public static void main(String[] args) { launch(args);}

    Timer timer = new Timer();

    @Override
    public void start(Stage window) {
        window.setResizable(false);
        window.setTitle("Game of Life");

        // Input Dialog, was not bother to make another class -----------------------------------------------------------------
        TextInputDialog inputdialog = new TextInputDialog("15");
        inputdialog.setContentText("Size of Board(Min 5, Max 60): ");
        inputdialog.setHeaderText("Game of Life");
        Button okButton = (Button) inputdialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) inputdialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        TextField inputField = inputdialog.getEditor();
        BooleanBinding isValid = Bindings.createBooleanBinding(() -> !isValid(inputField.getText()), inputField.textProperty());
        okButton.disableProperty().bind(isValid);
        cancelButton.setOnAction(e -> System.exit(0));
        Optional<String> result = inputdialog.showAndWait();
        if (!result.isPresent()) {
            System.exit(0);
        }
        // -----------------------------------------------------------------

        // GUI
        BorderPane scenePane = new BorderPane();
        Rectangle[][] pola = new Rectangle[Integer.parseInt(inputdialog.getEditor().getText())][Integer.parseInt(inputdialog.getEditor().getText())];
        GridPane layout = new GridPane();
        Board board = new Board(pola, layout);
        board.printBoard();
        layout = board.getBoard();
        HBox buttonBox = new HBox();
        Button stopButton = new Button("Pause");
        Button startButton = new Button("Start");
        Button resetButton = new Button("Reset");
        stopButton.setMinWidth((board.getRectangles()[0][0].getWidth() * board.getBoard().getRowCount()) / 3);
        startButton.setMinWidth((board.getRectangles()[0][0].getWidth() * board.getBoard().getRowCount()) / 3);
        resetButton.setMinWidth((board.getRectangles()[0][0].getWidth() * board.getBoard().getRowCount()) / 3);
        stopButton.setAlignment(Pos.CENTER);
        startButton.setAlignment(Pos.CENTER);
        resetButton.setAlignment(Pos.CENTER);

        // Buttons actions
        final int[] countGenerations = {0};
        final int[] costam = {0};

        AtomicBoolean started = new AtomicBoolean(false);
        AtomicBoolean stopped = new AtomicBoolean(true);

        startButton.setOnAction(e -> {
            if(!started.get()) {
                this.timer = new Timer();
                countGenerations[0] = costam[0];
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        scenePane.setTop(Game.getNewBoard(board));
                        if(Game.playingGame(board)) {
                            pause();
                            Platform.runLater(stopButton::fire);
                        }
                        countGenerations[0]++;
                    }
                }, 0, 200);
                started.set(true);
                stopped.set(false);
            }
        });

        stopButton.setOnAction(e -> {
            if (!stopped.get()) {
                pause();
                System.out.println("Generations: " + countGenerations[0]);
                costam[0] = countGenerations[0];
                stopped.set(true);
                started.set(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Generations information");
                alert.setHeaderText("Generations: " + countGenerations[0]);
                alert.showAndWait();
            }
        });

        resetButton.setOnAction(e -> {
            pause();
            costam[0] = 0;
            started.set(false);
            stopped.set(true);
            board.printBoard();
        });

        buttonBox.getChildren().add(startButton);
        buttonBox.getChildren().add(stopButton);
        buttonBox.getChildren().add(resetButton);

        buttonBox.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        scenePane.setBottom(buttonBox);
        scenePane.setTop(layout);
        Scene scene = new Scene(scenePane);
        window.setScene(scene);
        window.setOnCloseRequest(e -> System.exit(0));
        window.show();
        window.setIconified(true);
        window.setIconified(false);
    }


    public void pause() {
        timer.cancel();
    }

    public boolean isValid(String text) { // Input Dialog data verification
        if (text == null) {
            return false;
        }
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException nfe) {
            return false;
        }
        if(Integer.parseInt(text) > 60 || Integer.parseInt(text) < 5) {
            return false;
        }
        return true;
    }
}

