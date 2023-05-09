package Rbac;

import Components.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RbacConfig {
    public static Map<String, List<String>> getUsersRoles(){
        return new HashMap<>(Map.of(
                "admin", List.of("admin", "manager")
        ));
    }

    public static Map<String, List<String>> getRolesPerms(){
        return new HashMap<>(Map.of(
                "admin", List.of("addLibrary", "removeUser"),
                "manager", List.of("addResource")
        ));
    }

    public static Map<String, Rule> getPermsRule(){
        return new HashMap<>(Map.of(
                "removeUser", new RemoveUserRule(),
                "addResource", new AddResourceRule()
        ));
    }
}
