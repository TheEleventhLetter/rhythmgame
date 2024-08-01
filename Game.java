package rhythmgame;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class Game {

    private TileAlley alley;
    private int tempo;
    private boolean isPlaying;
    private int score;
    private Label scoreLabel;

    public Game(Pane tilePane, HBox buttonPane){
        this.score = 0;
        this.isPlaying = false;
        this.tempo = 120;
        this.alley = new TileAlley(tilePane, this);
        this.setUpButtonPane(buttonPane, tilePane);
        this.setUpTilePane(tilePane);
    }
    private void setUpButtonPane(HBox buttonPane, Pane tilePane){
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
        startButton.setFocusTraversable(false);
        Button stopButton = new Button("Stop");
        stopButton.setPrefHeight(Constants.BUTTON_HEIGHT);
        stopButton.setPrefWidth(Constants.BUTTON_WIDTH);
        stopButton.setStyle("-fx-background-color: #ffffff");
        stopButton.setOnAction((ActionEvent e) -> this.stopGame());
        stopButton.setFocusTraversable(false);
        this.scoreLabel = new Label(String.valueOf(this.score));
        this.scoreLabel.setFont(new Font(20));
        this.scoreLabel.setAlignment(Pos.CENTER_RIGHT);

        buttonPane.getChildren().addAll(quitButton, startButton, stopButton, this.scoreLabel);
        buttonPane.setFocusTraversable(false);
    }
    private void setUpTilePane(Pane tilePane){
        tilePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        tilePane.setFocusTraversable(true);
    }

    private void startGame(Pane tilePane){
        this.isPlaying = true;
        this.alley.startGame(tilePane);
    }
    private void stopGame(){
        this.isPlaying = false;
        this.alley.stopGame();
    }
    private void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if (this.isPlaying){
            switch (keyPressed) {
                case D:
                    this.alley.hitLane(1);
                    System.out.println("D");
                    break;
                case F:
                    this.alley.hitLane(2);
                    System.out.println("F");
                    break;
                case J:
                    this.alley.hitLane(3);
                    System.out.println("J");
                    break;
                case K:
                    this.alley.hitLane(4);
                    System.out.println("K");
                    break;
                default:
                    break;
            }
            e.consume();

        }
    }
    public void addPoint(){
        this.score++;
        this.scoreLabel.setText(String.valueOf(this.score));
    }
}
