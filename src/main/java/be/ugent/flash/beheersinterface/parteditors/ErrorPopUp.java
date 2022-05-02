package be.ugent.flash.beheersinterface.parteditors;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorPopUp {
    public VBox box = new VBox();
    public Label errorMessage = new Label();
    public Button ok = new Button("ok");

    public void display(String error) {
        Stage popUp = new Stage();
        popUp.setResizable(false);
        popUp.initModality(Modality.APPLICATION_MODAL);
        box.setPrefSize(400, 100);
        errorMessage.setText(error);
        errorMessage.setTextFill(Color.RED);
        errorMessage.setFont(Font.font(18));
        ok.setFont(Font.font(14));
        ok.setOnAction(e -> popUp.close());
        box.getChildren().addAll(errorMessage, ok);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box);
        popUp.setScene(scene);
        popUp.showAndWait();
    }
}
