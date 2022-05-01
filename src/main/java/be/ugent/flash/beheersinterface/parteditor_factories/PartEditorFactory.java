package be.ugent.flash.beheersinterface.parteditor_factories;

import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.parteditors.PartEditor;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.VBox;

public interface PartEditorFactory {
    PartEditor getPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox);
}
