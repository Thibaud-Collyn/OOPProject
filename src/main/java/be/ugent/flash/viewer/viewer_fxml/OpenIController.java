package be.ugent.flash.viewer.viewer_fxml;

import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;

public class OpenIController extends OpenController{
    public OpenIController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview) {
        super(question, dataAccessProvider, viewerManager, wasCorrect, isPreview);
    }

    @Override
    public String getFXML() {
        return "/OpenI.fxml";
    }

    @Override
    public boolean errorCheck() {
        return text.getText().matches("^-?[0-9]+$");
    }
}
