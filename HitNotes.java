package rhythmgame;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class HitNotes {
    private Rectangle note;
    private Timeline timeline;

    public HitNotes(Pane tilePane, int laneNum){
        this.setUpTimeline(tilePane, laneNum);
        this.generateNote(tilePane, laneNum);
        this.startTimeline();
    }

    private void setUpTimeline(Pane tilePane, int laneNum){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.moveNote(tilePane, laneNum));
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }
    private void generateNote(Pane tilePane, int laneNum){
        this.note = new Rectangle();
        this.note.setWidth(Constants.LINE_START_INCREMENT);
        this.note.setHeight(Constants.NOTE_HEIGHT);
        this.note.setX(Constants.STARTING_X_VALUE + ((laneNum - 2) * Constants.LINE_START_INCREMENT));
        this.note.setY(0);
        this.note.setFill(Color.BLUEVIOLET);
        System.out.println("WAH");
        tilePane.getChildren().addAll(this.note);
    }
    private void startTimeline(){
        this.timeline.play();
    }
    private void moveNote(Pane tilePane, int laneNum){
        switch (laneNum){
            case 1:
                this.note.setY(this.note.getY() + 2);
                this.note.setX(this.note.getX() - 2);
                this.note.setWidth(this.note.getWidth() + 1);
                break;
            case 2:
                this.note.setY(this.note.getY() + 2);
                this.note.setX(this.note.getX() - 1);
                this.note.setWidth(this.note.getWidth() + 1);
                break;
            case 3:
                this.note.setY(this.note.getY() + 2);
                this.note.setWidth(this.note.getWidth() + 1);
                break;
            case 4:
                this.note.setY(this.note.getY() + 2);
                this.note.setX(this.note.getX() + 1);
                this.note.setWidth(this.note.getWidth() + 1);
                break;
        }
        if (this.note.getY() > Constants.SCENE_HEIGHT){
            tilePane.getChildren().remove(this.note);
        }
    }
}
