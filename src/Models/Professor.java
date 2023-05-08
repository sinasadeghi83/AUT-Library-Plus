package Models;

import Main.App;

public class Professor extends Staff{
    public Professor(String id, String password, String firstName, String lastName, String nationalCode, String birthdate, String address) {
        super(id, password, firstName, lastName, nationalCode, birthdate, address);
    }

    @Override
    protected void afterSave() {
        super.afterSave();
        App.getAuthManager().assignRole(this, "professor");
    }
}
