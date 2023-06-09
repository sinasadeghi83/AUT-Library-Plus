package Main;

import Components.Auth;
import Components.AuthManager;
import Components.Database;
import Components.Model;
import Console.Command;
import Models.User;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {
    private static AuthManager authManager;
    private static Database db;
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        setupApp();
        Scanner scanner = new Scanner(System.in);
        Command command = new Command(scanner);
        while(command.hasNext()){
            authManager = new AuthManager();
            command.next();
            command.run();
        }
//        printAll();
    }

    private static void printAll(){
        HashMap<String, HashMap<String, Model>> models = getDb().all();
        for (String modelName :
                models.keySet()) {
            System.out.println("Model Name: " + modelName);
            for (Model model :
                    models.get(modelName).values()) {
                System.out.println("\t" + "id: " + model.getId());
                System.out.println("\t" + "Class: " + model.getClass());
                System.out.println("\t" + "Model: " + model);
            }
        }
    }

    private static void setupApp() {
        db = new Database();
        User admin = new User("admin", "AdminPass", "Admin", "Adminzadeh", "123456", "1348", "");
        admin.save();
    }

    public static AuthManager getAuthManager(){
        return authManager;
    }

    public static Database getDb(){
        return db;
    }

    public static String getAction(){
        return Command.getAction();
    }

    public static List<String> getArgs(){
        return Command.getArgs();
    }
}
