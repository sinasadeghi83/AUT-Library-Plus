package Controllers;

import Components.Model;
import Components.Response;
import Main.App;
import Models.Book;
import Models.SellingBook;
import Models.Thesis;

import java.util.List;
import java.util.Map;

public class ResourceController extends BaseController{
    @Override
    public Map<String, List<String>> accessControl() {
        return Map.of(
                "actionAddBook", List.of("manager"),
                "actionAddThesis", List.of("manager"),
                "actionAddSellingBook", List.of("manager")
        );
    }

    public Response actionAddBook(List<String> args){
        Book book = new Book(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), Integer.parseInt(args.get(5)), args.get(6), args.get(7));
        if(!book.validate()){
            String zeroError = (String) book.getErrors().values().toArray()[0];
            if(zeroError.equals(Book.UNIQUE_LIBRARY_ERR) || zeroError.equals(Model.UNIQUE_ERR)){
                return new Response(1); //duplicate-id
            }
            return new Response(2); //not-found
        }
        if(!App.getAuthManager().can("addResource", Map.of("libraryId", book.getLibId()))){
            return new Response(5); //permission-denied
        }
        book.save();
        return new Response(0); //success
    }

    public Response actionAddThesis(List<String> args){
        Thesis thesis = new Thesis(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
        if(!thesis.validate()){
            String zeroError = (String) thesis.getErrors().values().toArray()[0];
            if(zeroError.equals(Book.UNIQUE_LIBRARY_ERR) || zeroError.equals(Model.UNIQUE_ERR)){
                return new Response(1); //duplicate-id
            }
            return new Response(2); //not-found
        }
        if(!App.getAuthManager().can("addResource", Map.of("libraryId", thesis.getLibId()))){
            return new Response(5); //permission-denied
        }
        thesis.save();
        return new Response(0); //success
    }

    public Response actionAddSellingBook(List<String> args){
        SellingBook book = new SellingBook(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), Integer.parseInt(args.get(5)), Integer.parseInt(args.get(6)), Integer.parseInt(args.get(7)), args.get(8), args.get(9));
        if(!book.validate()){
            String zeroError = (String) book.getErrors().values().toArray()[0];
            if(zeroError.equals(SellingBook.UNIQUE_LIBRARY_ERR) || zeroError.equals(Model.UNIQUE_ERR)){
                return new Response(1); //duplicate-id
            }
            return new Response(2); //not-found
        }
        if(!App.getAuthManager().can("addResource", Map.of("libraryId", book.getLibId()))){
            return new Response(5); //permission-denied
        }
        book.save();
        return new Response(0); //success
    }

}
