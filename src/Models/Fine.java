package Models;

import Components.Model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Fine extends Model {

    private static final String USER_NOT_FOUND = "This user doesn't exist";
    private String userId;
    private int fine;

    public Fine(String userId, int fine) {
        this.userId = userId;
        this.fine = fine;
    }

    @Override
    public Map<String, String[]> rules() {
        return Map.of(
                "id", new String[]{"Required"},
                "userId", new String[]{"Required", "ExistUser"},
                "fine", new String[]{"Required"}
        );
    }

    public void evalExistUser(Field field) throws IllegalAccessException {
        List<User> user = User.find(Map.of(
           "id", field.get(this)
        ));

        if(user.size() == 0){
            this.addError(field.getName(), USER_NOT_FOUND);
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}
