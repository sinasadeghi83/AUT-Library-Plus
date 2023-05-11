package Models;

import java.util.HashMap;
import java.util.Map;

public class SellingBook extends Book{
    protected int price, offPercent;
    public SellingBook(){
        super();
    }
    public SellingBook(String id, String title, String author, String pub, String year, int copyCount, int price, int offPercent, String catId, String libId) {
        super(id, title, author, year, pub, copyCount, catId, libId);
        this.price = price;
        this.offPercent = offPercent;
    }

    @Override
    public Map<String, String[]> rules() {
        Map<String, String[]> superRules = new HashMap<>(super.rules());
        superRules.putAll(Map.of(
                "price", new String[]{"Required"},
                "offPercent", new String[]{"Required"}
                )
        );
        return superRules;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOffPercent() {
        return offPercent;
    }

    public void setOffPercent(int offPercent) {
        this.offPercent = offPercent;
    }
}
