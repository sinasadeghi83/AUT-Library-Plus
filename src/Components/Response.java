package Components;

public class Response {

    public static final String[] ID_DEFAULT_MSG = new String[]{
            "success", //id: 0
            "duplicate-id", //id: 1
            "not-found", //id: 2
            "not-allowed", //id: 3
            "invalid-pass", //id: 4
            "permission-denied" //id: 5
    };
    private int id;
    private String message;

    public Response(int id) {
        this.id = id;
        this.message = getDefaultMessage(id);
    }

    public Response(int id, String message) {
        this.id = id;
        this.message = message;
        if(this.message == null){
            this.message = getDefaultMessage(id);
        }
    }

    public Response(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDefaultMessage(int id){
        if(ID_DEFAULT_MSG.length <= id){
            System.err.println("Index out of range");
            return "Err!";
        }
        return ID_DEFAULT_MSG[id];
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
