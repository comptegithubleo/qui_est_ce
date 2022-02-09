import com.fasterxml.jackson.databind.JsonNode;

public class OTF {

	private String id;
	private String src;

	private JsonNode attributes;

	public OTF(){}

	/*public OTF(String id, String src, ArrayList<String> attributes){
		this.id = id;
		this.src = src;
		this.attribute = attributes;
	} commented but usable to manually create an instance*/

	public String toString(){
		return "id : " + this.id + "\nattributes : " + attributes + "\n";
	}

	public void setid(String id){
		this.id = id;
	}

	public void setsrc(String src){
		this.src = src;
	}

	public void setattributes(JsonNode attributes){
		this.attributes = attributes;
	}

}
