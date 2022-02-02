

public class OTF {

    private String nom;
    private String age;

    public OTF(){}

    public OTF(String nom, String age){
        this.nom = nom;
        this.age = age;
    }

    public String toString(){
        return "je suis " + this.nom + " j'ai " + this.age + " ans, je lance le test"; 
    }
    
    public void setnom(String nom){
        this.nom = nom;
    }

    public void setage(String age){
        this.age = age;
    }

}
