package be.ugent.flash;

import be.ugent.flash.db.*;
import be.ugent.flash.factories.*;
import be.ugent.flash.fxml.AbstractController;
import be.ugent.flash.fxml.SceneChanger;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewerManager {
    private ArrayList<Question> questions = new ArrayList<>();
    private final DataAccessProvider dataAccessProvider;
    private HashMap<String, ControllerFactory> typeFactories;
    private final SceneChanger sceneChanger;

    public ViewerManager(String connection, Stage stage) throws DataAccessException{
        this.sceneChanger = new SceneChanger(stage);
        dataAccessProvider = new JDBCDataAccessProvider(connection);
        questions = dataAccessProvider.getDataAccessContext().getQuestionsDAO().getAllQuestions();
        typeFactories = new HashMap<>() {{
            put("mcs", new MCSControllerFactory());
            put("mcc", new MCCControllerFactory());
            put("mci", new MCIControllerFactory());
            put("mr", new MRControllerFactory());
            put("open", new OpenControllerFactory());
            put("openi", new OpenIControllerFactory());
        }};
    }

    public void start() throws IOException {
        while (! questions.isEmpty()) {
            Question currentQuestion = questions.get(0);
            AbstractController controller = typeFactories.get(currentQuestion.questionType()).getController(currentQuestion, dataAccessProvider);
            sceneChanger.changeScene(controller.getFXML(), controller, currentQuestion.title());
        }
    }
}
