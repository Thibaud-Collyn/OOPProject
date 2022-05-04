package be.ugent.flash.viewer.viewer_fxml;

import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;

import java.util.ArrayList;

public class OpenIController extends OpenController{
    public OpenIController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview, ArrayList<?> cstmparts) {
        super(question, dataAccessProvider, viewerManager, wasCorrect, isPreview, cstmparts);
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
