package be.ugent.flash.viewer.viewer_factories;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.viewer.viewer_fxml.AbstractController;
import be.ugent.flash.viewer.viewer_fxml.MCCController;

import java.util.ArrayList;

public class MCCControllerFactory implements ControllerFactory{
    @Override
    public AbstractController getController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview, ArrayList<?> cstmparts) {
        return new MCCController(question, dataAccessProvider, viewerManager, wasCorrect, isPreview, (ArrayList<Part>) cstmparts);
    }
}
