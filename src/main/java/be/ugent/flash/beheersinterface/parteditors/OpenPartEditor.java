package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.EditorController;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class OpenPartEditor extends PartEditor {
    public TextArea text;

    public OpenPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox, EditorController editorController) {
        super(question, dap, qEditorBox, editorController);
    }

//    Laad text area met huidig correct antwoord in
    @Override
    public void loadParts() {
        text = new TextArea(currentQuestion.correctAnswer());
        text.setWrapText(true);
        text.setPrefHeight(10);
        text.setPrefWidth(400);
        gridPane.add(new Label("Correct antwoord"), 0, 0);
        gridPane.add(text, 1, 0);
        qEditorBox.getChildren().add(gridPane);
        text.setOnKeyTyped((e) -> editorController.questionChanged(true));
    }

//    Slaat het gegeven juiste antwoord op in de databank
    @Override
    public void saveParts() {
        try {
            dap.getDataAccessContext().getQuestionsDAO().updateCorrectAnswer(currentQuestion.questionId(), text.getText());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

//    Geeft het huidige juiste antwoord terug en gooit een exception als het antwoord type fout is
    @Override
    public String getCorrectAnswer() {
        if (!text.getText().equals("")) {
            return text.getText();
        } else {
            throw new IllegalArgumentException("Het antwoord mag niet leeg zijn");
        }
    }
}
