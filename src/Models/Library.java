package Models;

import Components.Model;

import java.util.Date;
import java.util.Map;

public class Library extends Model {
    public static final int RETURN_FALSE = -1;
    private String name;
    private String foundDate;
    private int tableCount;
    private String address;

    public Library(){
        super();
    }

    public Library(String id, String name, String foundDate, int tableCount, String address) {
        super();
        super.id = id;
        this.name = name;
        this.foundDate = foundDate;
        this.tableCount = tableCount;
        this.address = address;
    }

    public Map<String, String[]> rules(){
        return Map.of(
                "id", new String[]{ "Required", "Unique" },
                "name", new String[]{ "Required" },
                "foundDate", new String[]{ "Required", "Year" },
                "tableCount", new String[]{ "Required" },
                "address", new String[]{ "Required" }
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", foundDate='" + foundDate + '\'' +
                ", tableCount=" + tableCount +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
