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
    private ArrayList<Part> parts;
    private ArrayList<TextField> newParts;

    public MccPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) throws DataAccessException {
        super(question, dap, qEditorBox);
        parts = dap.getDataAccessContext().getPartsDAO().getParts(question.questionId());
        newParts = new ArrayList<>();
    }

    @Override
    public void loadParts() {
        VBox vBox = new VBox(new ScrollPane(gridPane));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        TitledPane partBox = new TitledPane("Mogelijke antwoorden", vBox);
        partBox.setCollapsible(false);
        for(int c = 0; c < parts.size(); c++) {
            CheckBox cb = new CheckBox();
            cb.setSelected(currentQuestion.correctAnswer().equals(c+""));
            correctAnswers.add(cb);
            TextField text = new TextField(parts.get(c).part());
            text.setPrefHeight(20);
            text.setPrefWidth(90);
            Button button = new Button("X");
            button.setOnAction((e) -> removeCompactPart(button, text, cb));
            gridPane.add(cb, 0, c);
            gridPane.add(text, 1, c);
            gridPane.add(button, 2, c);
            newParts.add(text);
        }
        Button addPartBtn = new Button("Optie toevoegen");
        addPartBtn.setOnAction(this::addPart);
        vBox.getChildren().add(addPartBtn);
        qEditorBox.getChildren().add(partBox);
    }

    @Override
    public void addPart(ActionEvent event) {
        int row = gridPane.getRowCount();
        CheckBox cB = new CheckBox();
        gridPane.add(cB, 0, row);
        TextField text = new TextField();
        text.setPrefHeight(20);
        text.setPrefWidth(75);
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
