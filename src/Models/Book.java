package Models;

import Components.Model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class Book extends Model {

    public static final String CATEGORY_NULL_ERR = "This field should not be null";
    public static final String UNIQUE_LIBRARY_ERR = "Book should be unique in this library";
    public static final String LIBRARY_NULL_ERR = "Library with this libId doesn't exist";
    private String title, author, pub, printYear, copyCount, catId, libId;

    public Book() {
        super();
    }

    public Book(String id, String title, String author, String pub, String printYear, String copyCount, String catId, String libId) {
        super();
        super.id = id;
        this.title = title;
        this.author = author;
        this.pub = pub;
        this.printYear = printYear;
        this.copyCount = copyCount;
        this.catId = catId;
        this.libId = libId;
    }

    @Override
    public Map<String, String[]> rules() {
        return Map.of(
                "id", new String[]{"Required", "UniqueLibrary"},
                "title", new String[]{"Required"},
                "author", new String[]{"Required"},
                "pub", new String[]{"Required"},
                "printYear", new String[]{"Required"},
                "copyCount", new String[]{"Required"},
                "catId", new String[]{"Required", "ExistCategory"},
                "libId", new String[]{"Required", "ExistLibrary"}

        );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getPrintYear() {
        return printYear;
    }

    public void setPrintYear(String printYear) {
        this.printYear = printYear;
    }

    public String getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(String copyCount) {
        this.copyCount = copyCount;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public void evalUniqueLibrary(Field field) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Model> models = Model.find(Book.class, Map.of(
                field.getName(), (String) field.get(this),
                "libId", (String) this.libId
        ));
        if(models.size() > 0)
            this.addError(field.getName(), UNIQUE_LIBRARY_ERR);
    }

    public void evalExistCategory(Field field) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(field.get(this).equals("null")){
            return;
        }
        List<Model> models = Model.find(Category.class, Map.of(
                "id", (String) field.get(this)
        ));
        if(models.size() == 0)
            this.addError(field.getName(), CATEGORY_NULL_ERR);
    }

    public void evalExistLibrary(Field field) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Model> models = Model.find(Library.class, Map.of(
                "id", (String) field.get(this)
        ));
        if(models.size() == 0)
            this.addError(field.getName(), LIBRARY_NULL_ERR);
    }
}
