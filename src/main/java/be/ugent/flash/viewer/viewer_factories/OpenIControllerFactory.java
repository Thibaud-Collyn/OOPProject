package be.ugent.flash.viewer.viewer_factories;

import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.viewer.viewer_fxml.AbstractController;
import be.ugent.flash.viewer.viewer_fxml.OpenIController;

public class OpenIControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect) {
        return new OpenIController(question, dataAccessProvider, viewerManager, wasCorrect);
    }
}
