package rhythmgame;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PaneOrganizer {
    private BorderPane root;

    public PaneOrganizer(){
        this.root = new BorderPane();
        Pane tilePane = new Pane();
        HBox buttonPane = new HBox();
        tilePane.setPrefSize(Constants.SCENE_WIDTH, Constants.TILEPANE_HEIGHT);
        tilePane.setStyle("-fx-background-color: #1e1b69");
        buttonPane.setPrefSize(Constants.SCENE_WIDTH, Constants.BUTTONPANE_HEIGHT);
        buttonPane.setStyle("-fx-background-color: #809ee0");
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        this.root.setTop(buttonPane);
        this.root.setCenter(tilePane);
        new Game(tilePane, buttonPane);
    }

    public Pane getRoot(){
        return this.root;
    }
}
