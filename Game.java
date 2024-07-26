package rhythmgame;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Game {

    private TileAlley alley;
    private int tempo;

    public Game(Pane tilePane, Pane buttonPane){
        this.tempo = 120;
        this.alley = new TileAlley(tilePane, this);
        this.setUpButtonPane(buttonPane, tilePane);
    }
    private void setUpButtonPane(Pane buttonPane, Pane tilePane){
        Button quitButton = new Button("Quit");
        quitButton.setPrefHeight(Constants.BUTTON_HEIGHT);
        quitButton.setPrefWidth(Constants.BUTTON_WIDTH);
        quitButton.setStyle("-fx-background-color: #ffffff");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setFocusTraversable(false);
        Button startButton = new Button("Start");
        startButton.setPrefHeight(Constants.BUTTON_HEIGHT);
        startButton.setPrefWidth(Constants.BUTTON_WIDTH);
        startButton.setStyle("-fx-background-color: #ffffff");
        startButton.setOnAction((ActionEvent e) -> this.startGame(tilePane));
        buttonPane.getChildren().addAll(quitButton, startButton);
    }

    private void startGame(Pane tilePane){
        this.alley.startGame(tilePane);
    }
}
