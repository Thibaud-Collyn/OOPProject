package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class McsPartEditor extends PartEditor{
    private ArrayList<Part> parts;
    private ArrayList<TextArea> newParts = new ArrayList<>();
    protected ArrayList<CheckBox> correctAnswers = new ArrayList<>();

    public McsPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) throws DataAccessException {
        super(question, dap, qEditorBox);
        parts = dap.getDataAccessContext().getPartsDAO().getParts(question.questionId());
    }

//    Laad alle parts in die op moment van opvragen aan de vraag gekoppeld zijn
    @Override
    public void loadParts() {
        VBox vBox = new VBox( new ScrollPane(gridPane));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        TitledPane partBox = new TitledPane("Mogelijke antwoorden", vBox);
        partBox.setCollapsible(false);
        for(int c = 0; c < parts.size(); c++) {
            CheckBox cb = new CheckBox();
            setChecked(cb, c);
            correctAnswers.add(cb);
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

//    hulpmethode om de juiste antwoorden te initialiseren(wordt apart geschreven zodat deze overschreven kan worden voor mr vraag)
    public void setChecked(CheckBox checkBox, int c) {
        checkBox.setSelected(currentQuestion.correctAnswer().equals(c+""));
    }

    public void addPart(ActionEvent event) {
        int row = gridPane.getRowCount();
        CheckBox cB = new CheckBox();
        correctAnswers.add(cB);
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
        correctAnswers.remove(cB);
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
        for (int c = 0; c < correctAnswers.size(); c++) {
            if(correctAnswers.get(c).isSelected()) {
                correctAnswer = c+"";
                answers++;
            }
        }
        if (answers == 1) {
            return correctAnswer;
        } else {
//            TODO: throw error
            throw new IllegalArgumentException("Only one answer may be selected");
        }
    }
}
