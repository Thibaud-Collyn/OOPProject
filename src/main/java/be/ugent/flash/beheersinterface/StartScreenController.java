package be.ugent.flash.beheersinterface;

import be.ugent.flash.SceneChanger;
import be.ugent.flash.db.DataAccessException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StartScreenController {
    public BorderPane interfaceScreen;

    public void change(ActionEvent event) throws IOException, DataAccessException {
        Button button = (Button)event.getSource();
        File selectedDB = null;
        if(button.getText().equals("Open een bestaande databank")) {
            openExistingDB();
        } else {
            //TODO: make create new db in scenechanger and implement in separate method
        }
    }

    public void openExistingDB() throws IOException, DataAccessException {
        SceneChanger sceneChanger = new SceneChanger((Stage) interfaceScreen.getScene().getWindow());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies databank");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sqlite databank", "*.sqlite"));
        File selectedDB = fileChooser.showOpenDialog(interfaceScreen.getScene().getWindow());
        if (selectedDB != null){
            sceneChanger.changeInterfaceScene(selectedDB);
        }
    }

    public void createNewDB() {
        //TODO: implement
    }

    public void close() {
        Platform.exit();
    }
}
