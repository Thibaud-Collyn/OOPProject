package be.ugent.flash.beheersinterface;

import be.ugent.flash.SceneChanger;
import be.ugent.flash.db.CreateTables;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StartScreenController {
    public BorderPane interfaceScreen;

//    kiest afhangend van het opschrift van de knoppen de juiste methode
    public void change(ActionEvent event) throws IOException, SQLException {
        Button button = (Button)event.getSource();
        SceneChanger sceneChanger = new SceneChanger((Stage) interfaceScreen.getScene().getWindow());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies databank");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sqlite databank", "*.sqlite"));
        if(button.getText().equals("Open een bestaande databank")) {
            openExistingDB();
        } else {
            createNewDB();
        }
    }

    public void openExistingDB() throws SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies databank");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sqlite databank", "*.sqlite"));
        SceneChanger sceneChanger = new SceneChanger((Stage) interfaceScreen.getScene().getWindow());
        File selectedDB = fileChooser.showOpenDialog(interfaceScreen.getScene().getWindow());
        if (selectedDB != null){
            sceneChanger.changeInterfaceScene(selectedDB);
        }
    }

    public void createNewDB() throws SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies databank");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sqlite databank", "*.sqlite"));
        SceneChanger sceneChanger = new SceneChanger((Stage) interfaceScreen.getScene().getWindow());
        File selectedDB = fileChooser.showSaveDialog(interfaceScreen.getScene().getWindow());
        if (selectedDB != null){
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + selectedDB.getPath());
            CreateTables.createQuestionsTable(connection);
            CreateTables.createPartsTable(connection);
            sceneChanger.changeInterfaceScene(selectedDB);
        }
    }

    public void close() {
        Platform.exit();
    }
}
