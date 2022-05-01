package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class PartEditor {
    protected VBox qEditorBox;
    protected Question currentQuestion;
    protected DataAccessProvider dap;
    protected GridPane gridPane;

    public PartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) {
        this.qEditorBox = qEditorBox;
        this.currentQuestion = question;
        this.dap = dap;
        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
    }

    public abstract void loadParts();
    public abstract void saveParts();

    public abstract String getCorrectAnswer();
}
