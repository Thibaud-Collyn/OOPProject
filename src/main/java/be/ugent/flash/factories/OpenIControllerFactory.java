package be.ugent.flash.factories;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.fxml.AbstractController;

public class OpenIControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider, boolean wasCorrect) {
        return null;
    }
    //TODO: implement getController
}
