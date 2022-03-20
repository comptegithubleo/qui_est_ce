package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OptionsController extends Game implements IGlobalFunctions{

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	Button options_apply;
	@FXML
	Button options_back;
	@FXML
	ImageView options_background;
	@FXML
	ImageView options_choice_easy;
	@FXML
	ImageView options_choice_normal;
	@FXML
	ImageView options_choice_advanced;
	@FXML
	ToggleGroup difficulty_choice;
	@FXML
	ToggleButton easy_toggle;
	@FXML
	ToggleButton normal_toggle;
	@FXML
	ToggleButton advanced_toggle;
	@FXML
	Text dlc_text;

	public void initialize() {

		loadImg();

		switch (game.getDifficulty()) {
			case "Easy":
				toggleEasy();
				break;
			case "Normal":
				toggleNormal();
				break;
			case "Advanced":
				toggleAdvanced();
				break;
			default:
				break;
		}

		options_apply.setStyle("-fx-text-fill: #757575");
		options_apply.setDisable(true);

		easy_toggle.setOnAction(e -> {
			toggleEasy();
		});
		normal_toggle.setOnAction(e -> {
			toggleNormal();
		});
		advanced_toggle.setOnAction(e -> {
			toggleAdvanced();
		});
	}

	public void loadImg()
	{
		setImageView("files/images/UI/options/background.png", 900, 450, options_background);
		setImageView("files/images/UI/options/choice.png", 180 , 30, options_choice_easy);
		setImageView("files/images/UI/options/choice.png", 180 , 30, options_choice_normal);
		setImageView("files/images/UI/options/choice.png", 180 , 30, options_choice_advanced);

	}

	public void toggleEasy()
	{
		options_apply.setDisable(false);
		options_apply.setStyle("-fx-text-fill: #9c9102");
		difficulty_choice.selectToggle(easy_toggle);
		options_choice_easy.setVisible(true);
		options_choice_normal.setVisible(false);
		options_choice_advanced.setVisible(false);
	}
	public void toggleNormal()
	{
		options_apply.setDisable(false);
		options_apply.setStyle("-fx-text-fill: #9c9102");
		difficulty_choice.selectToggle(normal_toggle);
		options_choice_easy.setVisible(false);
		options_choice_normal.setVisible(true);
		options_choice_advanced.setVisible(false);
	}
	public void toggleAdvanced()
	{
		options_apply.setDisable(false);
		options_apply.setStyle("-fx-text-fill: #9c9102");
		difficulty_choice.selectToggle(advanced_toggle);
		options_choice_easy.setVisible(false);
		options_choice_normal.setVisible(false);
		options_choice_advanced.setVisible(true);
	}

	public void apply()
	{
		ToggleButton tmp = (ToggleButton) difficulty_choice.getSelectedToggle();

		game.setDifficulty(tmp.getText());
		options_apply.setStyle("-fx-text-fill: #757575");
		options_apply.setDisable(true);
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "Menu", stage, scene, false);
	}

}