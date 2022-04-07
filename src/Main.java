import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception
	{
<<<<<<< HEAD
		Parent root = FXMLLoader.load(getClass().getResource("app/Menu.fxml"));
=======
		Parent root = FXMLLoader.load(getClass().getResource("/app/Menu.fxml"));
>>>>>>> refactoring navigability issues
		Scene scene = new Scene(root, 700, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) throws Exception {

		launch(args);
	}
}