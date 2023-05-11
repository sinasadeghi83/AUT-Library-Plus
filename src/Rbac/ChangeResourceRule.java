package Rbac;

import Components.Auth;
import Components.Rule;
import Exceptions.NotAuthenticatedException;
import Main.App;
import Models.Manager;

import java.util.Map;

public class ChangeResourceRule extends Rule {
    @Override
    public boolean execute(Auth auth, String item, Map<String, Object> params) {
        try {
            Manager manager = (Manager) App.getAuthManager().identity();
            return manager.getLibId().equals(params.get("libraryId"));
        } catch (NotAuthenticatedException e) {
            return false;
        }
    }
}
