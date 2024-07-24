package rhythmgame;

import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class HitLine {

    private Line hitLine;

    public HitLine(Pane tilePane){
        this.hitLine = new Line(300.00, 300.0, 1300.0, 300.0 );
        this.hitLine.setStrokeWidth(5.0);
        this.hitLine.setStroke(Color.BLUEVIOLET);
        Glow glow = new Glow();
        glow.setLevel(1.0);
        this.hitLine.setEffect(glow);
        tilePane.getChildren().addAll(this.hitLine);

    }
}
