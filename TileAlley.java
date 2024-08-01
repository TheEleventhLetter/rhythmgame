package rhythmgame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class TileAlley {
    private HitLine[] lanes;
    private ArrayList<HitNotes>[] notes;
    private Timeline timeline;
    private Game myGame;

    public TileAlley(Pane tilePane, Game game){
        this.myGame = game;
        this.notes = new ArrayList[4];
        this.lanes = new HitLine[4];
        this.setUpTimeline(tilePane);
        this.setUpAlleyLane(tilePane);
    }
    private void setUpAlleyLane(Pane tilePane){
        Polygon alleyLane = new Polygon();
        alleyLane.getPoints().addAll(Constants.ALLEY_COORDS);
        Stop[] stops = new Stop[] { new Stop(0, Color.DARKGRAY), new Stop(1, Color.LIGHTGRAY)};
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        alleyLane.setFill(lg1);
        alleyLane.setStroke(Color.BLUEVIOLET);
        alleyLane.setStrokeType(StrokeType.OUTSIDE);
        alleyLane.setStrokeWidth(5.0);
        tilePane.getChildren().addAll(alleyLane);
        double xstart = Constants.STARTING_X_VALUE; double xend = Constants.ENDING_X_VALUE;
        for (int i = 0; i < 3; i++){
            Line line = new Line(xstart, 0.0, xend, (double)Constants.SCENE_HEIGHT);
            tilePane.getChildren().addAll(line);
            xstart = xstart + Constants.LINE_START_INCREMENT; xend = xend + Constants.LINE_END_INCREMENT;
        }
        this.setUpHitLines(tilePane);

    }
    private void setUpHitLines(Pane tilePane){
        for (int i = 0; i < 4;  i++){
            HitLine currLine = new HitLine(tilePane, i + 1);
            this.lanes[i] = currLine;
            this.notes[i] = new ArrayList<HitNotes>();
        }
    }
    public void setUpTimeline(Pane tilePane){
        KeyFrame kf = new KeyFrame((Duration.seconds(1)), (ActionEvent e) -> this.sendNote(tilePane));
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }
    public void startGame(Pane tilePane){
        this.timeline.play();
    }
    public void stopGame(){
        this.timeline.stop();
    }

    private void sendNote(Pane tilePane){
        int laneNum = this.getLaneNum();
        HitNotes currNote = new HitNotes(tilePane, laneNum, this);
        this.notes[laneNum - 1].add(currNote);
    }
    public void removeNote(HitNotes remNote, int myLane){
        this.notes[myLane - 1].remove(remNote);
    }
    private int getLaneNum(){
        int rand = (int)(Math.random() * 4) + 1;
        return rand;
    }
    public void hitLane(int laneNum){
        switch (laneNum){
            case 1:
                this.lanes[0].glowUp();
                this.checkIntersection(1);
                break;
            case 2:
                this.lanes[1].glowUp();
                this.checkIntersection(2);
                break;
            case 3:
                this.lanes[2].glowUp();
                this.checkIntersection(3);
                break;
            case 4:
                this.lanes[3].glowUp();
                this.checkIntersection(4);
                break;
            default:
                break;
        }
    }

    public void checkIntersection(int laneNum){
        HitNotes lastNote = null;
        if (!this.notes[laneNum - 1].isEmpty()) {
            lastNote = this.notes[laneNum - 1].get(0);
            if (this.lanes[laneNum - 1].didCollide(lastNote.getX(), lastNote.getY(), lastNote.getWidth())) {
                this.myGame.addPoint();
                lastNote.removeNoteVisual();
                this.removeNote(lastNote, laneNum);
            }
        }

    }

}
