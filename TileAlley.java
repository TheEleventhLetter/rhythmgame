package rhythmgame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class TileAlley {
    private HitLine hitLine;

    public TileAlley(Pane tilePane, Game game){
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
        this.hitLine = new HitLine(tilePane);

    }
    public void startGame(){

    }
}
