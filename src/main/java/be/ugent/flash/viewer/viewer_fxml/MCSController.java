package be.ugent.flash.viewer.viewer_fxml;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;

public class MCSController extends AbstractController {
    public GridPane partBox;

    private ArrayList<Part> parts;

    public MCSController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview) {
        super(question, dataAccessProvider, viewerManager, wasCorrect, isPreview);
        try {
            parts = dataAccessProvider.getDataAccessContext().getPartsDAO().getParts(question.questionId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        for(int i = 0; i < parts.size(); i++) {
            String letter = ""+(char) (65 + i);
            Button button = new Button(letter);
            button.setOnAction(this::answer);
            button.setUserData(i);
            partBox.add(button, 0, i);
            partBox.add(new TextFlow(new Text(parts.get(i).part())), 1, i);
        }
    }

    @Override
    public String getFXML() {
        return "/MCS.fxml";
    }

    @Override
    public void answer(ActionEvent event) {
        Button button = (Button)event.getSource();
        correct = question.correctAnswer().equals(button.getUserData() + "");
        try {
            super.answer(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
