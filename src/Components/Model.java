package Components;

import Console.Command;
import Main.App;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Model {
    public static final String REQUIRED_ERR = "This field is required:";
    public static final String DATE_ERR = "This field should be Date:";
    public static final String YEAR_ERR = "This field should be Year:";
    public static final String UNIQUE_ERR = "This field should be unique";

    protected String id;

    //Field's name -> error message
    protected Map<String, String> errors;
    public abstract Map<String, String[]> rules();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Model(){
        errors = new HashMap<String, String>();
    }

    public static String getName(Class classObj){
        return classObj.getName();
    };

    public boolean save(){
        if(!this.validate()){
            return false;
        }
        App.getDb().save(this);
        return true;
    }

    public static List<Model> find(Class classObj, Map<String, Object> where) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        HashMap<String, Model> models = App.getDb().find(Model.getName(classObj));
        List<Model> result = new ArrayList<>();
        for (Model model :
                models.values()) {
            boolean isGood = true;
            for(String fieldName :
                    where.keySet()){
                Method getter = classObj.getMethod(Command.parseAction("get-" + fieldName));
                Object fieldValue = getter.invoke(model);
                if(!fieldValue.equals(where.get(fieldName))){
                    isGood = false;
                    break;
                }
            }
            if(isGood){
                result.add(model);
            }
        }
        return result;
    }

    private void checkRules() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        errors.clear();
        Map<String, String[]> rules = this.rules();
        for (String fieldName :
                rules.keySet()) {
            for (String condition:
                    rules.get(fieldName)) {
                Field field = getFieldUpTo(this.getClass(), null, fieldName);
                Method evalCondition = getMethodUpTo(this.getClass(), null, "eval"+condition, Field.class);
                field.setAccessible(true);
                evalCondition.invoke(this, field);
            }
        }
    }

    private void evalRequired(Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(field.get(this) == null){
            this.addError(field.getName(), REQUIRED_ERR);
        }
    }

    private void evalDate(Field field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String dateStr = (String) field.get(this);
        if(!isValidDate(dateStr)) {
            this.addError(field.getName(), DATE_ERR);
        }
    }

    private void evalYear(Field field) throws IllegalAccessException {
        String yearStr = (String) field.get(this);
        try {
            new SimpleDateFormat("yyyy").parse(yearStr);
        } catch (ParseException e) {
            this.addError(field.getName(), YEAR_ERR);
        }
    }

    private void evalUnique(Field field) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        Object fieldData = field.get(this);
        List<Model> models = find(this.getClass(), Map.of(field.getName(), fieldData));
        if(models.size() > 0)
            this.addError(field.getName(), UNIQUE_ERR);
    }

    private Object getFieldByGetter(Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getter = this.getClass().getDeclaredMethod(Command.parseAction("get-" + field.getName()));
        return getter.invoke(this);
    }

    public boolean validate(){
        try {
            this.checkRules();
        } catch (Exception e) {
            System.out.println(e);
            this.addError(null, e.getMessage());
        }
        return errors.size() == 0;
    }

    private boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms");
        dateFormat.setLenient(true);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    protected void addError(String field, String error) {
        errors.put(field, error);
    }

    /*
    Thanks to John B
    Reference: https://stackoverflow.com/questions/16966629/what-is-the-difference-between-getfields-and-getdeclaredfields-in-java-reflectio
     */
    private Iterable<Field> getFieldsUpTo(Class<?> startClass,
                                                Class<?> exclusiveParent) {
        List<Field> currentClassFields = new ArrayList<Field>(List.of(startClass.getDeclaredFields()));
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null &&
                (exclusiveParent == null || !(parentClass.equals(exclusiveParent)))) {
            List<Field> parentClassFields =
                    (List<Field>) getFieldsUpTo(parentClass, exclusiveParent);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }

    private Field getFieldUpTo(Class<?> startClass,
                                          Class<?> exclusiveParent, String name) {
        Field field = null;
        try {
            field = startClass.getDeclaredField(name);
        }catch (NoSuchFieldException e) {
            Class<?> parentClass = startClass.getSuperclass();

            if (parentClass != null &&
                    (exclusiveParent == null || !(parentClass.equals(exclusiveParent)))) {
                field = getFieldUpTo(parentClass, exclusiveParent, name);
            }
        }

        return field;
    }

    private Method getMethodUpTo(Class<?> startClass, Class<?> exclusiveParent, String name, Class<?>... args) {
        Method method = null;
        try {
            method = startClass.getDeclaredMethod(name, args);
        } catch (NoSuchMethodException e) {
            Class<?> parentClass = startClass.getSuperclass();
            if(parentClass != null && (exclusiveParent == null || !(parentClass.equals(exclusiveParent)))){
                method = getMethodUpTo(parentClass, exclusiveParent, name, args);
            }
        }
        return method;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
