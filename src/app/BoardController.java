package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BoardController implements Initializable, IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	GridPane board_grid = new GridPane();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		board_grid.add(new Button(), 1, 0); // column=1 row=0
		board_grid.add(new Label(), 2, 0);  // column=2 row=0
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene);
	}
}
