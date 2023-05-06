package Main;

import Console.Command;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Scanner scanner = new Scanner(System.in);
        Command command = new Command(scanner);
        while(command.hasNext()){
            command.next();
            command.run();
        }
    }

    public static String getAction(){
        return Command.getAction();
    }

    public static List<String> getArgs(){
        return Command.getArgs();
    }
}
