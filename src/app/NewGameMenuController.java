package app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class NewGameMenuController implements IGlobalFunctions {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    ScrollPane scrollpane;
    @FXML
    ListView<String> list;

    public void initialize() {

        File folder = new File("files\\save");
        File[] files = folder.listFiles();
        for (File file : files)
        {
            if (file.isFile()) {
                list.getItems().add(file.getName());
            }
        }
    }

    public void switchScene_Board(ActionEvent event) throws IOException {
		transfer.setSave(list.getSelectionModel().getSelectedItem());
		System.out.println(transfer.getSave());
        switch_scene(event, "Board", stage, scene);
    }
    public void switchScene_Menu(ActionEvent event) throws IOException {
        switch_scene(event, "Menu", stage, scene);
    }
}