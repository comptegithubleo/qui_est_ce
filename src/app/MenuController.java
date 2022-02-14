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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController implements Initializable, IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;

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


	private void setImageView(String imagePath, int width, int height, ImageView imageView)
	{
		Image newImage = new Image("file:files/images/" + imagePath, width, height, true, false);
		imageView.setImage(newImage);
		imageView.setSmooth(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setImageView("UI/menu/background.png", 700, 400, img_background);
		setImageView("UI/menu/flame_gif.gif", 700, 400, img_flame);
		setImageView("UI/menu/play.png", 400, 40, img_play);
		setImageView("UI/menu/menu.png", 50, 60, img_menu);
		setImageView("UI/menu/quit.png", 50, 60, img_quit);

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
