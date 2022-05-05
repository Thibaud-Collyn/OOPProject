package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.EditorController;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class MrPartEditor extends McsPartEditor{

    public MrPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox, EditorController editorController) throws DataAccessException {
        super(question, dap, qEditorBox, editorController);
    }

    @Override
    public void setCorrect(CheckBox checkBox, Part part) {
        checkBox.setSelected(currentQuestion.correctAnswer().charAt(parts.indexOf(part)) == 'T');
    }

//    Geeft het huidige juiste antwoord terug en gooit een exception als het antwoord type fout is
    @Override
    public String getCorrectAnswer() {
        StringBuilder correctAnswer = new StringBuilder();
        for (CheckBox answer : correctAnswers) {
            correctAnswer.append(answer.isSelected() ? "T" : "F");
        }
        if (! correctAnswer.toString().matches("^F*$")) {
            return correctAnswer.toString();
        } else if (correctAnswers.isEmpty()) {
            throw new IllegalArgumentException("Antwoordenlijst mag niet leeg zijn");
        } else {
            throw new IllegalArgumentException("Er moet minstens 1 antwoord juist zijn");
        }
    }
}
