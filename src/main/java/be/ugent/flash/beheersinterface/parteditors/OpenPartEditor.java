package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class OpenPartEditor extends PartEditor {
    public GridPane gp;

    public OpenPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) {
        super(question, dap, qEditorBox);
    }

    @Override
    public void loadParts() {
        gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        TextArea text = new TextArea(currentQuestion.correctAnswer());
        text.setWrapText(true);
        text.setPrefHeight(10);
        text.setPrefWidth(400);
        gp.add(new Label("Correct antwoord"), 0, 0);
        gp.add(text, 1, 0);
        qEditorBox.getChildren().add(gp);
    }

    @Override
    public void saveParts() {

    }
}
