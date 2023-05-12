package Models;

import Components.Model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static Models.Resource.LIBRARY_NULL_ERR;

public class Borrow extends Model {

    public static final String RESOURCE_NULL_ERR = "This resource doesn't exist";
    public static final String RESOURCE_NOT_AVAILABLE = "This resource isn't available";
    private String userId, resId, libId, date, hour;

    public Borrow(String userId, String resId, String libId, String date, String hour) {
        this.userId = userId;
        this.resId = resId;
        this.libId = libId;
        this.date = date;
        this.hour = hour;
        super.id = userId+"-"+resId+"-"+libId;
    }

    @Override
    public Map<String, String[]> rules() {
        return Map.of(
                "id", new String[]{"Required"},
                "resId", new String[]{"Required", "ExistResource"},
                "libId", new String[]{"Required", "ExistLibrary"},
                "date", new String[]{"Required", "Date"},
                "hour", new String[]{"Required", "Hour"}
        );
    }

    public boolean checkAvailability() {
        Resource resource = Resource.find(Map.of(
                "resId", this.resId,
                "libId", this.libId
        )).get(0);

        List<Model> borrows = Model.find(Borrow.class, Map.of(
                "resId", this.resId,
                "libId", this.libId
        ));

        if (resource instanceof TreasureBook || resource instanceof SellingBook || resource.getCopyCount() == borrows.size()) {
            this.addError("id", RESOURCE_NOT_AVAILABLE);
            return false;
        }
        return true;
    }

    @Override
    public boolean validate() {
        return super.validate() && checkAvailability();
    }

    public void evalExistResource(Field field) throws IllegalAccessException {
        List<Resource> resources = Resource.find(Map.of(
           "resId", field.get(this),
           "libId", this.libId
        ));
        
        if(resources.size() == 0)
            this.addError(field.getName(), RESOURCE_NULL_ERR);
    }
    
    public void evalExistLibrary(Field field) throws IllegalAccessException {
        List<Model> models = Model.find(Library.class, Map.of(
                "id", (String) field.get(this)
        ));
        if(models.size() == 0)
            this.addError(field.getName(), LIBRARY_NULL_ERR);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        super.id = userId+"-"+resId+"-"+libId;
        this.userId = userId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        super.id = userId+"-"+resId+"-"+libId;
        this.resId = resId;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        super.id = userId+"-"+resId+"-"+libId;
        this.libId = libId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "userId='" + userId + '\'' +
                ", resId='" + resId + '\'' +
                ", libId='" + libId + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
