package be.ugent.flash.fxml;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.ViewerManager;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MRController extends AbstractController{
    public GridPane partBox;

    private ArrayList<Part> parts;
    private char[] allAnswers;

    public MRController(Question question, DataAccessProvider dataAccessProvider, ViewerManager viewerManager, boolean wasCorrect) {
        super(question, dataAccessProvider, viewerManager, wasCorrect);
        try {
            parts = dataAccessProvider.getDataAccessContext().getPartsDAO().getParts(question.questionId());
            allAnswers = new char[parts.size()];
            Arrays.fill(allAnswers, 'F');
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        for(int i = 0; i < parts.size(); i++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setUserData((int) i);
            checkBox.setOnAction(this::addAnswer);
            partBox.add(checkBox, 0, i);
            partBox.add(new TextFlow(new Text(parts.get(i).part())), 1, i);
        }
        Button button = new Button("OK");
        card.getChildren().add(button);
        button.setOnAction(this::answer);
    }

    @Override
    public String getFXML() {
        return "/MR.fxml";
    }

    @Override
    public void answer(ActionEvent event) {
        String correctAnswer = question.correctAnswer();
        boolean b = true;
        int i = 0;
        while (b && i < parts.size()){
            if(! (allAnswers[i] == correctAnswer.charAt(i))) {
                b = false;
            }
            i++;
        }
        correct = b;
        try {
            super.answer(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAnswer(ActionEvent event) {
        CheckBox checkBox = (CheckBox)event.getSource();
        if(checkBox.isSelected()) {
            allAnswers[(int) checkBox.getUserData()] = 'T';
        } else {
            allAnswers[(int) checkBox.getUserData()] = 'F';
        }
    }
}
