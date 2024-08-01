package rhythmgame;

import javafx.animation.FillTransition;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class HitLine {

    private Polygon hitLine;

    public HitLine(Pane tilePane, int laneNum){
        this.hitLine = new Polygon();
        switch (laneNum){
            case 1:
                this.hitLine.getPoints().addAll(Constants.HITLINE1_COORDS);
                break;
            case 2:
                this.hitLine.getPoints().addAll(Constants.HITLINE2_COORDS);
                break;
            case 3:
                this.hitLine.getPoints().addAll(Constants.HITLINE3_COORDS);
                break;
            case 4:
                this.hitLine.getPoints().addAll(Constants.HITLINE4_COORDS);
                break;
        }
        this.hitLine.setStrokeWidth(5.0);
        this.hitLine.setStroke(Color.BLUEVIOLET);
        this.hitLine.setFill(Color.LIGHTPINK);



        tilePane.getChildren().addAll(this.hitLine);

    }
    public void glowUp(){
        FillTransition ft = new FillTransition(Duration.millis(300), this.hitLine, Color.MEDIUMPURPLE, Color.LIGHTPINK);
        ft.play();
    }
    public boolean didCollide(double xSpot, double ySpot, double width){
        return this.hitLine.intersects(xSpot, ySpot, width, Constants.NOTE_HEIGHT);
    }
}
