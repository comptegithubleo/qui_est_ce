package app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class NewGameMenuController extends SceneController implements Initializable {

	@FXML
	private ChoiceBox<String> choiceTheme;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		choiceTheme.getItems().setAll("theme1", "theme2");
	}
}
