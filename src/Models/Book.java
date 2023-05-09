package Models;

import Components.Model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book extends Resource {

    protected String pub;
    public Book(){
        super();
    }
    public Book(String id, String title, String author, String pub, String year, int copyCount, String catId, String libId) {
        super(id, title, author, year, copyCount, catId, libId);

        this.pub = pub;
    }

    @Override
    public Map<String, String[]> rules() {
        Map<String, String[]> superRules = new HashMap<>(super.rules());
        superRules.put(
                "pub", new String[]{"Required"}
        );
        return superRules;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }
}
