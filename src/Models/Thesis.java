package Models;

import Components.Model;

import java.util.HashMap;
import java.util.Map;

public class Thesis extends Resource {
    public static final String UNIVERSITY_PUB = "uni-pub";

    protected String profName;
    public Thesis(){
        super();
    }
    public Thesis(String id, String title, String author, String profName, String year, String catId, String libId) {
        super(id, title, author, year, 1, catId, libId);
        this.profName = profName;
    }

    @Override
    public Map<String, String[]> rules() {
        Map<String, String[]> superRules = new HashMap<>(super.rules());
        superRules.put(
                "profName", new String[]{"Required"}
        );
        return superRules;
    }
}
