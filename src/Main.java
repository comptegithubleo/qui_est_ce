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

		//==============================================================================> CREATED IN BOARDCONTROLLER.JAVA
		ObjectMapper mapper = new ObjectMapper();
		//create entire JSON tree
		//useful to load everything : creating / removing theme easier
		JsonNode json = mapper.readTree(Paths.get("files/sheet/gameset.json").toFile());
		
		//after THEME select, get Theme Tree
		JsonNode theme_json = json.at("/theme/human");

		//populate game board with "objects" from theme_json tree
		List<OTF> board = Arrays.asList(mapper.treeToValue(theme_json.get("objects"), OTF[].class));

		Board test1 = new Board(board, 5, 3 , 2, "human");






		/*//get all themes
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
		JsonNode save_json = mapper.readTree(Paths.get("files/save/save_sample.json").toFile());

		//get theme
		save_json.at("/theme");

		//get answer
		save_json.at("/answer");

		//get size
		save_json.at("/size");

		//json save to board
		List<OTF> board2 = Arrays.asList(mapper.treeToValue(theme_json.get("objects"), OTF[].class));*/

		

		//
		//Save objects in json
		//

		
		//Method need to be implemented elsewhere...
		//for now a save overwrite another : to update. (-> maybe with a file counting system ?)

		ObjectMapper mapper2 = new ObjectMapper();
		mapper2.setVisibility(PropertyAccessor.FIELD,Visibility.ANY);
		JsonNode jsonNode = mapper2.createObjectNode();
		
		//saving theme and answer to JSON as (-> "key" : "value")
		((ObjectNode)jsonNode).put("theme" , test1.getTheme());
		((ObjectNode)jsonNode).put("answer" , test1.getITF().getid());

		//saving the size array into JSON
		ArrayNode sizeConvert = mapper2.valueToTree(test1.getSize());
		((ObjectNode)jsonNode).putArray("size").addAll(sizeConvert);

		//Saving date and time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd"); //Time format
		((ObjectNode)jsonNode).put("date" , dtf.format(LocalDateTime.now()));

		//save all the present object in the JSON file
		ArrayNode boardConvert = mapper2.valueToTree(test1.getBoard());
		((ObjectNode)jsonNode).set("objects", boardConvert);

		

		//save all modifications in the file
		mapper2.writeValue(Paths.get("files/save/save.json").toFile(), jsonNode);
	
		


		System.out.println("__________________________________ \n");

		


		launch(args);
	}
}