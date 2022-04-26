package be.ugent.flash.beheersinterface;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.db.JDBCDataAccessProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
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

    public TableView<Question> questionTable;
    public TableColumn<Question, String> titleColumn;
    public TableColumn<Question, String> typeColumn;
    public VBox QEditorBox;

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
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Titel"),0, 0);
        gridPane.add(new TextField(question.title()),1, 0);
        gridPane.add(new Label("Type"),0, 1);
        gridPane.add(new Label(fullTypes.get(question.questionType())),1, 1);
        gridPane.add(new Label("Titel"),0, 2);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new TextArea(question.textPart()), 1, 2);
        QEditorBox.getChildren().add(gridPane);
    }
}
