package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public interface IGlobalFunctions {
	
	public default void switch_scene(ActionEvent event, String scene_name, Stage stage, Scene scene) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource(scene_name + ".fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
