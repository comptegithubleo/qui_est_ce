import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.event.SwingPropertyChangeSupport;

import com.fasterxml.jackson.databind.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("app/Menu.fxml"));
			Scene scene = new Scene(root, 700, 400);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
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

		//get all themes
		json.at("/theme").fieldNames().forEachRemaining(System.out::println);
		
		for (OTF otf : board)
		{
			System.out.println(otf);
		}

		//test user guess (boolean)
		for (OTF otf : board) {
			otf.guess("question", "supposed answer");
		}
		
		
		//===== SAVE HANDLING =====//
		//load
		JsonNode save_json = mapper.readTree(Paths.get("files/saves/save1_sample.json").toFile());

		//get theme
		save_json.at("/theme");

		//get answer
		save_json.at("/answer");

		//get size
		save_json.at("/size");

		//json save to board
		List<OTF> board2 = Arrays.asList(mapper.treeToValue(theme_json.get("objects"), OTF[].class));

		launch(args);

		//
		//Save objects in json
		//

		try {
			ObjectMapper mapper3= new ObjectMapper();
			mapper3.writeValue(Paths.get("src/save1.json").toFile(), board2);
			System.out.println("Créé !");

		}
		catch(Exception e) {
			e.printStackTrace();

		}
		

	}
}