package Components;

import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;
import Exceptions.NotAuthenticatedException;
import Models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthManager {
    private static Auth auth = null;
    //User id -> roles
    private static final Map<String, List<String>> usersRoles = new HashMap<>();

    public static String getUserId(){
        return auth.getId();
    }

    public static Auth identity() throws NotAuthenticatedException {
        return auth.identity();
    }

    public static boolean authorise(List<String> roles) throws NotAuthenticatedException {
        List<String> userRoles = usersRoles.get(AuthManager.getUserId());
        for (String role :
                roles) {
            if(userRoles.contains(role)){
                return true;
            }
        }

        return false;
    }



    public static void authenticate(Auth auth) throws ModelNotFoundException, InvalidPasswordException {
        AuthManager.auth = auth.authenticate();
    }
}
