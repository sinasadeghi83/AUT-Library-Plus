package Rbac;

import Components.Auth;
import Components.Rule;

import java.util.HashMap;
import java.util.Map;

public class RemoveUserRule extends Rule {

    //TODO: Check if user borrowed something or is in debt
    @Override
    public boolean execute(Auth auth, String item, Map<String, Object> params) {
        return true;
    }

}
