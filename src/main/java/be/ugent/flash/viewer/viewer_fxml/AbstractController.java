package be.ugent.flash.viewer.viewer_fxml;

import be.ugent.flash.Question;
import be.ugent.flash.viewer.ViewerManager;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractController {
    public TextFlow textPart;
    public VBox card;
    public ImageView image;
    public Label prevQuestion;

    protected Question question;
    protected DataAccessProvider dataAccessProvider;
    protected boolean correct;
    protected ViewerManager viewerManager;
    protected boolean wasCorrect;
    protected boolean isPreview;

//    De abstract controller parameters spreken grotendeels voor zichzelf behalve misschien isPreview en cstmParts(custom parts)
//    isPreview zegt de vraag of hij zijn knoppen al dan niet moet disablen(voor bij een preview in de beheersinterface)
//    en cstmParts is een lijst met ofwel parts of image parts die een niet opgeslagen lijst aan parts voorstelt(wat nodig is bij het aanmaken van en preview waar nog niet alle parts persistent opgeslagen zijn in de db)
    public AbstractController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect, boolean isPreview, ArrayList<?> cstmParts) {
        this.question = question;
        this.dataAccessProvider = dataAccessProvider;
        this.wasCorrect = wasCorrect;
        this.viewerManager = viewerManager;
        this.isPreview = isPreview;
    }

//    initialiseert algemene delen van de vraag zoals of de vorige vraag juist was of een eventueele afbeelding
    public void initialize() {
        prevQuestion.setVisible(! wasCorrect);
        textPart.getChildren().add(new Text(question.textPart()));
        try {
            image.setImage(new Image(new ByteArrayInputStream(question.imagePart())));
        } catch (NullPointerException e) {
            card.getChildren().remove(image);
        }
    }

//    disabled de vraag(nodig bij preview)
    public void disableView() {
        card.setDisable(isPreview);
    }

//    geeft de fxml file terug van de huidige kaart
    public abstract String getFXML();

//    roept de viewermanager op om de volgende vraag te tonen op basis van het gegeven antwoord
    public void answer(ActionEvent event) throws IOException {
        viewerManager.nextQuestion(correct);
    }

    public void answerKeyPress(KeyEvent event) throws IOException {
        viewerManager.nextQuestion(correct);
    }

    public Boolean isCorrect() {
        return correct;
    }
}
