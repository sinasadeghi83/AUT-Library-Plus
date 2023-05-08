package Controllers;

import Components.Model;
import Components.Response;
import Main.App;
import Models.Library;

import java.util.List;
import java.util.Map;

public class LibraryController extends BaseController{

    public Map<String, List<String>> accessControl(){
        return Map.of(
                "actionAddLibrary", List.of("admin")
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
}
