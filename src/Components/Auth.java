package Components;

import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;
import Exceptions.NotAuthenticatedException;
import Models.User;

import java.util.List;

public interface Auth {

    public String getId();
    public void enterPassword(String password);
    public Auth identity() throws NotAuthenticatedException;
    public Auth authenticate() throws InvalidPasswordException, ModelNotFoundException;
}
