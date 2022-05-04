package be.ugent.flash.beheersinterface;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;

// aparte pop-up klasse voor een vraag toe te voegen
public class AddQuestionPopUp {
    private EditorController controller;
    private final Map<String, String> stdAnswers = Map.of("mcs","0","mcc","0","mci","0","mr","F","open","","openi","0");
    private final Map<String, String> types = Map.of("Meerkeuze (standaard)", "mcs", "Meerkeuze (compact)", "mcc", "Meerkeuze (Afbeelding)", "mci", "Meerantwoord", "mr", "Open (tekst)", "open", "Open (geheel)", "openi");

    public VBox vBox = new VBox();
    public Label title;
    public Button addQuestionBtn = new Button("OK");
    public Button cancelBtn = new Button("Annuleren");
    public TextField questionTitle = new TextField();
    public ComboBox<String> questionTypes = new ComboBox<>();


    public AddQuestionPopUp(EditorController controller) {
        this.controller = controller;
    }

//    Grotendeels opmaak en toewijzing van function calls
    public void initialise() {
        Stage addQPopUp = new Stage();
        addQPopUp.setResizable(false);
        addQPopUp.initModality(Modality.APPLICATION_MODAL);
        vBox.setPrefSize(300, 200);
        vBox.setAlignment(Pos.CENTER);
        questionTypes.getItems().addAll("Meerkeuze (standaard)", "Meerkeuze (compact)", "Meerkeuze (Afbeelding)", "Meerantwoord", "Open (tekst)", "Open (geheel)");
        questionTypes.getSelectionModel().select(0);
        title = new Label("Nieuwe vraag toevoegen");
        title.setFont(Font.font(null, FontWeight.BOLD, 20));
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);
        gp.add(new Label("Titel"), 0, 0);
        gp.add(questionTitle, 1, 0);
        gp.add(new Label("Type"), 0, 1);
        gp.add(questionTypes, 1, 1);
        cancelBtn.setOnAction(e -> addQPopUp.close());
        addQuestionBtn.setOnAction((e) -> addQuestion(addQPopUp));
        HBox hBox = new HBox(addQuestionBtn, cancelBtn);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(title, gp, hBox);
        Scene scene = new Scene(vBox);
        addQPopUp.setScene(scene);
        addQPopUp.showAndWait();
    }

//    voegt een vraag toe aan de databank en laad de beheersinterface opnieuw in met de nu opgeslagen vraag aanwezig
    public void addQuestion(Stage stage) {
        String qType = types.get(questionTypes.getValue());
        DataAccessProvider dap = controller.getDataAccessProvider();
        try {
            Question newQuestion = dap.getDataAccessContext().getQuestionsDAO().createQuestion(questionTitle.getText(), null, null, qType, stdAnswers.get(qType));
            controller.initialize();
            controller.questionTable.getSelectionModel().select(newQuestion);
            stage.close();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
