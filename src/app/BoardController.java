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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Board;
import main.OTF;

public class BoardController implements IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;
	Board gameset;
	private boolean finalGuessOngoing = false;
	
	@FXML
	private ScrollPane grid_anchor;
	@FXML
	private ChoiceBox<String> choice_question;
	@FXML
	private ChoiceBox<String> choice_answer;
	@FXML
	private Button guess_btn;
	@FXML
	private Button finalguess_btn;
	@FXML
	private Text guess_text;
	
	public void initialize() throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(Paths.get("files/sheet/gameset.json").toFile());
		JsonNode theme_json = json.at("/theme/human");
		List<OTF> all_obj = Arrays.asList(mapper.treeToValue(theme_json.get("objects"), OTF[].class));

		int board_size = 6;
		gameset = new Board(all_obj, board_size, 5, 5, "human");
		
		grid_anchor.setContent(createGrid(3, 2, gameset));

		populateChoicebox(gameset.getGlobalAttributes());
		
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
				index++;
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

		img.setOnMouseClicked(e -> {
			/*Node source = (Node) e.getTarget();
			int columnIndex = GridPane.getColumnIndex(source);
			int rowIndex = GridPane.getRowIndex(source);*/
			System.out.println("Row : " + row + ", Col : " + column + ", Name : " + o.getid());

			if (finalGuessOngoing)
			{
				if (o.getid() == gameset.getITF().getid())
				{
					win();
				}
				else {
					loose();
				}
			}
			else {
				eliminate(o, img);
			}
		});
		
		img.setOnMouseEntered(e -> {
			if (finalGuessOngoing)
			{
				img.getStyleClass().add("final_hover");
			}
		});
		img.setOnMouseExited(e -> {
			if (finalGuessOngoing)
			{
				img.getStyleClass().remove("final_hover");
			}
		});
	}
	
	public void eliminate(OTF o, ImageView img)
	{
		if (o.geteliminated())
		{
			o.setEliminated(false);
			img.getStyleClass().remove("eliminated");
		}
		else {
			o.setEliminated(true);
			img.getStyleClass().add("eliminated");
		}
	}

	public void populateChoicebox(HashMap<String, ArrayList<String>> attributes)
	{
		guess_btn.setDisable(true);

		for (String key : attributes.keySet())
		{
			choice_question.getItems().add(key);
		}

		choice_question.setOnAction(e -> {
			choice_answer.getItems().clear();
			
			for (String value : attributes.get(choice_question.getValue()))
			{
				choice_answer.getItems().add(value);
			}

			guess_btn.setDisable(true);
		});

		choice_answer.setOnAction(e -> {
			guess_btn.setDisable(false);
		});
	}

	public void guess()
	{
		if (gameset.guess(choice_question.getValue(), choice_answer.getValue()))
		{
			guess_text.setText("TRUE");
			guess_text.setFill(Color.GREEN);
		} else {
			guess_text.setText("FALSE");
			guess_text.setFill(Color.RED);
		}
		System.out.println(gameset.getITF().getid());
	}

	public void finalGuess()
	{
		if (finalGuessOngoing)
		{
			choice_question.setDisable(false);
			choice_answer.setDisable(false);
			guess_btn.setDisable(false);
			finalguess_btn.setText("Try Final Guess");
			finalGuessOngoing = false;
		}
		else {
			choice_question.setDisable(true);
			choice_answer.setDisable(true);
			guess_btn.setDisable(true);
			finalguess_btn.setText("Back");
			finalGuessOngoing = true;
		}
	}

	public void win()
	{
		System.out.println("you win");
	}
	public void loose()
	{
		System.out.println("you loose");
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene);
	}
}
