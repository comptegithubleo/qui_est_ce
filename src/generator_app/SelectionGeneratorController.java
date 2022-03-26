package generator_app;

import java.io.File;
import java.io.IOException;

import app.IGlobalFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelectionGeneratorController implements IGlobalFunctions {
	private Scene scene;
	private Stage stage;

	private static boolean is_new_gen = true;

	@FXML
	private Button existing_gen_btn;

	public void initialize()
	{
		File file = new File("files/sheet/gen_tmp.json");
		if (!file.exists())
		{
			existing_gen_btn.setDisable(true);
		}
	}

	public static boolean getIsNewGen()
	{
		return is_new_gen;
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		switch_scene(event, "../app/Menu", stage, scene, false);
	}

	public void switchScene_newGen(ActionEvent event) throws IOException {
		switch_scene(event, "NewThemeGen", stage, scene, false);
	}
	public void switchScene_existingGen(ActionEvent event) throws IOException {
		is_new_gen = false;
		switch_scene(event, "NewThemeGen", stage, scene, false);
	}

}

