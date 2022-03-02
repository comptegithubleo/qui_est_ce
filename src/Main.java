import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Board;
import main.OTF;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("app/Menu.fxml"));
		Scene scene = new Scene(root, 700, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		
		System.out.println("main launched");
		System.out.println("__________________________________ \n");

		


		launch(args);
	}
}