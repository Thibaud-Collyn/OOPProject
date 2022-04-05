package be.ugent.flash.fxml;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class MCSController extends AbstractController {
    public TextFlow textPart;
    public VBox partBox;
    private ArrayList<Part> parts;

    public MCSController(Question question, DataAccessProvider dataAccessProvider) {
        super(question, dataAccessProvider);
        try {
            parts = dataAccessProvider.getDataAccessContext().getPartsDAO().getParts(question.questionId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    //TODO: display image part if necessary

    @Override
    public void initialize() {
        textPart.getChildren().add(new Text(question.textPart()));
        for(Part part:parts) {
            Button button = new Button(part.part());
            partBox.getChildren().add(button);
        }
    }

    @Override
    public String getFXML() {
        return "MCS.fxml";
    }
}
