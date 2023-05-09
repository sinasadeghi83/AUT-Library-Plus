package Controllers;

import Components.Response;
import Main.App;
import Models.Book;
import Models.Thesis;

import java.util.List;
import java.util.Map;

public class ResourceController extends BaseController{
    @Override
    public Map<String, List<String>> accessControl() {
        return Map.of(
                "actionAddBook", List.of("manager"),
                "actionAddThesis", List.of("manager")
        );
    }

    public Response actionAddBook(List<String> args){
        Book book = new Book(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), Integer.parseInt(args.get(5)), args.get(6), args.get(7));
        if(!book.validate()){
            String zeroError = (String) book.getErrors().values().toArray()[0];
            if(zeroError.equals(Book.UNIQUE_LIBRARY_ERR)){
                return new Response(1); //duplicate-id
            }
            return new Response(2); //not-found
        }
        if(!App.getAuthManager().can("addBook", Map.of("libraryId", book.getLibId()))){
            return new Response(5); //permission-denied
        }
        book.save();
        return new Response(0); //success
    }

}
