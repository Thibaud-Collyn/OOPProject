package be.ugent.flash.fxml;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;

public abstract class AbstractController {
    protected Question question;
    protected DataAccessProvider dataAccessProvider;
    protected Boolean correct = null;

    public AbstractController(Question question, DataAccessProvider dataAccessProvider) {
        this.question = question;
        this.dataAccessProvider = dataAccessProvider;
    }

    public abstract void initialize();

    public abstract String getFXML();

    public abstract void answer(ActionEvent event);

    public Boolean isCorrect() {
        return correct;
    }
}
