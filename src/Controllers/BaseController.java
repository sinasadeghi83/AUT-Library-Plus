package Controllers;

import Components.AuthManager;
import Components.Response;
import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;
import Exceptions.NotAuthenticatedException;
import Exceptions.NotAuthorisedException;
import Main.App;
import Models.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public abstract class BaseController {

    public abstract Map<String, List<String>> accessControl();
    public void init(){

    }
    public BaseController(){
        init();
    }

    public Response runAction(String action){
        try {
            User user = new User();
            user.setId(App.getArgs().get(0));
            user.setPassword(App.getArgs().get(1));
            App.getAuthManager().authenticate(user);
            checkAccessControl(action);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Method method = null;
        try {
            method = this.getClass().getMethod(action, List.class);
            return (Response) method.invoke(this, App.getArgs().subList(2, App.getArgs().size()));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkAccessControl(String action) throws NotAuthenticatedException, NotAuthorisedException {
        Map<String, List<String>> access = accessControl();
        List<String> rolesPerms = access.get(action);
        if(!App.getAuthManager().authorise(rolesPerms)){
            throw new NotAuthorisedException();
        }
    }
}
