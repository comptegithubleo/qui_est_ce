package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Board {

	public ArrayList<OTF> board = new ArrayList<OTF>();
	private String theme;
	private int ITF;
	private int nbrofOTF;
	private List<Integer> size;
	private HashMap<String, ArrayList<String>> global_attributes = new HashMap<String, ArrayList<String>>();

	ObjectMapper mapper = new ObjectMapper();

	public Board(String savedboard) throws IOException, Exception
	{
		JsonNode json = mapper.readTree(Paths.get("files/save/" + savedboard).toFile());
		
		List<OTF> tmp = Arrays.asList(mapper.treeToValue(json.get("objects"), OTF[].class));
	
		for(OTF i : tmp){
			this.board.add(i);
		}
		
		this.size = (List<Integer>) Arrays.asList(mapper.treeToValue(json.get("size"), Integer[].class));
		this.nbrofOTF = this.size.get(0) * this.size.get(1);
		this.theme = mapper.treeToValue(json.get("theme"), String.class);
		this.ITF = mapper.treeToValue(json.get("answer"), Integer.class);

		populateGlobalAttributes();
	}

	public Board(List<OTF> givenboard, int nbrofOTF, int sizex, int sizey, String theme) {

		this.nbrofOTF = nbrofOTF;
		this.theme = theme;
		this.size = Arrays.asList(sizex,sizey);
		
		ArrayList<OTF> save = new ArrayList<OTF>();
		for(OTF i : givenboard){
			save.add(i);
		}

		for(int i=0; i < nbrofOTF ; i++){
			Random rand = new Random();
			int nbr = rand.nextInt(save.size());
			this.board.add(save.get(nbr));
			save.remove(nbr);
		}

		Random rand = new Random();
		this.ITF = rand.nextInt(board.size());

		populateGlobalAttributes();
	}

	public String getTheme(){
		return theme;
	}

	public void setBoardEliminated(int index){
		this.board.get(index).setEliminated(true);
	}
	
	public void setITF(int ITF){
		this.ITF = ITF;
	}

	public int getITF(){
		return ITF;
	}

	public int getNbrofOTF(){
		return nbrofOTF;
	}

	public List<Integer> getSize(){
		return size;
	}

	public ArrayList<OTF> getBoard(){
		return board;
	}


	public HashMap<String, ArrayList<String>> getGlobalAttributes() {
		return global_attributes;
	}

	public boolean guess(String question, String guess)
	{
		if (this.board.get(ITF).getattributes().containsKey(question))
		{
			if(this.board.get(ITF).getattributes().get(question).equals(guess))
			{
				return true;
			}
		}
		return false;
	}

	public boolean guessAdvanced(String question1, String answer1, String question2, String answer2, String operator)
	{
		if (operator.equals("and")){
			if (this.board.get(ITF).getattributes().containsKey(question1) && this.board.get(ITF).getattributes().containsKey(question2))
			{
				if(this.board.get(ITF).getattributes().get(question1).equals(answer1) && this.board.get(ITF).getattributes().get(question2).equals(answer2))
				{
					return true;
				}
				else{
					return false;
				}
			}
			else { return false; }
		}
		else
		{
			if (this.board.get(ITF).getattributes().containsKey(question1) || this.board.get(ITF).getattributes().containsKey(question2))
			{
				if(this.board.get(ITF).getattributes().get(question1).equals(answer1) || this.board.get(ITF).getattributes().get(question2).equals(answer2))
				{
					return true;
				}
				else{
					return false;
				}
			}
			else { return false; }
		}

	}

	public ArrayList<Integer> getCompatibleList(String question, String answer, boolean check){
		ArrayList<Integer> cmpt = new ArrayList<Integer>();
		int compteur = 0;
	
		for (OTF o : board)
		{
			if (o.getattributes().containsKey(question))
			{
				if (check)
				{
					if(!o.getattributes().get(question).equals(answer))
					{
						cmpt.add(compteur);
					}
				}
				else 
				{
					if(o.getattributes().get(question).equals(answer))
					{
						cmpt.add(compteur);
					}
				}
			}
			compteur++;
		}
		
		return cmpt;
	}

	public void populateGlobalAttributes()
	{
		for (OTF o : this.board)
		{
			for (Map.Entry<String, String> entry : o.getattributes().entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();

				if (this.global_attributes.containsKey(key))
				{
					if (!this.global_attributes.get(key).contains(value))
					{
						this.global_attributes.get(key).add(value);
					}
				}
				else {
					ArrayList<String> tmp = new ArrayList<String>();
					tmp.add(value);
					this.global_attributes.put(key, tmp);
				}
			}
		}
	}

	public void printBoard(){
		System.out.println(board);

	}

	public void save() throws IOException
	{
		mapper.setVisibility(PropertyAccessor.FIELD,Visibility.ANY);
		
		File file = new File("files/save/" + this.theme + ".json");
		file.delete();

		JsonNode jsonNode = mapper.createObjectNode();
		

		
		//saving theme and answer to JSON as (-> "key" : "value")
		((ObjectNode)jsonNode).put("theme" , this.theme);
		((ObjectNode)jsonNode).put("answer" , this.ITF);

		//saving the size array into JSON
		ArrayNode sizeConvert = mapper.valueToTree(this.size);
		((ObjectNode)jsonNode).putArray("size").addAll(sizeConvert);

		//Saving date and time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd"); //Time format
		((ObjectNode)jsonNode).put("date" , dtf.format(LocalDateTime.now()));

		//save all the present object in the JSON file
		ArrayNode boardConvert = mapper.valueToTree(this.board);
		((ObjectNode)jsonNode).set("objects", boardConvert);

		//save all modifications in the file
		mapper.writeValue(Paths.get("files/save/" + this.theme + ".json").toFile(), jsonNode);
	}


}
