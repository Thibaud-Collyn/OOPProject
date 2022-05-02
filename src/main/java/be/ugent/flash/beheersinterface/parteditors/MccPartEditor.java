package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MccPartEditor extends McsPartEditor {
    private ArrayList<TextField> newParts = new ArrayList<>();

    public MccPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) throws DataAccessException {
        super(question, dap, qEditorBox);
        for(Part part: parts) {
            newParts.add(new TextField(part.part()));
        }
    }

    @Override
    public void loadGP() {
        gridPane.getChildren().clear();
        for(int c = 0; c < newParts.size(); c++) {
            TextField text = newParts.get(c);
            text.setPrefHeight(20);
            text.setPrefWidth(90);
            Button button = new Button("X");
            CheckBox checkBox = correctAnswers.get(c);
            button.setOnAction((e) -> removeCompactPart(button, text, checkBox));
            gridPane.add(checkBox, 0, c);
            gridPane.add(text, 1, c);
            gridPane.add(button, 2, c);
        }
    }

    @Override
    public void addPart(ActionEvent event) {
        int row = gridPane.getRowCount();
        CheckBox cB = new CheckBox();
        correctAnswers.add(cB);
        gridPane.add(cB, 0, row);
        TextField text = new TextField();
        text.setPrefHeight(20);
        text.setPrefWidth(90);
        gridPane.add(text, 1, row);
        Button button = new Button("X");
        button.setOnAction((e) -> removeCompactPart(button, text, cB));
        gridPane.add(button, 2, row);
        newParts.add(text);
    }

    public void removeCompactPart(Button button, TextField text, CheckBox cB) {
        gridPane.getChildren().remove(button);
        gridPane.getChildren().remove(text);
        gridPane.getChildren().remove(cB);
        newParts.remove(text);
        correctAnswers.remove(cB);
        loadGP();
    }

    @Override
    public void saveParts() {
        try {
            dap.getDataAccessContext().getQuestionsDAO().updateCorrectAnswer(currentQuestion.questionId(), getCorrectAnswer());
            dap.getDataAccessContext().getPartsDAO().removeParts(currentQuestion.questionId());
            for(TextField text:newParts) {
                dap.getDataAccessContext().getPartsDAO().addTextPart(currentQuestion.questionId(), text.getText());
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
