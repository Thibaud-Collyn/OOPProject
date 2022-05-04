package be.ugent.flash.beheersinterface;

import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.parteditor_factories.*;
import be.ugent.flash.beheersinterface.parteditors.*;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.db.JDBCDataAccessProvider;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
import java.util.Arrays;
import java.util.Map;

// Deze klasse erft over van de startScreenController zodat de menuBar makkelijk overgenomen kan worden.
public class EditorController extends StartScreenController {
    private final DataAccessProvider dataAccessProvider;
    private ArrayList<Question> questions;
    private final Map<String, String> fullTypes = Map.of("mcs", "Meerkeuze (standaard)",
                                                        "mcc", "Meerkeuze (compact)",
                                                        "mci", "Meerkeuze (Afbeelding)",
                                                        "mr", "Meerantwoord",
                                                        "open", "Open (tekst)",
                                                        "openi", "Open (geheel)");
    private final Map<String, PartEditorFactory> partFactories = Map.of("mcs", new McsPartEditorFactory(),
                                                                        "mcc", new MccPartEditorFactory(),
                                                                        "mci", new MciPartEditorFactory(),
                                                                        "mr", new MrPartEditorFactory(),
                                                                        "open", new OpenPartEditorFactory(),
                                                                        "openi", new OpenIPartEditorFactory());
    private PartEditor partEditor;
    private Question currentQuestion = null;
    private PreviewPopUp previewPopUp;
    private BooleanProperty wasChanged = new SimpleBooleanProperty(false);

    public BorderPane interfaceScreen;
    public TableView<Question> questionTable;
    public TableColumn<Question, String> titleColumn;
    public TableColumn<Question, String> typeColumn;
    public VBox QEditorBox;
    public GridPane generalItems;
    public HBox imageBox;
    public ImageView image;
    public TextField title;
    public TextArea qText;
    public Button addQuestionButton;
    public Button removeQuestionButton;
    public Button restoreButton;
    public Button preview;
    public Button updateQuestionBtn;

    public EditorController(File selectedDB) {
        dataAccessProvider = new JDBCDataAccessProvider("jdbc:sqlite:" + selectedDB.getPath());
    }

    public DataAccessProvider getDataAccessProvider(){
        return dataAccessProvider;
    }

//    laad de tableView in op basis van de huidige status van de db()
    public void initialize() throws DataAccessException {
//        aantal initiele binds om knoppen te disablen
        questionTable.disableProperty().bind(wasChanged);
        restoreButton.disableProperty().bind(wasChanged.not().or(questionTable.getSelectionModel().selectedItemProperty().isNull()));
        updateQuestionBtn.disableProperty().bind(wasChanged.not().or(questionTable.getSelectionModel().selectedItemProperty().isNull()));

        questions = dataAccessProvider.getDataAccessContext().getQuestionsDAO().getAllQuestions();
        noneSelected();
        ObservableList<Question> Oquestions = FXCollections.observableArrayList(questions);
        titleColumn.setCellValueFactory(question -> new SimpleStringProperty(question.getValue().title()));
        typeColumn.setCellValueFactory(question -> new SimpleStringProperty(fullTypes.get(question.getValue().questionType())));
        questionTable.setItems(Oquestions);
        questionTable.getSelectionModel().selectedItemProperty().addListener(this::showQEditor);

        previewPopUp = new PreviewPopUp();
        preview.setOnMousePressed(event -> showPreview());
        preview.setOnMouseReleased(event -> previewPopUp.closePreview());
        preview.setOnMouseExited(event -> previewPopUp.closePreview());
        preview.disableProperty().bind(questionTable.getSelectionModel().selectedItemProperty().isNull());
        questionChanged(false);
    }

//    Veranderd het rechter paneel van de editor op basis van de geselecteerde vraag
    public void showQEditor(ObservableValue<? extends Question> observable, Question oldValue, Question newValue) {
        if(observable.getValue() != null) {
            generalEditor(observable.getValue());
        } else {
            noneSelected();
        }
    }

//    Functie om algemene elementen van de vraag(bv. titel, type,...) in de interface te tonen.
    public void generalEditor(Question question) {
        QEditorBox.getChildren().clear();
        currentQuestion = question;
        removeQuestionButton.setDisable(false);
        generalItems = new GridPane();
        generalItems.setPadding(new Insets(10, 10, 10, 10));
        generalItems.setHgap(10);
        generalItems.setVgap(10);
        generalItems.add(new Label("Titel"),0, 0);
        title = new TextField(question.title());
        generalItems.add(title,1, 0);
        generalItems.add(new Label("Type"),0, 1);
        generalItems.add(new Label(fullTypes.get(question.questionType())),1, 1);
        generalItems.add(new Label("Tekst"),0, 2);
        qText = new TextArea(question.textPart());
        qText.setWrapText(true);
        generalItems.add(qText, 1, 2);

//        Het eventueel toevoegen van een image(met bijhorende verwijder en verander button) als die er is of van een voeg image toe button als die er niet is
        generalItems.add(new Label("Afbeelding"), 0, 3);
        imageBox = new HBox();
        imageBox.setSpacing(10);
        image = new ImageView();
        image.setFitHeight(125);
        image.setFitWidth(125);
        image.setPreserveRatio(true);
        if(question.imagePart() == null) {
            buttonReset(true);
        } else {
            image.setImage(new Image(new ByteArrayInputStream(question.imagePart())));
            buttonReset(false);
            imageBox.setUserData(question.imagePart());
        }
        generalItems.add(imageBox, 1, 3);
        QEditorBox.getChildren().add(generalItems);
        partEditor = partFactories.get(currentQuestion.questionType()).getPartEditor(currentQuestion, dataAccessProvider, QEditorBox, this);
        partEditor.loadParts();
        title.setOnKeyTyped((e) -> questionChanged(true));
        qText.setOnKeyTyped((e) -> questionChanged(true));
        questionChanged(false);
    }

//    opent een algemene image file chooser
    public void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies afbeelding");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg afbeelding", "*.jpg"), new FileChooser.ExtensionFilter("jpeg afbeelding", "*.jpeg"), new FileChooser.ExtensionFilter("png afbeelding", "*.png"));
        questionChanged(true);
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

    public void questionChanged(boolean changed) {
        wasChanged.set(changed);
    }


//    Hulpmethode om tableView te de-activeren als een image veranderd wordt
    public boolean imageCheck() {
        if(imageBox == null) {
            return true;
        } else {
            byte[] currentImage = (byte[]) imageBox.getUserData();
            return Arrays.equals(currentQuestion.imagePart(), currentImage);
        }
    }

    public void removeImage(ActionEvent event) {
        buttonReset(true);
    }

//    reset het rechtse paneel van de editor als er geen vraag geselecteerd is
    public void noneSelected(){
        QEditorBox.getChildren().clear();
        removeQuestionButton.setDisable(true);
        QEditorBox.getChildren().add(new Label("(Geen vraag geselecteerd)"));
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

//    verwijder geselecteerde vraag en laad de db opnieuw in
    public void removeQuestion(ActionEvent event) throws DataAccessException {
        dataAccessProvider.getDataAccessContext().getPartsDAO().removeParts(currentQuestion.questionId());
        dataAccessProvider.getDataAccessContext().getQuestionsDAO().removeQuestion(currentQuestion.questionId());
        initialize();
    }

    public void addQuestion(ActionEvent event) {
        AddQuestionPopUp popUp = new AddQuestionPopUp(this);
        popUp.initialise();
        questionTable.getSelectionModel().select(null);
        currentQuestion = null;
    }

//    Maakt alle aanpassingen op de huidige vraag ongedaan als die nog niet opgeslagen is.
    public void restore() {
        generalEditor(currentQuestion);
    }

//    Update een bestaande vraag en laad die ge-update vraag meteen in
    public void updateQuestion() throws DataAccessException {
        try {
            currentQuestion = new Question(currentQuestion.questionId(), title.getText(), qText.getText(), (byte[]) imageBox.getUserData(), currentQuestion.questionType(), partEditor.getCorrectAnswer());
            partEditor.saveParts();
            dataAccessProvider.getDataAccessContext().getQuestionsDAO().updateGeneralQuestion(currentQuestion.questionId(), currentQuestion.title(), currentQuestion.textPart(), currentQuestion.imagePart());
            initialize();
            questionTable.getSelectionModel().select(currentQuestion);
        } catch (IllegalArgumentException e) {
            ErrorPopUp popUp = new ErrorPopUp();
            popUp.display(e.getMessage());
        }
    }

    public void showPreview() {
        Question previewQuestion = new Question(currentQuestion.questionId(), title.getText(), qText.getText(), (byte[]) imageBox.getUserData(), currentQuestion.questionType(), null);
        previewPopUp.showPreview(previewQuestion, dataAccessProvider, partEditor.getParts());
    }
}