package be.ugent.flash.factories;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.fxml.MCSController;

public class MCSControllerFactory {
    public MCSController getMCSController(Question question, DataAccessProvider dataAccessProvider) {
        return new MCSController(question, dataAccessProvider);
    }
}
