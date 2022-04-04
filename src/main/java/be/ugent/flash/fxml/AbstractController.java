package be.ugent.flash.fxml;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;

public abstract class AbstractController {
    protected Question question;
    protected DataAccessProvider dataAccessProvider;

    public AbstractController(Question question, DataAccessProvider dataAccessProvider) {
        this.question = question;
        this.dataAccessProvider = dataAccessProvider;
    }

    public abstract void initialize();

    public boolean isCorrect(String answer) {
        return answer.equals(question.correctAnswer());
    }
}
