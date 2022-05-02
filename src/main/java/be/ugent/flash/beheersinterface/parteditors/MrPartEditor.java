package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class MrPartEditor extends McsPartEditor{

    public MrPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) throws DataAccessException {
        super(question, dap, qEditorBox);
    }

    @Override
    public void setChecked(CheckBox checkBox, int c) {
        checkBox.setSelected(currentQuestion.correctAnswer().charAt(c) == 'T');
    }

    @Override
    public String getCorrectAnswer() {
        StringBuilder correctAnswer = new StringBuilder();
        for (CheckBox answer : correctAnswers) {
            correctAnswer.append(answer.isSelected() ? "T" : "F");
        }
        if (! correctAnswer.toString().matches("^F*$")) {
            return correctAnswer.toString();
        } else {
//            TODO: throw error
            throw new IllegalArgumentException("At least one answer must be selected");
        }
    }
}
