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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewGameMenuController implements IGlobalFunctions {

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

    public void initialize() throws IOException {

        File folder = new File("files\\save");
        File[] files = folder.listFiles();
        for (File file : files)
        {
            if (file.isFile()) {
                list.getItems().add(file.getName());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(Paths.get("files/sheet/gameset.json").toFile());
        json.at("/theme").fieldNames().forEachRemaining((theme) -> choice_theme.getItems().add(theme));

        newgame_btn.setDisable(true);

        choice_theme.setOnAction(e -> {
            if (choice_theme != null) { newgame_btn.setDisable(false); }
        });
    }

    public void switchScene_BoardSave(ActionEvent event) throws IOException {
		transfer.setSave(list.getSelectionModel().getSelectedItem());
        String save_name = list.getSelectionModel().getSelectedItem().split("\\.")[0];
        transfer.setTheme(save_name);

        transfer.setIsNewGame(false);
        switch_scene(event, "Board", stage, scene);
    }

    public void switchScene_Board(ActionEvent event) throws IOException {

        if (list.getItems().contains(choice_theme.getValue() + ".json"))
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning !");
            alert.setHeaderText(null);
            alert.setContentText("you're about to delete your " + choice_theme.getValue() + " save file, do you want to proceed ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                transfer.setIsNewGame(true);
                transfer.setTheme(choice_theme.getValue());
                switch_scene(event, "Board", stage, scene);
            }
        }
        else {
            transfer.setIsNewGame(true);
            transfer.setTheme(choice_theme.getValue());
            switch_scene(event, "Board", stage, scene);
        }
    }

    public void switchScene_Menu(ActionEvent event) throws IOException {
        switch_scene(event, "Menu", stage, scene);
    }
}