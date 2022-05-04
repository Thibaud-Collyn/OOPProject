package be.ugent.flash.beheersinterface.parteditors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

// aparte error pop-up klasse (bevat grotendeels opmaak van de pop-up)
public class ErrorPopUp {
    public VBox box = new VBox();
    public Label errorMessage = new Label();
    public Button ok = new Button("ok");

    public void display(String error) {
        Stage popUp = new Stage();
        popUp.setResizable(false);
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("ERROR");
        box.setPrefSize(400, 90);
        box.setSpacing(10);
        errorMessage.setText(error);
        errorMessage.setTextFill(Color.WHITE);
        errorMessage.setFont(Font.font(null, FontWeight.BOLD, 18));
        errorMessage.setBackground(new Background(new BackgroundFill(Color.web("#ff6961"), new CornerRadii(2), Insets.EMPTY)));
        ok.setFont(Font.font(null, FontWeight.BOLD, 14));
        ok.setOnAction(e -> popUp.close());
        box.getChildren().addAll(errorMessage, ok);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box);
        popUp.setScene(scene);
        popUp.showAndWait();
    }
}
