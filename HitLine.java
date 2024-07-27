package rhythmgame;

import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class HitLine {

    private Polygon hitLine;

    public HitLine(Pane tilePane){
        this.hitLine = new Polygon();
        this.hitLine.getPoints().addAll(Constants.HITLINE_COORDS);
        this.hitLine.setStrokeWidth(5.0);
        this.hitLine.setStroke(Color.BLUEVIOLET);
        this.hitLine.setFill(Color.LIGHTPINK);
        Glow glow = new Glow();
        glow.setLevel(1.0);
        this.hitLine.setEffect(glow);
        tilePane.getChildren().addAll(this.hitLine);

    }
}
