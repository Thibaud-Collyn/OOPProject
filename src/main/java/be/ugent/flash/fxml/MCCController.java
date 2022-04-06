package be.ugent.flash.fxml;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MCCController extends AbstractController{
    public VBox partBox;

    private ArrayList<Part> parts;

    public MCCController(Question question, DataAccessProvider dataAccessProvider, boolean wasCorrect) {
        super(question, dataAccessProvider, wasCorrect);
        try {
            parts = dataAccessProvider.getDataAccessContext().getPartsDAO().getParts(question.questionId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        super.initialize();
        for(Part part:parts) {
            Button button = new Button(part.part());
            partBox.getChildren().add(button);
            button.setOnAction(this::answer);
        }
    }

    @Override
    public String getFXML() {
        return "MCC.fxml";
    }

    @Override
    public void answer(ActionEvent event) {
        Button button = (Button)event.getSource();
        correct = button.getText().equals(parts.get(Integer.parseInt(question.correctAnswer())).part());
    }
}
