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
			Scene scene = new Scene(root, 600, 400);
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


		//create entire JSON tree
		//useful to load everything : creating / removing theme easier
		JsonNode json = mapper.readTree(Paths.get("files/sheet/gameset.json").toFile());
		
		//after THEME select, get Theme Tree
		JsonNode theme_json = json.at("/theme/human");

		//populate game board with "objects" from theme_json tree
		List<OTF> board = Arrays.asList(mapper.treeToValue(theme_json.get("objects"), OTF[].class));

		for (OTF otf : board)
		{
			System.out.println(otf);
		}




		//load save
		JsonNode save = mapper.readTree(Paths.get("files/saves/save1_sample.json").toFile());
		

		launch(args);
	}



}