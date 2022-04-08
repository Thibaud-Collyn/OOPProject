package be.ugent.flash.factories;

import be.ugent.flash.Question;
import be.ugent.flash.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.fxml.AbstractController;
import be.ugent.flash.fxml.MRController;

public class MRControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect) {
        return new MRController(question, dataAccessProvider, viewerManager, wasCorrect);
    }
}
