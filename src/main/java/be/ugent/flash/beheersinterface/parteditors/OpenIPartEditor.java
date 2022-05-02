package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.VBox;

public class OpenIPartEditor extends OpenPartEditor {
    public OpenIPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) {
        super(question, dap, qEditorBox);
    }

    @Override
    public String getCorrectAnswer() {
        if ((! text.getText().equals("")) && text.getText().matches("^-?[0-9]+$")) {
            return text.getText();
        } else {
            throw new IllegalArgumentException("Answer must be a integer");
        }
    }
}
