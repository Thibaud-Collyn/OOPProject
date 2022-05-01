package be.ugent.flash.beheersinterface.parteditor_factories;

import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.parteditors.OpenIPartEditor;
import be.ugent.flash.beheersinterface.parteditors.PartEditor;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.VBox;

public class OpenIPartEditorFactory implements PartEditorFactory{
    @Override
    public PartEditor getPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) {
        return new OpenIPartEditor(question, dap, qEditorBox);
    }
}
