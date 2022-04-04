package be.ugent.flash.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {
    private final Stage stage;

    public SceneChanger(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String fxmlFile, AbstractController controller, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneChanger.class.getResource(fxmlFile));
        fxmlLoader.setController(controller);
        Scene scene = null;
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
