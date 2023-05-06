package Components;

import java.util.HashMap;

public class Database {
    //Model -> HashMap<Model.id, Model>
    private static final HashMap<String, HashMap<String, Model>> models = new HashMap<>();

    public static void save(Model model){
        String modelName = model.getName();
        HashMap<String, Model> myModels = models.computeIfAbsent(modelName, k -> new HashMap<>());
        myModels.put(model.getId(), model);
    }

}
