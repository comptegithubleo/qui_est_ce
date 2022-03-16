package generator_app;

public class CreatedObject {

    //first window fields
    private String theme;
    private String name;

    public CreatedObject(String name){
        this.name=name;
    }


    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
