package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class OptionsController extends Game implements IGlobalFunctions{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    ChoiceBox<String> choice_difficulty;
    @FXML
    Button apply;


    public void initialize() {
        choice_difficulty.getItems().setAll("Easy", "Normal", "Advanced");
		choice_difficulty.setValue(game.getDifficulty());
    }

    public void setDifficulty()
    {
        if (choice_difficulty.getValue() != null)
        {
        	game.setDifficulty(choice_difficulty.getValue());
        }
    }

    public void switchScene_Menu(ActionEvent event) throws IOException {
        switch_scene(event, "Menu", stage, scene);
    }

}