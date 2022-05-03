package be.ugent.flash.viewer.viewer_fxml;

import be.ugent.flash.ImagePart;
import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MCIController extends AbstractController{
    public HBox partBox;

    private ArrayList<ImagePart> parts;

    public MCIController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview) {
        super(question, dataAccessProvider, viewerManager, wasCorrect, isPreview);
        try {
            parts = dataAccessProvider.getDataAccessContext().getPartsDAO().getImageParts(question.questionId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        super.initialize();
        for(int i = 0; i < parts.size(); i++) {
            ImageView image = new ImageView(new Image(new ByteArrayInputStream(parts.get(i).part())));
            Button button = new Button();
            button.setGraphic(image);
            button.setUserData(i);
            partBox.getChildren().add(button);
            button.setOnAction(this::answer);
        }
    }

    @Override
    public String getFXML() {
        return "/MCI.fxml";
    }

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
