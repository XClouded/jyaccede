package lelexxx.com.jyaccede.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "category")
public class CategoryModel {

    @DatabaseField(id = true)
    public String id;

    @DatabaseField
    public String name;

    public CategoryModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryModel() {
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
