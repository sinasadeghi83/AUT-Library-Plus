package Components;

import java.util.HashMap;

public class Database {
    //Model -> HashMap<Model.id, Model>
    private HashMap<String, HashMap<String, Model>> models;

    public Database(){
        this.models = new HashMap<>();
    }
    public void save(Model model){
        String modelName = model.getMapName();
        HashMap<String, Model> myModels = models.computeIfAbsent(modelName, k -> new HashMap<>());
        myModels.put(model.getId(), model);
    }

    public HashMap<String, Model> find(String name) {
        HashMap<String, Model> results = this.models.get(name);
        if(results == null)
            results = new HashMap<>();
        return results;
    }
}
