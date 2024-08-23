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
    private int laneNum;
    private Pane tilePane;
    private boolean isOutOfBounds;

    public HitNotes(Pane tilePane, int laneNum, TileAlley myAlley){
        this.laneNum = laneNum;
        this.tilePane = tilePane;
        this.isOutOfBounds = false;
        this.setUpTimeline(myAlley);
        this.generateNote();
        this.startTimeline();
    }

    private void setUpTimeline(TileAlley myAlley){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.moveNote(this.laneNum, myAlley));
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }
    private void generateNote(){
        this.note = new Rectangle();
        this.note.setWidth(Constants.LINE_START_INCREMENT);
        this.note.setHeight(Constants.NOTE_HEIGHT);
        this.note.setX(Constants.STARTING_X_VALUE + ((this.laneNum - 2) * Constants.LINE_START_INCREMENT));
        this.note.setY(0);
        this.note.setFill(Color.BLUEVIOLET);
        this.note.setStrokeWidth(5.0);
        this.tilePane.getChildren().addAll(this.note);
    }
    public void startTimeline(){
        this.timeline.play();
    }
    private void moveNote(int laneNum, TileAlley myAlley){
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
            this.tilePane.getChildren().remove(this.note);
            myAlley.removeNote(this, this.laneNum);
            if (!this.isOutOfBounds){
                myAlley.removePoint();
                this.isOutOfBounds = true;
            }
        }
    }
    public void stopMoving(){
        this.timeline.stop();
    }
    public void removeNoteVisual(){
        this.tilePane.getChildren().remove(this.note);
    }
    public double getX(){
        return this.note.getX();
    }
    public double getY(){
        return this.note.getY();
    }
    public double getWidth(){
        return this.note.getWidth();
    }
}
