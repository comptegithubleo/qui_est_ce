package generator_app;

public class ObservableAttribute {
    private String obName;
    private String key;
    private String value;

    public ObservableAttribute(String obName, String key, String value){
        this.obName=obName;
        this.key=key;
        this.value=value;
    }
    
    public String getObName() {
        return obName;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
}
