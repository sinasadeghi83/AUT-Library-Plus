package Components;

import java.util.HashMap;

public class Database {
    //Model -> HashMap<Model.id, Model>
    private static final HashMap<String, HashMap<String, Model>> models = new HashMap<>();

    public static void save(Model model){
        String modelName = Model.getName(model.getClass());
        HashMap<String, Model> myModels = models.get(modelName);
        if(myModels == null){
            myModels = new HashMap<>();
            models.put(modelName, myModels);
        }
        myModels.put(model.getId(), model);
    }

    public static HashMap<String, Model> find(String name) {
        return models.get(name);
    }
}
