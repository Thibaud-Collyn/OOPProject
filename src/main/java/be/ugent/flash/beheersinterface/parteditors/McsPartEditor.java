package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class McsPartEditor extends PartEditor{
    protected ArrayList<Part> parts;
    private ArrayList<TextArea> newParts = new ArrayList<>();
    protected ArrayList<CheckBox> correctAnswers = new ArrayList<>();

    protected VBox vBox;

    public McsPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox) throws DataAccessException {
        super(question, dap, qEditorBox);
        parts = dap.getDataAccessContext().getPartsDAO().getParts(question.questionId());
        for(Part part: parts) {
            newParts.add(new TextArea(part.part()));
            CheckBox checkBox = new CheckBox();
            setCorrect(checkBox, part);
            correctAnswers.add(checkBox);
        }
    }

//    Hulpmethode om checkbox te checken volgens het juiste antwoord van een Mcs, Mcc vraag(wordt overschreven in Mr omwille van ander soort juist antwoord)
    public void setCorrect(CheckBox checkBox, Part part) {
        checkBox.setSelected(currentQuestion.correctAnswer().equals(parts.indexOf(part) + ""));
    }

//    Laad alle parts in die op moment van opvragen aan de vraag gekoppeld zijn
    @Override
    public void loadParts() {
        Label label = new Label(labels.get(currentQuestion.questionType()));
        label.setFont(new Font(10));
        label.setTextFill(Color.GREY);
        vBox = new VBox( label, new ScrollPane(gridPane));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        TitledPane partBox = new TitledPane("Mogelijke antwoorden", vBox);
        partBox.setCollapsible(false);
        loadGP();
        Button addPartBtn = new Button("Optie toevoegen");
        addPartBtn.setOnAction(this::addPart);
        vBox.getChildren().add(addPartBtn);
        qEditorBox.getChildren().add(partBox);
    }

//    initialiseert de gridpane opnieuw(vooral om lege rows in de gridpane te vermijden)
    public void loadGP() {
        gridPane.getChildren().clear();
        for(int c = 0; c < newParts.size(); c++) {
            TextArea text = newParts.get(c);
            text.setWrapText(true);
            text.setPrefHeight(20);
            Button button = new Button("X");
            CheckBox checkBox = correctAnswers.get(c);
            button.setOnAction((e) -> removePart(button, text, checkBox));
            gridPane.add(checkBox, 0, c);
            gridPane.add(text, 1, c);
            gridPane.add(button, 2, c);
        }
    }

//    Voegt een lege part toe aan de editor
    public void addPart(ActionEvent event) {
        int row = gridPane.getRowCount();
        CheckBox cB = new CheckBox();
        correctAnswers.add(cB);
        gridPane.add(cB, 0, row);
        TextArea text = new TextArea();
        text.setWrapText(true);
        text.setMaxHeight(20);
        gridPane.add(text, 1, row);
        Button button = new Button("X");
        button.setOnAction((e) -> removePart(button, text, cB));
        gridPane.add(button, 2, row);
        newParts.add(text);
    }

//    Verwijdert een part van de editor
    public void removePart(Button button, TextArea text, CheckBox cB) {
        gridPane.getChildren().remove(button);
        gridPane.getChildren().remove(text);
        gridPane.getChildren().remove(cB);
        newParts.remove(text);
        correctAnswers.remove(cB);
        loadGP();
    }

//    Slaat alle parts die zich op dat moment in de editor bevinden op in de databank
    @Override
    public void saveParts() {
        try {
            dap.getDataAccessContext().getQuestionsDAO().updateCorrectAnswer(currentQuestion.questionId(), getCorrectAnswer());
            dap.getDataAccessContext().getPartsDAO().removeParts(currentQuestion.questionId());
            for(TextArea text:newParts) {
                dap.getDataAccessContext().getPartsDAO().addTextPart(currentQuestion.questionId(), text.getText());
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

//    Geeft het huidige juiste antwoord terug en gooit een exception als het antwoord type fout is
    @Override
    public String getCorrectAnswer() {
        String correctAnswer = "";
        int answers = 0;
        for (int c = 0; c < correctAnswers.size(); c++) {
            if(correctAnswers.get(c).isSelected()) {
                correctAnswer = c+"";
                answers++;
            }
        }
        if (answers == 1) {
            return correctAnswer;
        } else {
            throw new IllegalArgumentException("Er moet precies 1 antwoord juist zijn");
        }
    }
}
