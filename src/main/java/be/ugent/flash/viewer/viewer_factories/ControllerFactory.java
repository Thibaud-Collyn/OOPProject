package be.ugent.flash.viewer.viewer_factories;

import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.viewer.viewer_fxml.AbstractController;

import java.util.ArrayList;

//  Algemene controller klasse voor vraag controllers
public interface ControllerFactory {
    AbstractController getController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview, ArrayList<?> cstmparts);
}
