package app;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	public void switchScene_Menu(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchScene_Game(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchScene_Options(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("Options.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchScene_NewGameMenu(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("NewGameMenu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void quitGame(ActionEvent event)
	{
		Platform.exit();
	}
}
