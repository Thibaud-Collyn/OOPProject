package be.ugent.flash.beheersinterface;

import be.ugent.flash.Question;
import be.ugent.flash.SceneChanger;
import be.ugent.flash.db.DataAccessProvider;
import be.ugent.flash.viewer.viewer_factories.*;
import be.ugent.flash.viewer.viewer_fxml.AbstractController;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class PreviewPopUp {
    private final Map<String, ControllerFactory> typeFactories = Map.of("mcs", new MCSControllerFactory(),
                                                                        "mcc", new MCCControllerFactory(),
                                                                        "mci", new MCIControllerFactory(),
                                                                        "mr", new MRControllerFactory(),
                                                                        "open", new OpenControllerFactory(),
                                                                        "openi", new OpenIControllerFactory());

    public Stage previewStage = new Stage();

//    Laat een preview zien van een opgegeven vraag met niet opgeslagen parts(cstmparts)
    public void showPreview(Question question, DataAccessProvider dap, ArrayList<?> cstmparts){
        SceneChanger sceneChanger = new SceneChanger(previewStage);
        AbstractController controller = typeFactories.get(question.questionType()).getController(question, dap, null, true, true, cstmparts);
        try {
            sceneChanger.changeViewerScene(controller.getFXML(), controller, question.title());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closePreview() {
        previewStage.close();
    }

}
