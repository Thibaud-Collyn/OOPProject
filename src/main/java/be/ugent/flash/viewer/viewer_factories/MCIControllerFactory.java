package be.ugent.flash.viewer.viewer_factories;

import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.viewer.viewer_fxml.AbstractController;
import be.ugent.flash.viewer.viewer_fxml.MCIController;

public class MCIControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect) {
        return new MCIController(question, dataAccessProvider, viewerManager, wasCorrect);
    }
    //TODO: implement getController
}
