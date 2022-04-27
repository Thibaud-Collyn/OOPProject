package be.ugent.flash.beheersinterface;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.db.JDBCDataAccessProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class EditorController extends StartScreenController {
    private final DataAccessProvider dataAccessProvider;
    private ArrayList<Question> questions;
    private final File selectedDB;
    private final Map<String, String> fullTypes = Map.of("mcs", "Meerkeuze (standaard)",
                                                        "mcc", "Meerkeuze (compact)",
                                                        "mci", "Meerkeuze (Afbeelding)",
                                                        "mr", "Meerantwoord",
                                                        "open", "Open (tekst)",
                                                        "openi", "Open (geheel)");

    public BorderPane interfaceScreen;
    public TableView<Question> questionTable;
    public TableColumn<Question, String> titleColumn;
    public TableColumn<Question, String> typeColumn;
    public VBox QEditorBox;
    public GridPane generalItems;
    public HBox imageBox;
    public ImageView image;

    public EditorController(File selectedDB) throws DataAccessException {
        dataAccessProvider = new JDBCDataAccessProvider("jdbc:sqlite:" + selectedDB.getPath());
        this.selectedDB = selectedDB;
        questions = dataAccessProvider.getDataAccessContext().getQuestionsDAO().getAllQuestions();
    }

    public void initialize(){
        QEditorBox.getChildren().add(new Label("(Geen vraag geselecteerd)"));
        ObservableList<Question> Oquestions = FXCollections.observableArrayList(questions);
        titleColumn.setCellValueFactory(question -> new SimpleStringProperty(question.getValue().title()));
        typeColumn.setCellValueFactory(question -> new SimpleStringProperty(fullTypes.get(question.getValue().questionType())));
        questionTable.setItems(Oquestions);
        questionTable.getSelectionModel().selectedItemProperty().addListener(this::showQEditor);
    }

//    Veranderd het rechter paneel van die interface op basis van de geselecteerde vraag
    public void showQEditor(ObservableValue<? extends Question> observable, Question oldValue, Question newValue) {
        QEditorBox.getChildren().clear();
        generalEditor(observable.getValue());
    }

//    Functie om algemene elementen van de vraag(bv. titel, type,...) in de interface te tonen.
    public void generalEditor(Question question) {
        generalItems = new GridPane();
        generalItems.setHgap(10);
        generalItems.setVgap(10);
        generalItems.add(new Label("Titel"),0, 0);
        generalItems.add(new TextField(question.title()),1, 0);
        generalItems.add(new Label("Type"),0, 1);
        generalItems.add(new Label(fullTypes.get(question.questionType())),1, 1);
        generalItems.add(new Label("Tekst"),0, 2);
        generalItems.add(new TextArea(question.textPart()), 1, 2);

//        Het eventueel toevoegen van een image(met bijhorende verwijder en verander button) als die er is of van een voeg image toe button als die er niet is
        generalItems.add(new Label("Afbeelding"), 0, 3);
        imageBox = new HBox();
        imageBox.setSpacing(10);
        if(question.imagePart() == null) {
            image = new ImageView();
            buttonReset(true);
        } else {
            image = new ImageView(new Image(new ByteArrayInputStream(question.imagePart())));
            buttonReset(false);
            imageBox.setUserData(question.imagePart());
        }
        generalItems.add(imageBox, 1, 3);
        QEditorBox.getChildren().add(generalItems);
    }

    public void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies afbeelding");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpeg afbeelding", "*.jpg"), new FileChooser.ExtensionFilter("png afbeelding", "*.png"));
        File newImage = fileChooser.showOpenDialog(interfaceScreen.getScene().getWindow());
        if (newImage != null){
            try {
                image.setImage(new Image(new ByteArrayInputStream(Files.readAllBytes(Path.of(newImage.getPath())))));
                buttonReset(false);
                imageBox.setUserData(Files.readAllBytes(Path.of(newImage.getPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeImage(ActionEvent event) {
        buttonReset(true);
    }

//    Hulpmethode om na een verandering/wijziging van images terug te juist buttons toe te voegen aan de interface.
//    Als de methode true meekrijgt zal er een 'voeg afbeelding toe' knop toegevoegd worden, anders een wijzig en verwijder knop
    public void buttonReset(Boolean noImage) {
        imageBox.getChildren().clear();
        if(noImage) {
            Button toevoegen = new Button("Voeg afbeelding toe...");
            toevoegen.setOnAction(this::chooseImage);
            imageBox.getChildren().add(toevoegen);
            imageBox.setUserData(null);
        } else {
            Button veranderen = new Button("Wijzig...");
            Button verwijder = new Button("Verwijder");
            veranderen.setOnAction(this::chooseImage);
            verwijder.setOnAction(this::removeImage);
            VBox vBox = new VBox(veranderen, verwijder);
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER_LEFT);
            imageBox.getChildren().addAll(image, vBox);
        }
    }
}
