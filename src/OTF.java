public class OTF {

    private String nom;
    private String age;
    private int test;

    public OTF(){}

    public OTF(String nom, String age, int test){
        this.nom = nom;
        this.age = age;
        this.test = test;
    }

    public String toString(){
        return "je suis " + this.nom + " j'ai " + this.age + "je lance le test : " + this.test; 
    }
    
}
