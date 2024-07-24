package rhythmgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage){
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        scene.setFill(Color.WHITE);
        stage.setScene(scene);
        stage.setTitle("Rhythm Game");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
