package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.VBox;

public class OpenIPartEditor extends OpenPartEditor {
    public OpenIPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) {
        super(question, dap, qEditorBox);
    }

//    Geeft het huidige juiste antwoord terug en gooit een exception als het antwoord type fout is
    @Override
    public String getCorrectAnswer() {
        if ((! text.getText().equals("")) && text.getText().matches("^-?[0-9]+$")) {
            return text.getText();
        } else {
            throw new IllegalArgumentException("Het antwoord moet een geheel getal zijn");
        }
    }
}
