package Models;

import Components.Model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class Category extends Model {
    public static final String PARENT_NULL_ERR = "Parent id doesn't exist";
    private String name, parentId;

    public Category() {
        super();
    }

    public Category(String id, String name, String parentId) {
        super();
        super.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    @Override
    public Map<String, String[]> rules() {
        return Map.of(
                "id", new String[]{"Required", "Unique"},
                "name", new String[]{"Required"},
                "parentId", new String[]{"Required", "ExistParentId"}
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void evalExistParentId(Field field) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(field.get(this).equals("null")){
            return;
        }
        List<Model> models = Model.find(this.getClass(), Map.of(
                "id", (String) field.get(this)
        ));
        if(models.size() == 0)
            this.addError(field.getName(), PARENT_NULL_ERR);
    }
}
