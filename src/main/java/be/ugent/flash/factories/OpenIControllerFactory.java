package be.ugent.flash.factories;

import be.ugent.flash.Question;
import be.ugent.flash.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.fxml.AbstractController;
import be.ugent.flash.fxml.OpenIController;

public class OpenIControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect) {
        return new OpenIController(question, dataAccessProvider, viewerManager, wasCorrect);
    }
}
