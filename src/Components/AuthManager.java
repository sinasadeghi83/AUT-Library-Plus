package Components;

import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;
import Exceptions.NotAuthenticatedException;
import Models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthManager {
    private Auth auth = null;
    //User id -> roles
    private Map<String, List<String>> usersRoles;
    //Roles -> permissions
    private Map<String, List<String>> rolesPerms;

    public AuthManager(){
        usersRoles = Map.of(
                "admin", List.of("admin")
        );

        rolesPerms = Map.of(
                "admin", List.of("addLibrary")
        );
    }

    public String getUserId(){
        return auth.getId();
    }

    public Auth identity() throws NotAuthenticatedException {
        return auth.identity();
    }

    public boolean authorise(List<String> rolesPerms) throws NotAuthenticatedException {
        List<String> userRoles = usersRoles.get(this.getUserId());
        for(String rolePerm:
                rolesPerms){
            if(userRoles.contains(rolePerm)){
                return true;
            }

            if(this.can(rolePerm)){
                return true;
            }
        }
        return false;
    }

    public boolean can(String perm){
        List<String> userRoles = usersRoles.get(this.getUserId());
        for (String userRole :
                userRoles) {
            List<String> perms = this.rolesPerms.get(userRole);
            if (perms.contains(perm))
                return true;
        }
        return false;
    }



    public void authenticate(Auth auth) throws ModelNotFoundException, InvalidPasswordException {
        this.auth = auth.authenticate();
    }
}
