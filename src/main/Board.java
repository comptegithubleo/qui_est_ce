package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Board {

	public ArrayList<OTF> board = new ArrayList<OTF>();
	private String theme;
	private OTF ITF;
	private int nbrofOTF;
	private List<Integer> size;
	private HashMap<String, ArrayList<String>> global_attributes = new HashMap<String, ArrayList<String>>();

	public Board(List<OTF> givenboard, int nbrofOTF, int sizex, int sizey, String theme){

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
		this.ITF = board.get(rand.nextInt(board.size()));

		populateGlobalAttributes();
	}

	public String getTheme(){
		return theme;
	}

	public OTF getITF(){
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
		if (this.ITF.getattributes().containsKey(question))
		{
			if(this.ITF.getattributes().get(question).equals(guess))
			{
				return true;
			}
		}
		return false;
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
}
