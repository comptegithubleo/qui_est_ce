import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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