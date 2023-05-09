package Models;

import Components.Model;
import Main.App;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager extends User{
    public static final String LIBRARY_NULL_ERR = "LibId doesn't exist";
    private String libId;
    public Manager(String id, String password, String firstName, String lastName, String nationalCode, String birthdate, String address, String libId) {
        super(id, password, firstName, lastName, nationalCode, birthdate, address);
        this.libId = libId;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    @Override
    public Map<String, String[]> rules(){
        Map<String, String[]> result = new HashMap<>(Map.of(
                "libId", new String[]{"Required", "ExistLibrary"}
        ));
        result.putAll(super.rules());
        return result;
    }

    @Override
    protected boolean afterSave() {
        super.afterSave();
        App.getAuthManager().assignRole(this, "manager");
        return true;
    }

    public void evalExistLibrary(Field field) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Model> models = Model.find(Library.class, Map.of(
                "id", (String) field.get(this)
        ));
        if(models.size() == 0)
            this.addError(field.getName(), LIBRARY_NULL_ERR);
    }
}
