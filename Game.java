package rhythmgame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private TileAlley alley;
    private int tempo;
    private boolean isPlaying;
    private int score;
    private int countDown;
    private Label scoreLabel;
    private Label countDownLabel;
    private boolean songPlayed;
    private Duration resumeLength;

    public Game(Pane tilePane, HBox buttonPane){
        this.resumeLength = null;
        this.score = 0;
        this.countDown = 3;
        this.isPlaying = false;
        this.tempo = 120;
        this.alley = new TileAlley(tilePane, this);
        this.songPlayed = false;
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
        this.playSong();
        this.alley.startGame();
        this.startCountDown(tilePane);
    }
    private void startCountDown(Pane tilePane){
        this.countDownLabel = new Label(String.valueOf(this.countDown));
        this.countDownLabel.setFont(new Font(100));
        this.countDownLabel.setTextFill(Color.LAWNGREEN);
        this.countDownLabel.setAlignment(Pos.CENTER);

        tilePane.getChildren().add(this.countDownLabel);
        KeyFrame kf = new KeyFrame((Duration.seconds(1)), (ActionEvent e) -> this.updateCountDown(tilePane));
        Timeline countDownTimeline = new Timeline(kf);
        countDownTimeline.setCycleCount(4);
        countDownTimeline.play();
    }
    private void updateCountDown(Pane tilePane){
        if (this.countDown != 0){
            this.countDown--;
            this.countDownLabel.setText(String.valueOf(this.countDown));
        } else {
            tilePane.getChildren().remove(this.countDownLabel);
        }
    }
    private void stopGame(){
        if (this.isPlaying) {
            this.isPlaying = false;
            this.mediaPlayer.pause();
            this.resumeLength = this.mediaPlayer.getCurrentTime();
            this.alley.stopGame();
        } else {
            this.isPlaying = true;
            this.mediaPlayer.play();
            this.mediaPlayer.seek(this.resumeLength);
            this.alley.resumeGame();
        }
    }
    public void endGame(){
        this.isPlaying = false;
        this.startFadeIn();
        this.alley.stopGame();
    }
    private void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if (this.isPlaying){
            switch (keyPressed) {
                case D:
                    this.alley.hitLane(1);
                    break;
                case F:
                    this.alley.hitLane(2);
                    break;
                case J:
                    this.alley.hitLane(3);
                    break;
                case K:
                    this.alley.hitLane(4);
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
    public void removePoint(){
        this.score--;
        this.scoreLabel.setText(String.valueOf(this.score));
    }
    private MediaPlayer mediaPlayer;
    private void playSong(){
        if (!this.songPlayed) {
            String bip = "src/rhythmgame/kageroudays.mp3";
            Media song = new Media(new File(bip).toURI().toString());
            this.mediaPlayer = new MediaPlayer(song);
            this.mediaPlayer.play();
            this.songPlayed = true;
        }
    }
    float volume = 1;

    private void startFadeIn(){
        final int FADE_DURATION = 3000; //The duration of the fade
        //The amount of time between volume changes. The smaller this is, the smoother the fade
        final int FADE_INTERVAL = 250;
        final int MAX_VOLUME = 1; //The volume will increase from 0 to 1
        int numberOfSteps = FADE_DURATION/FADE_INTERVAL; //Calculate the number of fade steps
        //Calculate by how much the volume changes each step
        final float deltaVolume = MAX_VOLUME / (float)numberOfSteps;

        //Create a new Timer and Timer task to run the fading outside the main UI thread
        final Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Game.this.fadeInStep(deltaVolume); //Do a fade step
                //Cancel and Purge the Timer if the desired volume has been reached
                if(Game.this.volume >=1f){
                    timer.cancel();
                    timer.purge();
                }
            }
        };

        timer.schedule(timerTask,FADE_INTERVAL,FADE_INTERVAL);
    }

    private void fadeInStep(float deltaVolume){
        this.mediaPlayer.setVolume(this.volume);
        this.volume -= deltaVolume;

    }

}
