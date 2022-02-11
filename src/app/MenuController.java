package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController implements Initializable, IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void switchScene_NewGameMenu(ActionEvent event) throws IOException {
		switch_scene(event, "NewGameMenu", stage, scene);
	}
	public void switchScene_Options(ActionEvent event) throws IOException {
		switch_scene(event, "Options", stage, scene);
	}
	public void quitGame(ActionEvent event) {
		Platform.exit();
	}
}
