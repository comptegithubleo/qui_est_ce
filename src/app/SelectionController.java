package app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
    Button newgame_btn;
    @FXML
    ChoiceBox<String> choice_theme;
	@FXML
	TextField choice_sizex;
	@FXML
	TextField choice_sizey;

    public void initialize() throws IOException {

        File folder = new File("files\\save");
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

        choice_theme.setOnAction(e -> {
            if (choice_theme.getValue() != null) {
				choice_sizex.setText("");
				choice_sizey.setText("");
				newgame_btn.setDisable(false);
			}
        });
    }

    public void switchScene_BoardSave(ActionEvent event) throws IOException {
		game.setSave(list.getSelectionModel().getSelectedItem());
        String save_name = list.getSelectionModel().getSelectedItem().split("\\.")[0];
        game.setTheme(save_name);
        game.setIsNewGame(false);
		
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
				alertEraseSave(event);
			}
			else {
				game.setIsNewGame(true);
				game.setTheme(choice_theme.getValue());
				switch_scene(event, "Board", stage, scene);
			}
		}
	}

	public void alertEraseSave(ActionEvent event) throws IOException
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning !");
		alert.setHeaderText(null);
		alert.setContentText("You're about to delete your " + choice_theme.getValue() + " save file, do you want to proceed ?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			game.setIsNewGame(true);
			game.setTheme(choice_theme.getValue());
			switch_scene(event, "Board", stage, scene);
		}
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