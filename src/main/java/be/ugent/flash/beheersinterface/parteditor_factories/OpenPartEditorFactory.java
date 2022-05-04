package be.ugent.flash.beheersinterface.parteditor_factories;

import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.EditorController;
import be.ugent.flash.beheersinterface.parteditors.OpenPartEditor;
import be.ugent.flash.beheersinterface.parteditors.PartEditor;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.VBox;

public class OpenPartEditorFactory implements PartEditorFactory{
    @Override
    public PartEditor getPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox, EditorController editorController) {
        return new OpenPartEditor(question, dap, qEditorBox, editorController);
    }
}
