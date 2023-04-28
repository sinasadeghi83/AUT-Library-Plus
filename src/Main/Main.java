package Main;

import Console.Command;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Scanner scanner = new Scanner(System.in);
        Command command = new Command(scanner);
        while(command.hasNext()){
            command.next();
            command.run();
        }
    }
}
