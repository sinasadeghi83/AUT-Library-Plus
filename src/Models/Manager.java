package Models;

import Main.App;

public class Manager extends User{
    public Manager(String id, String password, String firstName, String lastName, String nationalCode, String birthdate, String address) {
        super(id, password, firstName, lastName, nationalCode, birthdate, address);
    }

    @Override
    protected void afterSave() {
        super.afterSave();
        App.getAuthManager().assignRole(this, "manager");
    }
}
