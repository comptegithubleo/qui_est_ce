package app;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Board;
import main.OTF;
import java.lang.Math;

public class BoardController implements IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private Board gameset;
	private boolean finalGuessOngoing = false;
	
	@FXML
	private ScrollPane grid_anchor;
	@FXML
	private ChoiceBox<String> choice_question;
	@FXML
	private ChoiceBox<String> choice_answer;
	@FXML
	private ChoiceBox<String> choice_question2;
	@FXML
	private ChoiceBox<String> choice_answer2;
	@FXML
	private ChoiceBox<String> operator;
	@FXML
	private Button guess_btn;
	@FXML
	private Button finalguess_btn;
	@FXML
	private Text guess_text;
	@FXML
	private Text easy_text;
	@FXML
	private HBox advanced_hbox;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public void initialize() throws Exception {


		JsonNode json = mapper.readTree(Paths.get("files/sheet/gameset.json").toFile());

		JsonNode theme_json = json.at("/theme/" + transfer.getTheme());
		System.out.println(transfer.getTheme());
		List<OTF> all_obj = Arrays.asList(mapper.treeToValue(theme_json.get("objects"), OTF[].class));

		if (transfer.getIsNewGame())
		{
			gameset = new Board(all_obj,all_obj.size(), (int)(Math.sqrt(all_obj.size())),(int)(Math.sqrt(all_obj.size())), transfer.getTheme());
		}
		else {
			gameset = new Board(transfer.getSave());
		}
		gameset.save();
		
		grid_anchor.setContent(createGrid((int)(Math.sqrt(gameset.getBoard().size()))+1,(int)(Math.sqrt(gameset.getBoard().size()))+1, gameset));

		if (transfer.getDifficulty().equals("Advanced"))
		{
			advanced_hbox.setVisible(true);
			choice_question2.setDisable(true);
			choice_answer2.setDisable(true);
			operator.getItems().addAll("or", "and");
		}

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
				if(index<gameset.getBoard().size()){
					OTF o = gameset.getBoard().get(index);
					addImg(i, j, o, tmp_grid);
					index++;
				}
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

		tmp_grid.add(img, column, row);
		if (o.geteliminated()) { img.getStyleClass().add("eliminated"); }

		img.setOnMouseClicked(e -> {

			System.out.println("Row : " + row + ", Col : " + column + ", Name : " + o.getid());

			if (finalGuessOngoing)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				
				if (o.getid() == gameset.board.get(gameset.getITF()).getid())
				{
					alert.setTitle("Congratulation !");
					alert.setHeaderText("You won ! Great job !");
				}
				else
				{
					alert.setTitle("Uh Oh !");
					alert.setHeaderText("You lost... Try again next time !");
				}
				
				ButtonType reset = new ButtonType("Delete save file.");

				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(reset);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == reset)
				{
					File f = new File("files\\save\\" + transfer.getTheme() +".json");
					f.delete();
					try {
						root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
						stage = (Stage)((Node)e.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.setResizable(true);
						stage.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			else {
				eliminate(o, img);
				try {
					gameset.save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
			choice_question2.getItems().add(key);
		}

		choice_question.setOnAction(e -> {
			choice_answer.getItems().clear();
			
			for (String value : attributes.get(choice_question.getValue()))
			{
				choice_answer.getItems().add(value);
			}

			guess_btn.setDisable(true);
		});

		choice_question2.setOnAction(e -> {
			choice_answer2.getItems().clear();
			
			for (String value : attributes.get(choice_question2.getValue()))
			{
				choice_answer2.getItems().add(value);
			}

			guess_btn.setDisable(true);
		});

		choice_answer.setOnAction(e -> {
			if (transfer.getDifficulty().equals("Easy"))
			{
				easy_text.setText(gameset.getCompatibleList(choice_question.getValue(), choice_answer.getValue(), gameset.guess(choice_question.getValue(), choice_answer.getValue())).size() + " will be eliminated");
			}
			if (transfer.getDifficulty().equals("Advanced"))
			{
				if (choice_answer2.getValue() != null)
				{
					guess_btn.setDisable(false);
				}
			}
			guess_btn.setDisable(false);

		});
		choice_answer2.setOnAction(e -> {
			if (transfer.getDifficulty().equals("Easy"))
			{
				easy_text.setText(gameset.getCompatibleList(choice_question.getValue(), choice_answer.getValue(), gameset.guess(choice_question.getValue(), choice_answer.getValue())).size() + " will be eliminated");
			}
			if (transfer.getDifficulty().equals("Advanced"))
			{
				if (choice_answer.getValue() != null)
				{
					guess_btn.setDisable(false);
				}
			}
			guess_btn.setDisable(false);
		});

		operator.setOnAction(e -> {
			choice_question2.setDisable(false);
			choice_answer2.setDisable(false);
		});
	}


	public void guess()
	{
		if (transfer.getDifficulty().equals("Easy")){
			ArrayList<Integer> ListOfCompatible = new ArrayList<Integer>();
			ListOfCompatible = gameset.getCompatibleList(choice_question.getValue(), choice_answer.getValue(), gameset.guess(choice_question.getValue(), choice_answer.getValue()) );
			for(int i : ListOfCompatible){
				gameset.setBoardEliminated(i);
			}

			grid_anchor.setContent(null);
			grid_anchor.setContent(createGrid((int)(Math.sqrt(gameset.getBoard().size()))+1,(int)(Math.sqrt(gameset.getBoard().size()))+1, gameset));
		}
		else if (transfer.getDifficulty().equals("Advanced"))
		{
			if(operator.getValue() == null || choice_question2.getValue() == null || choice_answer2.getValue() == null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error.");
				alert.setHeaderText(null);
				alert.setContentText("You're playing in advanced mode ! \n please choose a comparative operator and two questions...");
		
				alert.showAndWait();
			}
			else{
				if (gameset.guessAdvanced(choice_question.getValue(), choice_answer.getValue(), choice_question2.getValue(), choice_answer2.getValue(), operator.getValue()))
				{
					guess_text.setText("TRUE");
					guess_text.setFill(Color.GREEN);
				} else {
					guess_text.setText("FALSE");
					guess_text.setFill(Color.RED);
				}
				System.out.println("ITF : " + gameset.board.get(gameset.getITF()).getid());
			}
		}
		else
		{
			if (gameset.guess(choice_question.getValue(), choice_answer.getValue()))
			{
				guess_text.setText("TRUE");
				guess_text.setFill(Color.GREEN);
			} else {
				guess_text.setText("FALSE");
				guess_text.setFill(Color.RED);
			}
			System.out.println("ITF : " + gameset.board.get(gameset.getITF()).getid());
		}
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

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene);
	}
}
