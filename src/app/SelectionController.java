package app;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectionController extends Game implements IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	ScrollPane scrollpane;
	@FXML
	ListView<String> list;
	@FXML
	ImageView selection_background;
	@FXML
	ImageView selection_web;
	@FXML
	ImageView selection_newgame;
	@FXML
	Button newgame_btn;
	@FXML
	ComboBox<String> choice_theme;
	@FXML
	Text loadgame_text;
	@FXML
	Text newgame_text;
	@FXML
	Text choosetheme_text;
	@FXML
	Text size_text;
	@FXML
	TextField choice_sizex;
	@FXML
	TextField choice_sizey;

	public void initialize() throws IOException {

		setImageView("files/images/UI/selection/background.png", 400, 500, selection_background);
		setImageView("files/images/UI/selection/web.png", 400, 500, selection_web);
		setImageView("files/images/UI/selection/newgame.png", 400, 250, selection_newgame);

		File folder = new File("files/save");
		File[] files = folder.listFiles();
		for (File file : files)
		{
			if (file.isFile()) {
				list.getItems().add(file.getName());
			}
		}

		//add every theme to ChoiceBox
		game.getGamesetJson().at("/theme").fieldNames().forEachRemaining((theme) -> choice_theme.getItems().add(theme));

		newgame_btn.setDisable(true);
		selection_newgame.setVisible(false);

		choice_theme.setOnAction(e -> {
			if (choice_theme.getValue() != null) {
				selection_newgame.setVisible(true);
				newgame_btn.setDisable(false);
			}
		});
	}

	public void switchScene_BoardSave(ActionEvent event) throws Exception {

		String theme_name = list.getSelectionModel().getSelectedItem().split("\\.")[0];
		game.setTheme(theme_name);
		game.createExistingBoard(theme_name);
		
		switch_scene(event, "Board", stage, scene);
	}

	public void switchScene_BoardNew(ActionEvent event) throws IOException {
		if (choice_sizex.getText().isEmpty() || choice_sizey.getText().isEmpty())
		{
			alertEmpty();
		}
		else {
			if (list.getItems().contains(choice_theme.getValue() + ".json"))
			{
				if(askEraseSave())
				{
					launchNewGame(event);
				}
			}
			else {
				launchNewGame(event);
			}
		}
	}

	public boolean askEraseSave()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning !");
		alert.setHeaderText(null);
		alert.setContentText("You're about to delete your " + choice_theme.getValue() + " save file, do you want to proceed ?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public void launchNewGame(ActionEvent event) throws IOException
	{
		game.setTheme(choice_theme.getValue());

		if(game.createNewBoard(Integer.parseInt(choice_sizex.getText()), Integer.parseInt(choice_sizey.getText())))
		{
			switch_scene(event, "Board", stage, scene);
		}
		else {
			alertWrongSize();
		}
	}

	public void alertWrongSize()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText("Incorrect board size (probably too high)");
		alert.showAndWait();
	}

	public void alertEmpty()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Warning");
		alert.setHeaderText(null);
		alert.setContentText("Choose board size first");
		alert.showAndWait();
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene);
	}
}