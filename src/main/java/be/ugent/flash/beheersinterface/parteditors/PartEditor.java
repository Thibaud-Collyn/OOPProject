package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.EditorController;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

// abstracte klasse voor part specifieke editors
public abstract class PartEditor {
    protected VBox qEditorBox;
    protected Question currentQuestion;
    protected DataAccessProvider dap;
    protected final Map<String, String> labels = Map.of("mcs", "Antwoorden worden onder elkaar getoond - met knoppen A, B, C,...",
                                                                        "mcc", "Antwoorden worden op knoppen naast elkaar getoond.",
                                                                        "mr", "Antwoorden worden onder elkaar getoond met aanvink knop.",
                                                                        "mci", "Antwoorden worden als foto's op knoppen afgebeeld(dubbel klik op een afbeelding om hem te veranderen).");

    protected GridPane gridPane;
    protected EditorController editorController;

    public PartEditor(Question question, DataAccessProvider dap, VBox qEditorBox, EditorController editorController) {
        this.qEditorBox = qEditorBox;
        this.currentQuestion = question;
        this.dap = dap;
        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        this.editorController = editorController;
    }

//    methode om alle parts in te laden in de editor
    public abstract void loadParts();

//    methode voor een lijst met parts alle huidig toegevoegde en aanwezige terug te krijgen(indien die er zijn)
    public ArrayList<?> getParts() {
        return null;
    }

//    methode om de parts op te slaan in de databank
    public abstract void saveParts();

//    methode om het huidige juiste antwoord op te vragen en te checken of het wel een geldig antwoord is
    public abstract String getCorrectAnswer();
}
