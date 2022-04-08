package be.ugent.flash;

import be.ugent.flash.db.*;
import be.ugent.flash.factories.*;
import be.ugent.flash.fxml.AbstractController;
import be.ugent.flash.fxml.SceneChanger;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewerManager {
    private ArrayList<Question> questions;
    private final DataAccessProvider dataAccessProvider;
    private final HashMap<String, ControllerFactory> typeFactories;
    private final SceneChanger sceneChanger;
    private boolean wasCorrect = true;

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
        Question currentQuestion = questions.get(0);
        AbstractController controller = typeFactories.get(currentQuestion.questionType()).getController(currentQuestion, dataAccessProvider, this, wasCorrect);
        sceneChanger.changeScene(controller.getFXML(), controller, currentQuestion.title());
    }

    public void nextQuestion(Boolean correct) throws IOException {
        if (correct) {
            questions.remove(0);
            wasCorrect = true;
        } else {
            questions.add(questions.remove(0));
            wasCorrect = false;
        }
        if (questions.isEmpty()) {
            Platform.exit();
        } else {
            Question currentQuestion = questions.get(0);
            AbstractController controller = typeFactories.get(currentQuestion.questionType()).getController(currentQuestion, dataAccessProvider, this, wasCorrect);
            sceneChanger.changeScene(controller.getFXML(), controller, currentQuestion.title());
        }
    }
}
