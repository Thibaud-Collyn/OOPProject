package be.ugent.flash.factories;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.fxml.AbstractController;

public interface ControllerFactory {
    AbstractController getController(Question question, DataAccessProvider dataAccessProvider);
}
