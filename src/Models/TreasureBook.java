package Models;

import java.util.HashMap;
import java.util.Map;

public class TreasureBook extends Book{

    private String donor;

    public TreasureBook(){
        super();
    }
    public TreasureBook(String id, String title, String author, String pub, String year, String donor, String catId, String libId) {
        super(id, title, author, pub, year, 1, catId, libId);
        this.donor = donor;
    }

    @Override
    public Map<String, String[]> rules() {
        Map<String, String[]> superRules = new HashMap<>(super.rules());
        superRules.put(
                "donor", new String[]{"Required"}
        );
        return superRules;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }
}
