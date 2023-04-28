package Console;

import Components.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Command {

    private final Map<String, String> routes = Map.of(
            "add-library", "LibraryController"
    );
    private Scanner input;
    private String action;
    private ArrayList<String> args;

    public Command(Scanner input){
        this.input = input;
    }

    public boolean hasNext() {
        return input.hasNextLine();
    }

    public void next() {
        String rawCmd = input.nextLine();
        String[] actionArgs = rawCmd.split("#");
        action = actionArgs[0];
        if(actionArgs.length > 1) {
            Collections.addAll(this.args, actionArgs[1].split("\\|"));
        }
    }

    public void run() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //add-library -> addLibrary
        String parsedAction = this.parseAction();
        String className = routes.get(action);
        Class<? extends String> controller = className.getClass();
        Method actionMethod = controller.getMethod(parsedAction, ArrayList.class);
        Object controllerInstance = controller.getDeclaredConstructor().newInstance();
        Response response = (Response) actionMethod.invoke(controllerInstance, args);
        System.out.println(response);
    }

    private String parseAction() {
        StringBuilder builder = new StringBuilder(action);
        int index;
        while ((index = builder.indexOf("-")) != -1){
            builder.deleteCharAt(index);
            builder.setCharAt(index, Character.toUpperCase(builder.charAt(index)));
        }
        return builder.toString();
    }
}
