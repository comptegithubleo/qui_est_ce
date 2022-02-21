import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OTF {

	private String id;
	private String src;
	private HashMap<String, String> attributes = new HashMap<String, String>();

	public OTF(){}

	public OTF(String id, String src, HashMap<String, String> attributes){
		this.id = id;
		this.src = src;
		this.attributes = attributes;
	}

	public String toString(){
		return "id : " + this.id + "\nattributes : " + attributes + "\n";
	}

	public void setid(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setsrc(String src){
		this.src = src;
	}

	public void setattributes(JsonNode attributes){
		ObjectMapper mapper = new ObjectMapper();
		this.attributes = mapper.convertValue(attributes, new TypeReference<HashMap<String, String>>(){});
	}

	public boolean guess(String question, String guess)
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
}
