package be.ugent.flash.viewer.viewer_fxml;

import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class OpenController extends AbstractController{
    public TextField text;
    public Label error;

    public OpenController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview) {
        super(question, dataAccessProvider, viewerManager, wasCorrect, isPreview);
    }

    @Override
    public void initialize() {
        super.initialize();
        text.setOnKeyReleased(this::answer);
    }

    @Override
    public String getFXML() {
        return "/Open.fxml";
    }

    public void answer(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            if(errorCheck()) {
                correct = text.getText().equals(question.correctAnswer());
                try {
                    super.answerKeyPress(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                error.setVisible(true);
            }
        }
    }

    public boolean errorCheck() {
        return !text.getText().equals("");
    }
}
