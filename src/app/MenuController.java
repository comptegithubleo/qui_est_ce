package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;

public class MenuController implements Initializable, IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;

	private String difficulty;

	@FXML
	private ImageView img_background = new ImageView();
	@FXML
	private ImageView img_flame = new ImageView();
	@FXML
	private ImageView img_play = new ImageView();
	@FXML
	private ImageView img_menu = new ImageView();
	@FXML
	private ImageView img_quit = new ImageView();

	@FXML
	Button btn_play = new Button();
	@FXML
	Button btn_menu = new Button();
	@FXML
	Button btn_quit = new Button();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setImageView("files/images/UI/menu/background.png", 700, 400, img_background);
		setImageView("files/images/UI/menu/flame_gif.gif", 700, 400, img_flame);
		setImageView("files/images/UI/menu/play.png", 400, 40, img_play);
		setImageView("files/images/UI/menu/menu.png", 50, 60, img_menu);
		setImageView("files/images/UI/menu/quit.png", 50, 60, img_quit);

	}


	public void switchScene_NewGameMenu(ActionEvent event) throws IOException {
		this.difficulty = transfer.getDifficulty();
		if (difficulty == null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error.");
				alert.setHeaderText(null);
				alert.setContentText("Please, select a difficulty before starting the game.\n(Go to option, select difficulty and Apply !)");
		
				alert.showAndWait();
		}
		else{
			System.out.println(difficulty);
		switch_scene(event, "NewGameMenu", stage, scene);}
	}
	public void switchScene_Options(ActionEvent event) throws IOException {
		switch_scene(event, "Options", stage, scene);
	}
	public void quitGame(ActionEvent event) {
		Platform.exit();
	}


}
