package Models;

import Components.Model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class Book extends Resource {
    public Book(){
        super();
    }
    public Book(String id, String title, String author, String pub, String year, String copyCount, String catId, String libId) {
        super(id, title, author, pub, year, copyCount, catId, libId);
    }
}
