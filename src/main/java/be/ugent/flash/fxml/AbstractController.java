package be.ugent.flash.fxml;

import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.ByteArrayInputStream;

public abstract class AbstractController {
    public TextFlow textPart;
    public VBox card;
    public ImageView image;
    public Label prevQuestion;

    protected Question question;
    protected DataAccessProvider dataAccessProvider;
    protected Boolean correct = null;
    protected boolean wasCorrect;

    public AbstractController(Question question, DataAccessProvider dataAccessProvider, boolean wasCorrect) {
        this.question = question;
        this.dataAccessProvider = dataAccessProvider;
        this.wasCorrect = wasCorrect;
    }

    public void initialize() {
        prevQuestion.setVisible(wasCorrect);
        textPart.getChildren().add(new Text(question.textPart()));
        try {
            image.setImage(new Image(new ByteArrayInputStream(question.imagePart())));
        } catch (NullPointerException e) {
            card.getChildren().remove(image);
        }
    }

    public abstract String getFXML();

    public abstract void answer(ActionEvent event);

    public Boolean isCorrect() {
        return correct;
    }
}
