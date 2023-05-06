package Exceptions;

public class NotAuthorisedException extends Exception {
    @Override
    public String getMessage() {
        return "permission-denied";
    }
}
