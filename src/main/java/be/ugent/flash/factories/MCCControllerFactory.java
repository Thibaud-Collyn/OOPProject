package be.ugent.flash.factories;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.fxml.AbstractController;
import be.ugent.flash.fxml.MCCController;

public class MCCControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider, boolean wasCorrect) {
        return new MCCController(question, dataAccessProvider, wasCorrect);
    }
}
