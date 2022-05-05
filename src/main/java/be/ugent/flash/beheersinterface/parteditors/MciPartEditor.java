package be.ugent.flash.beheersinterface.parteditors;

import be.ugent.flash.ImagePart;
import be.ugent.flash.Part;
import be.ugent.flash.Question;
import be.ugent.flash.beheersinterface.EditorController;
import be.ugent.flash.db.DataAccessException;
import be.ugent.flash.db.DataAccessProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;

public class MciPartEditor extends PartEditor{
    private ArrayList<ImageView> newParts = new ArrayList<>();
    private ArrayList<CheckBox> correctAnswers = new ArrayList<>();

    public VBox vBox;

    public MciPartEditor(Question question, DataAccessProvider dap, VBox qEditorBox, EditorController editorController) throws DataAccessException {
        super(question, dap, qEditorBox, editorController);
        ArrayList<ImagePart> parts = dap.getDataAccessContext().getPartsDAO().getImageParts(currentQuestion.questionId());
        if (parts.isEmpty()) {//Laad de vragen lijst opnieuw in met een lege vraag(voor nieuw toegevoegde vragen)
            InputStream is = getClass().getResourceAsStream("/new_part_temp.png");
            try {
                dap.getDataAccessContext().getPartsDAO().addImagePart(question.questionId(), is.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            parts = dap.getDataAccessContext().getPartsDAO().getImageParts(question.questionId());
        }
        for (ImagePart part: parts) {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(part.part())));
            imageView.setUserData(part.part());
            imageView.setFitWidth(90);
            imageView.setFitWidth(90);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    if(mouseEvent.getButton() == MouseButton.PRIMARY){
                        if(mouseEvent.getClickCount() == 2) {
                            changeImage(imageView);
                        }
                    }
                }
            });
            newParts.add(imageView);
            CheckBox cB = new CheckBox();
            cB.setSelected(currentQuestion.correctAnswer().equals(parts.indexOf(part) + ""));
            cB.setOnAction((e) -> editorController.questionChanged(true));
            correctAnswers.add(cB);
        }
    }

    public void changeImage(ImageView image) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies afbeelding");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg afbeelding", "*.jpg"), new FileChooser.ExtensionFilter("jpeg afbeelding", "*.jpeg"), new FileChooser.ExtensionFilter("png afbeelding", "*.png"));
        editorController.questionChanged(true);
        File newImage = fileChooser.showOpenDialog(qEditorBox.getScene().getWindow());
        if (newImage != null){
            try {
                image.setImage(new Image(new ByteArrayInputStream(Files.readAllBytes(Path.of(newImage.getPath())))));
                image.setUserData(Files.readAllBytes(Path.of(newImage.getPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

    public void addPart(ActionEvent event) {
        int row = gridPane.getRowCount();
        CheckBox cB = new CheckBox();
        correctAnswers.add(cB);
        gridPane.add(cB, 0, row);
        ImageView imageView = new ImageView();
        InputStream is = getClass().getResourceAsStream("/new_part_temp.png");
        try {
            byte[] bytes = is.readAllBytes();
            imageView.setImage(new Image(new ByteArrayInputStream(bytes)));
            imageView.setUserData(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                if(mouseEvent.getButton() == MouseButton.PRIMARY){
                    if(mouseEvent.getClickCount() == 2) {
                        changeImage(imageView);
                    }
                }
            }
        });
        imageView.setFitWidth(90);
        imageView.setFitWidth(90);
        imageView.setPreserveRatio(true);
        gridPane.add(imageView, 1, row);
        Button button = new Button("X");
        button.setOnAction((e) -> removePart(button, imageView, cB));
        gridPane.add(button, 2, row);
        newParts.add(imageView);
        editorController.questionChanged(true);
    }

    public void removePart(Button button, ImageView imageView, CheckBox cB) {
        gridPane.getChildren().remove(button);
        gridPane.getChildren().remove(imageView);
        gridPane.getChildren().remove(cB);
        newParts.remove(imageView);
        correctAnswers.remove(cB);
        loadGP();
        editorController.questionChanged(true);
    }

    public void loadGP() {
        gridPane.getChildren().clear();
        for(int c = 0; c < newParts.size(); c++) {
            ImageView imageView = newParts.get(c);
            imageView.setFitWidth(90);
            imageView.setFitWidth(90);
            imageView.setPreserveRatio(true);
            Button button = new Button("X");
            CheckBox checkBox = correctAnswers.get(c);
            checkBox.setOnAction((e) -> editorController.questionChanged(true));
            button.setOnAction((e) -> removePart(button, imageView, checkBox));
            gridPane.add(checkBox, 0, c);
            gridPane.add(imageView, 1, c);
            gridPane.add(button, 2, c);
        }
    }

    @Override
    public ArrayList<?> getParts() {
        ArrayList<ImagePart> currentparts = new ArrayList<>();
        for(int c = 0; c < newParts.size(); c++) {
            currentparts.add(new ImagePart(c, currentQuestion.questionId(), (byte[]) newParts.get(c).getUserData()));
        }
        return currentparts;
    }

    @Override
    public void saveParts() {
        try {
            dap.getDataAccessContext().getQuestionsDAO().updateCorrectAnswer(currentQuestion.questionId(), getCorrectAnswer());
            dap.getDataAccessContext().getPartsDAO().removeParts(currentQuestion.questionId());
            for(ImageView imageView:newParts) {
                dap.getDataAccessContext().getPartsDAO().addImagePart(currentQuestion.questionId(), (byte[]) imageView.getUserData());
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
        } else if (correctAnswers.isEmpty()) {
            throw new IllegalArgumentException("Antwoordenlijst mag niet leeg zijn");
        } else {
            throw new IllegalArgumentException("Er moet precies 1 antwoord juist zijn");
        }
    }
}
