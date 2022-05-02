package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class OpenPartEditor extends PartEditor {
    public TextArea text;

    public OpenPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) {
        super(question, dap, qEditorBox);
    }

    @Override
    public void loadParts() {
        text = new TextArea(currentQuestion.correctAnswer());
        text.setWrapText(true);
        text.setPrefHeight(10);
        text.setPrefWidth(400);
        gridPane.add(new Label("Correct antwoord"), 0, 0);
        gridPane.add(text, 1, 0);
        qEditorBox.getChildren().add(gridPane);
    }

    @Override
    public void saveParts() {
        try {
            dap.getDataAccessContext().getQuestionsDAO().updateCorrectAnswer(currentQuestion.questionId(), text.getText());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCorrectAnswer() {
        if (!text.getText().equals("")) {
            return text.getText();
        } else {
            throw new IllegalArgumentException("Answer can't be empty");
        }
    }
}
