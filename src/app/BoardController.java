package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import main.OTF;

public class BoardController extends Game implements IGlobalFunctions {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private boolean finalGuessOngoing = false;
	
	@FXML
	ScrollPane grid_anchor;
	@FXML
	ComboBox<String> choice_question;
	@FXML
	ComboBox<String> choice_answer;
	@FXML
	ComboBox<String> choice_question2;
	@FXML
	ComboBox<String> choice_answer2;
	@FXML
	ComboBox<String> operator;
	@FXML
	Button guess_btn;
	@FXML
	Button finalguess_btn;
	@FXML
	Text guess_text;
	@FXML
	Text easy_text;
	@FXML
	Pane advanced_pane;
	@FXML
	Pane board_pane;
	@FXML
	ImageView board_buttons;
	@FXML
	ImageView board_buttons_advanced;
	@FXML
	ImageView board_guess;
	@FXML
	ImageView board_final_guess;
	@FXML
	ImageView board_final_guess_grey;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public void initialize() throws Exception {

		initImg();

		game.board.save();

		createGrid();

		populateChoicebox(game.board.getGlobalAttributes());
	}

	public void initImg()
	{
		ImageView image = new ImageView();
		setImageView("files/images/UI/board/background_side.png", 840, 480, image);
		image.fitWidthProperty().bind(board_pane.widthProperty());
		image.fitHeightProperty().bind(board_pane.heightProperty());
		board_pane.getChildren().add(image);

		setImageView("files/images/UI/board/board_buttons.png", 500, 50, board_buttons);
		setImageView("files/images/UI/board/board_buttons_advanced.png", 500, 50, board_buttons_advanced);
		setImageView("files/images/UI/board/board_guess.png", 500, 50, board_guess);
		setImageView("files/images/UI/board/board_final_guess.png", 500, 200, board_final_guess);
		setImageView("files/images/UI/board/board_final_guess_grey.png", 500, 400, board_final_guess_grey);
	}
	
	private void createGrid()
	{
		grid_anchor.setContent(null);

		GridPane grid = new GridPane();

		int index = 0;
		
		for (int i = 0; i < game.board.getSize()[1]; i++)
		{
			for (int j = 0; j < game.board.getSize()[0]; j++)
			{
				if(index < game.board.getNbrofOTF()){
					OTF o = game.board.getBoard().get(index);
					addImg(i, j, o, grid);
					index++;
				}
			}
		}

		ColumnConstraints c = new ColumnConstraints();
		c.setHgrow(Priority.NEVER);
		RowConstraints r = new RowConstraints();
		r.setVgrow(Priority.NEVER);
		grid.getColumnConstraints().add(c);
		grid.getRowConstraints().add(r);

		grid_anchor.setContent(grid);
	}

	private void addImg(int column, int row, OTF o, GridPane grid)
	{
		Image image = new Image("file:" + o.getsrc());
		ImageView img = new ImageView(image);

		grid.add(img, column, row);
		if (o.geteliminated()) { img.getStyleClass().add("eliminated"); }
		
		createImgEvents(o, img);
	}

	public void createImgEvents(OTF o, ImageView img)
	{

		img.setOnMouseClicked(e -> {

			if (finalGuessOngoing)
			{
				finalTry(o, e);
			}
			else {
				eliminate(o, img);
				game.board.save();
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

	public void finalTry(OTF o, MouseEvent e)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);

		if (o.getid() == game.board.getBoard().get(game.board.getITF()).getid())
		{
			alert.setTitle("Congratulations !");
			alert.setHeaderText("You won ! Great job ! Save file will now be deleted.");
		}
		else
		{
			alert.setTitle("Uh Oh !");
			alert.setHeaderText("You lost... Try again next time ! Save file will now be deleted.");
		}
		
		ButtonType reset = new ButtonType("Ok");

		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(reset);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == reset)
		{
			File f = new File("files/save/" + game.getTheme() +".json");
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
		advanced_pane.setVisible(false);
		board_buttons_advanced.setVisible(false);
		board_guess.setVisible(false);
		board_final_guess.setVisible(false);
		
		if (game.getDifficulty().equals("Advanced"))
		{
			advanced_pane.setVisible(true);
			board_buttons_advanced.setVisible(true);
			operator.getItems().addAll("or", "and");
		}

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
			board_guess.setVisible(false);
		});

		choice_question2.setOnAction(e -> {
			choice_answer2.getItems().clear();
			
			for (String value : attributes.get(choice_question2.getValue()))
			{
				choice_answer2.getItems().add(value);
			}
		});

		choice_answer.setOnAction(e -> {
			if (game.getDifficulty().equals("Easy"))
			{
				easy_text.setText(game.board.getCompatibleList(choice_question.getValue(), choice_answer.getValue(), game.board.guess(choice_question.getValue(), choice_answer.getValue())).size() + " will be eliminated");
			}
			guess_btn.setDisable(false);
			board_guess.setVisible(true);
		});
	}

	public void guess()
	{
		if (game.getDifficulty().equals("Easy")) {
			ArrayList<Integer> ListOfCompatible = new ArrayList<Integer>();
			ListOfCompatible = game.board.getCompatibleList(choice_question.getValue(), choice_answer.getValue(), game.board.guess(choice_question.getValue(), choice_answer.getValue()) );
			
			for(int i : ListOfCompatible) {
				game.board.setBoardEliminated(i);
			}

			createGrid();
		}
		else if (game.getDifficulty().equals("Advanced")) {
			if (operator.getValue() == null || choice_question2.getValue() == null || choice_answer2.getValue() == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error.");
				alert.setHeaderText(null);
				alert.setContentText("You're playing in advanced mode ! \n Please choose a comparative operator and two questions...");
		
				alert.showAndWait();
			}
			else {
				if (game.board.guessAdvanced(choice_question.getValue(), choice_answer.getValue(), choice_question2.getValue(), choice_answer2.getValue(), operator.getValue()))
				{
					guess_text.setText(choice_question.getValue() + " : " + choice_answer.getValue() + " " + operator.getValue() + " " +
					choice_question.getValue() + " : " + choice_answer.getValue() + " ? TRUE");
					guess_text.setFill(Color.GREEN);
				} else {
					guess_text.setText(choice_question.getValue() + " : " + choice_answer.getValue() + " " + operator.getValue() + " " +
					choice_question.getValue() + " : " + choice_answer.getValue() + " ? FALSE");
					guess_text.setFill(Color.RED);
				}
			}
		}
		else
		{
			if (game.board.guess(choice_question.getValue(), choice_answer.getValue()))
			{
				guess_text.setText(choice_question.getValue() + " : " + choice_answer.getValue() + " ? TRUE");
				guess_text.setFill(Color.GREEN);
			} else {
				guess_text.setText(choice_question.getValue() + " : " + choice_answer.getValue() + " ? FALSE");
				guess_text.setFill(Color.RED);
			}
		}
		//Prints ITF to find
		//System.out.println("ITF : " + game.board.getBoard().get(game.board.getITF()).getid());
	}

	public void finalGuess()
	{
		if (finalGuessOngoing)
		{
			choice_question.setDisable(false);
			choice_answer.setDisable(false);
			guess_btn.setDisable(false);
			board_guess.setVisible(true);
			board_final_guess.setVisible(false);
			finalguess_btn.setText("Final Guess");
			finalGuessOngoing = false;
		}
		else {
			choice_question.setDisable(true);
			choice_answer.setDisable(true);
			board_guess.setVisible(false);
			board_final_guess.setVisible(true);
			guess_btn.setDisable(true);
			finalguess_btn.setText("Back");
			finalGuessOngoing = true;
		}
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene, false);
	}
}
