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

import java.util.*;

public class TileAlley {
    private HitLine[] lanes;
    private ArrayList<HitNotes>[] notes;
    private Timeline timeline;
    private Game myGame;
    private int currNoteNum;

    public TileAlley(Pane tilePane, Game game){
        this.myGame = game;
        this.notes = new ArrayList[4];
        this.lanes = new HitLine[4];
        this.currNoteNum = 0;
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
        KeyFrame kf = new KeyFrame((Duration.millis(150)), (ActionEvent e) -> this.sendNote(tilePane));
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void startGame(){
        int delayTimer = 0;
        KeyFrame kf = new KeyFrame((Duration.millis(5530)), (ActionEvent e) -> this.timeline.play());
        Timeline delayTimeline = new Timeline(kf);
        delayTimeline.play();
    }

    public void stopGame(){
        this.timeline.stop();
    }

    private void sendNote(Pane tilePane){
        int[] laneNums = this.getLaneNum();
        for (int laneNum : laneNums){
            if (laneNum == 200){
                this.myGame.endGame();
            } else if (laneNum != 0) {
                HitNotes currNote = new HitNotes(tilePane, laneNum, this);
                this.notes[laneNum - 1].add(currNote);
            }
        }
    }
    public void removeNote(HitNotes remNote, int myLane){
        this.notes[myLane - 1].remove(remNote);
    }
    private int[] getLaneNum(){
        try {
            int[] laneNum = Constants.NOTE_INFO[this.currNoteNum];
            this.currNoteNum++;
            return laneNum;
        } catch (ArrayIndexOutOfBoundsException e) {
            return new int[]{0};
        }
    }
    public void hitLane(int laneNum){
        this.lanes[laneNum - 1].glowUp();
        this.checkIntersection(laneNum);
    }

    public void checkIntersection(int laneNum){
        HitNotes lastNote = null;
        if (!this.notes[laneNum - 1].isEmpty()) {
            lastNote = this.notes[laneNum - 1].get(0);
            if (this.lanes[laneNum - 1].didCollide(lastNote.getX(), lastNote.getY(), lastNote.getWidth())) {
                this.myGame.addPoint();
                lastNote.removeNoteVisual();
                this.removeNote(lastNote, laneNum);
            } else {
                this.myGame.removePoint();
            }
        }

    }

}
