package be.ugent.flash.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneChanger {
    private final Stage stage;

    public SceneChanger(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String fxmlFile, AbstractController controller, String title) throws IOException {
        URL url = SceneChanger.class.getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(controller);
        Scene scene = null;
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
