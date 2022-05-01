package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class McsPartEditor extends PartEditor{
    private ArrayList<Part> parts;
    private ArrayList<TextArea> newParts;

    public McsPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) throws DataAccessException {
        super(question, dap, qEditorBox);
        parts = dap.getDataAccessContext().getPartsDAO().getParts(question.questionId());
        newParts = new ArrayList<>();
    }

//    Laad alle parts in die op moment van opvragen aan de vraag gekoppeld zijn
    @Override
    public void loadParts() {
        VBox vBox = new VBox(gridPane);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        TitledPane partBox = new TitledPane("Mogelijke antwoorden", vBox);
        partBox.setCollapsible(false);
        for(int c = 0; c < parts.size(); c++) {
            CheckBox cb = new CheckBox();
            cb.setSelected(currentQuestion.correctAnswer().equals(c+""));
            TextArea text = new TextArea(parts.get(c).part());
            text.setWrapText(true);
            text.setPrefHeight(20);
            Button button = new Button("X");
            button.setOnAction((e) -> removePart(button, text, cb));
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

    public void addPart(ActionEvent event) {
        int row = gridPane.getRowCount();
        CheckBox cB = new CheckBox();
        gridPane.add(cB, 0, row);
        TextArea text = new TextArea();
        text.setWrapText(true);
        text.setMaxHeight(20);
        gridPane.add(text, 1, row);
        Button button = new Button("X");
        button.setOnAction((e) -> removePart(button, text, cB));
        gridPane.add(button, 2, row);
        newParts.add(text);
    }

    public void removePart(Button button, TextArea text, CheckBox cB) {
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
            for(TextArea text:newParts) {
                dap.getDataAccessContext().getPartsDAO().addTextPart(currentQuestion.questionId(), text.getText());
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCorrectAnswer() {
        String correctAnswer = "";
        int answers = 0;
        for (int c = 0; c < gridPane.getRowCount(); c++) {
            if(checkBoxOnIndex(gridPane, c, 0)) {
                correctAnswer = c+"";
                answers++;
            }
        }
        if (answers == 1) {
            return correctAnswer;
        } else {
//            TODO: throw error
            throw new RuntimeException("Too many answers");
        }
    }

//    Hulpmethode om de button op een bepaalde index te vinden
    public boolean checkBoxOnIndex(GridPane gridPane, int row, int column) {
        for(Node node: gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                CheckBox currentCheckBox = (CheckBox) node;
                return currentCheckBox.isSelected();
            }
        }
        return false;
    }
}
