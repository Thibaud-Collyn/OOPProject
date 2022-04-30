package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.VBox;

public class OpenPartEditorFactory implements PartEditorFactory{
    @Override
    public PartEditor getPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) {
        return new OpenPartEditor(question, dap, qEditorBox);
    }
}
