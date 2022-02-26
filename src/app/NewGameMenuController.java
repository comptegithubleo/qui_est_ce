package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class NewGameMenuController implements IGlobalFunctions {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private ChoiceBox<String> choiceTheme;

	public void initialize() {

		choiceTheme.getItems().setAll("theme1", "theme2");
	}


	public void switchScene_Board(ActionEvent event) throws IOException {
		switch_scene(event, "Board", stage, scene);
	}
	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene);
	}
}
