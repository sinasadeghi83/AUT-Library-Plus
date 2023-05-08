package Controllers;

import Components.Model;
import Components.Response;
import Models.Category;

import java.util.List;
import java.util.Map;

public class CategoryController extends BaseController{
    @Override
    public Map<String, List<String>> accessControl() {
        return Map.of(
                "actionAddCategory", List.of("admin")
        );
    }

    public Response actionAddCategory(List<String> args){
        Category cat = new Category(args.get(0), args.get(1), args.get(2));
        boolean isSaved = cat.save();
        if(!isSaved){
            if(cat.getErrors().containsValue(Category.UNIQUE_ERR)){
                //Category.UNIQUE_ERR
                return new Response(1); //dup-id
            }
            //Category.PARENT_NULL_ERR
            return new Response(2); //not-found
        }
        return new Response(0); //success
    }
}
