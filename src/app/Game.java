package app;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.text.Font;
import main.Board;
import main.OTF;

public class Game {

	private String difficulty;
	private String theme;
	private boolean newGame;
	private JsonNode gameset_json;
	protected Font font;
	private JsonNode theme_json;
	protected Board board;
	

	protected static Game game = new Game();
	
	ObjectMapper mapper = new ObjectMapper();

	public Game(){
		this.difficulty = "Normal";
		this.font = Font.loadFont("file:files/fonts/Retro_Gaming.ttf", 10);
		try {
			gameset_json = mapper.readTree(Paths.get("files/sheet/gameset.json").toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createExistingBoard(String theme_name) throws IOException, Exception
	{
		JsonNode save_json = mapper.readTree(Paths.get("files/save/" + theme_name + ".json").toFile());
		List<OTF> saved_board = Arrays.asList(mapper.treeToValue(save_json.get("objects"), OTF[].class));
		int[] size = mapper.treeToValue(save_json.get("size"), int[].class);
		int ITF = mapper.treeToValue(save_json.get("answer"), Integer.class);
		String theme = mapper.treeToValue(save_json.get("theme"), String.class);

		board = new Board(save_json, saved_board, theme, size, ITF);
	}
	public boolean createNewBoard(int sizex, int sizey) throws JsonProcessingException
	{
		List<OTF> all_OTF = new LinkedList<OTF>(Arrays.asList(mapper.treeToValue(game.getThemeJson().get("objects"), OTF[].class)));

		if ((sizex * sizey) > all_OTF.size())
		{
			return false;
		}

		board = new Board(all_OTF, sizex, sizey, game.getTheme());

		return true;
	}


	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public boolean getIsNewGame()
	{
		return this.newGame;
	}
	public void setIsNewGame(boolean newGame)
	{
		this.newGame = newGame;
	}

	public JsonNode getGamesetJson()
	{
		return gameset_json;
	}

	public JsonNode getThemeJson()
	{
		return theme_json;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
		theme_json = gameset_json.at("/theme/" + theme);
	}
}

