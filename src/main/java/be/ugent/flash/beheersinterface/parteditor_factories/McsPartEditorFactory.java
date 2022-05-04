package be.ugent.flash.beheersinterface.parteditor_factories;

import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.EditorController;
import be.ugent.flash.beheersinterface.parteditors.McsPartEditor;
import be.ugent.flash.beheersinterface.parteditors.PartEditor;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.VBox;

public class McsPartEditorFactory implements PartEditorFactory{
    public PartEditor getPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox, EditorController editorController) {
        try {
            return new McsPartEditor(question, dap, qEditorBox, editorController);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
