package app;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import main.Board;
import main.OTF;

public class BoardController implements IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private ScrollPane grid_anchor;
	@FXML
	private ChoiceBox<String> choice_question;
	@FXML
	private ChoiceBox<String> choice_answer;
	
	public void initialize() throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(Paths.get("files/sheet/gameset.json").toFile());
		JsonNode theme_json = json.at("/theme/human");
		List<OTF> all_obj = Arrays.asList(mapper.treeToValue(theme_json.get("objects"), OTF[].class));

		int board_size = 5;
		Board gameset = new Board(all_obj, board_size, 5, 5, "human");

		grid_anchor.setContent(createGrid(4, 3, gameset));
	}
	
	private GridPane createGrid(int rows, int columns, Board gameset)
	{
		GridPane tmp_grid = new GridPane();

		int index = 0;
		
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				OTF o = gameset.board.get(index);
				addImg(i, j, o, tmp_grid);
				//index++;
			}
		}

		ColumnConstraints c = new ColumnConstraints();
		c.setHgrow(Priority.NEVER);
		RowConstraints r = new RowConstraints();
		r.setVgrow(Priority.NEVER);
		tmp_grid.getColumnConstraints().add(c);
		tmp_grid.getRowConstraints().add(r);

		return tmp_grid;
	}

	private void addImg(int column, int row, OTF o, GridPane tmp_grid)
	{
		Image image = new Image("file:" + o.getsrc());
		ImageView img = new ImageView(image);
		//img.setPreserveRatio(false);

		tmp_grid.add(img, column, row);
		img.setOnMouseEntered(e -> {
			System.out.printf("Cursor: [%d, %d] / Name: %s%n", column, row, o.getid());
		});
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene);
	}
}
