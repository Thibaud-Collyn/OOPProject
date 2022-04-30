package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class McsPartEditor extends PartEditor{
    public GridPane gp;
    private ArrayList<Part> parts;

    public McsPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) throws DataAccessException {
        super(question, dap, qEditorBox);
        parts = dap.getDataAccessContext().getPartsDAO().getParts(question.questionId());
    }

//    Laad alle parts in die op moment van opvragen aan de vraag gekoppeld zijn
    @Override
    public void loadParts() {
        gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        VBox vBox = new VBox(gp);
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
            gp.add(cb, 0, c);
            gp.add(text, 1, c);
            gp.add(button, 2, c);
        }
        Button addPartBtn = new Button("Optie toevoegen");
        addPartBtn.setOnAction(this::addPart);
        vBox.getChildren().add(addPartBtn);
        qEditorBox.getChildren().add(partBox);
;    }

    public void addPart(ActionEvent event) {
        int row = gp.getRowCount();
        CheckBox cB = new CheckBox();
        gp.add(cB, 0, row);
        TextArea text = new TextArea();
        text.setWrapText(true);
        text.setMaxHeight(20);
        gp.add(text, 1, row);
        Button button = new Button("X");
        button.setOnAction((e) -> removePart(button, text, cB));
        gp.add(button, 2, row);
    }

    public void removePart(Button button, TextArea text, CheckBox cB) {
        gp.getChildren().remove(button);
        gp.getChildren().remove(text);
        gp.getChildren().remove(cB);
    }

    @Override
    public void saveParts() {

    }
}
