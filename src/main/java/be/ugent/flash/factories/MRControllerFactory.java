package be.ugent.flash.factories;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.fxml.AbstractController;

public class MRControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider) {
        return null;
    }
    //TODO: implement getController
}
