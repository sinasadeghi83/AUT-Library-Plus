package Components;

import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;
import Exceptions.NotAuthenticatedException;
import Models.User;
import Rbac.RbacConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthManager {
    private Auth auth = null;
    //User id -> roles
    private static final Map<String, List<String>> usersRoles = RbacConfig.getUsersRoles();;
    //Roles -> permissions
    private static Map<String, List<String>> rolesPerms = RbacConfig.getRolesPerms();
    //Permssion -> Rule
    private static Map<String, Rule> permsRule = RbacConfig.getPermsRule();

    public String getUserId(){
        return auth.getId();
    }

    public Auth identity() throws NotAuthenticatedException {
        return auth.identity();
    }

    public void assignRole(Auth auth, String role){
        List<String> userRoles = usersRoles.computeIfAbsent(auth.getId(), k -> new ArrayList<>());
//        List<String> userRoles = this.usersRoles.get(auth.getId());
//        if(userRoles == null){
//            userRoles = new ArrayList<>();
//            this.usersRoles.put(auth.getId(), userRoles);
//        }
        userRoles.add(role);
        if(!rolesPerms.containsKey(role)){
            rolesPerms.put(role, new ArrayList<>());
        }
    }

    public boolean authorise(List<String> rolesPerms) throws NotAuthenticatedException {
        List<String> userRoles = usersRoles.get(this.getUserId());
        if(userRoles == null){
            return false;
        }
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
        return this.can(perm, new HashMap<>());
    }

    public boolean can(String perm, Map<String, Object> params){
        List<String> userRoles = usersRoles.get(this.getUserId());
        for (String userRole :
                userRoles) {
            List<String> perms = this.rolesPerms.get(userRole);
            if (perms.contains(perm)) {
                if(permsRule.containsKey(perm)) {
                    Rule rule = permsRule.get(perm);
                    return rule.execute(this.auth, perm, params);
                }
                return true;
            }
        }
        return false;
    }

    public void authenticate(Auth auth) throws ModelNotFoundException, InvalidPasswordException {
        this.auth = auth.authenticate();
    }
}
