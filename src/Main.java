import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("app/Menu.fxml"));
			Scene scene = new Scene(root, 300, 300);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		System.out.println("main launched");

		ObjectMapper mapper = new ObjectMapper();

		List<OTF> plateau = Arrays.asList(mapper.readValue(Paths.get("src/sheet/test.json").toFile(), OTF[].class));

		for(OTF o : plateau)
		{
			System.out.println(o);
			o.showAttribute();
		}
		launch(args);
	}



}