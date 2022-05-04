package be.ugent.flash;

import be.ugent.flash.beheersinterface.EditorController;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.viewer.viewer_fxml.AbstractController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SceneChanger {
    private final Stage stage;

    public SceneChanger(Stage stage) {
        this.stage = stage;
    }

//    regelt verandering van de viewer scenes
    public void changeViewerScene(String fxmlFile, AbstractController controller, String title) throws IOException {
        URL url = SceneChanger.class.getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(controller);
        Scene scene = null;
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        controller.disableView();
    }

//    regelt verandering van de beheersinterface scenes
    public void changeInterfaceScene(File selectedDB) throws IOException, DataAccessException {
        URL url = EditorController.class.getResource("/Editor.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setController(new EditorController(selectedDB));
        Scene scene = null;
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle(selectedDB.getName());
        stage.show();
    }
}
