package Controllers;

import Components.Model;
import Components.Response;
import Main.App;
import Models.Borrow;
import Models.Library;

import java.util.List;
import java.util.Map;

public class LibraryController extends BaseController{

    public Map<String, List<String>> accessControl(){
        return Map.of(
                "actionAddLibrary", List.of("admin"),
                "actionBorrow", List.of("student", "staff")
        );
    }
    public Response actionAddLibrary(List<String> args){
        Library library = new Library(args.get(0), args.get(1), args.get(2), Integer.parseInt(args.get(3)), args.get(4));
        boolean isSaved = library.save();
        if(!isSaved){
            if (Model.UNIQUE_ERR.equals(library.getErrors().values().toArray()[0])) {
                return new Response(1);
            }
        }
        return new Response(0);
    }

    public Response actionBorrow(List<String> args){
        Borrow borrow = new Borrow(App.getAuthManager().getUserId(), args.get(1), args.get(0), args.get(2), args.get(3));
        boolean userCan = App.getAuthManager().can("borrow", Map.of("borrowObj", borrow));
        boolean isValid = borrow.validate();
        if(!isValid){
            if(borrow.getErrors().containsKey("libId") || borrow.getErrors().containsKey("resId")){
//                System.out.println(borrow.errorToString());
                return new Response(2); //not-found
            }else{
                return new Response(3); //not-allowed
            }
        }

        if(!userCan)
            return new Response(3); //not-allowed

        borrow.save();

        return new Response(0); //success
    }
}
