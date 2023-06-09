package Components;

import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;
import Exceptions.NotAuthenticatedException;
import Models.User;

import java.util.List;

public interface Auth {

    public String getId();
    public void setId(String id);
    public void enterPassword(String password);
    public String getPassword();
    public Auth identity() throws NotAuthenticatedException;
    public Auth authenticate() throws InvalidPasswordException, ModelNotFoundException;
}
