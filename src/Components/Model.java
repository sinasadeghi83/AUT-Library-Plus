package Components;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public abstract class Model {
    private static final String REQUIRED_ERR = "This field is required:";
    private static final String DATE_ERR = "This field should be Date:";

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

    public String getName(){
        return this.getClass().getName();
    }

    public boolean save(){
        if(!this.validate()){
            return false;
        }
        Database.save(this);
        return true;
    }

    private void checkRules() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        errors.clear();
        Map<String, String[]> rules = this.rules();
        for (String fieldName :
                rules.keySet()) {
            for (String condition:
                    rules.get(fieldName)) {
                Field field = this.getClass().getField(fieldName);
                Method evalCondition = Model.class.getMethod("eval"+condition, Field.class);
                evalCondition.invoke(this, field);
            }
        }
    }

    private void checkRequired(Field field) throws IllegalAccessException {
        if(field.get(field.getType()) == null){
            this.addError(field.getName(), REQUIRED_ERR);
        }
    }

    private void checkDate(Field field){
        String dateStr = field.getClass().toString();
        if(!isValidDate(dateStr)) {
            this.addError(field.getName(), DATE_ERR);
        }
    }

    //TODO
    private void checkUnique(Field field){
    }

    public boolean validate(){
        try {
            this.checkRules();
        } catch (Exception e) {
            this.addError(null, e.getMessage());
        }
        return errors.size() > 0;
    }

    private boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private void addError(String field, String error) {
        errors.put(field, error);
    }
}
