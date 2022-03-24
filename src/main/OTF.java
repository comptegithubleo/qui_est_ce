package main;

import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OTF {

	public Boolean eliminated=false;
	public String id;
	public String src;

	public HashMap<String, String> attributes = new HashMap<String, String>();


	public OTF(Boolean eliminated, String id, String src){
		this.eliminated=false;
		this.id=id;
		this.src=src;
	}

	public OTF(){}
	
	public String getid(){
		return id;
	}
	public void setid(String id){
		this.id = id;
	}
	
	public String getsrc(){
		return src;
	}
	public void setsrc(String src){
		this.src = src;
	}

	public boolean geteliminated()
	{
		return eliminated;
	}
	public void setEliminated(boolean eliminated)
	{
		this.eliminated = eliminated;
	}

	public HashMap<String, String> getattributes()
	{
		return attributes;
	}
	public void setattributes(JsonNode attributes){
		ObjectMapper mapper = new ObjectMapper();
		this.attributes = mapper.convertValue(attributes, new TypeReference<HashMap<String, String>>(){});
	}


	public boolean guessEasy(String question, String guess)
	{
		if (this.attributes.containsKey(question))
		{
			if(this.attributes.get(question).equals(guess))
			{
				return true;
			}
		}
		return false;
	}

	public String toString(){
		return "id : " + this.id + "\nattributes : " + attributes + "\n";
	}
}
