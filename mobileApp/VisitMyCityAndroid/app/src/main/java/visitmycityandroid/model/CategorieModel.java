package visitmycityandroid.model;

public class CategorieModel {

    public String id;

    public String name;

    public CategorieModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategorieModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
