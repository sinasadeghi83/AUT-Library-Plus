package Models;

import Main.App;

public class Student extends User{
    public Student(String id, String password, String firstName, String lastName, String nationalCode, String birthdate, String address) {
        super(id, password, firstName, lastName, nationalCode, birthdate, address);
    }

    @Override
    protected boolean afterSave() {
        super.afterSave();
        App.getAuthManager().assignRole(this, "student");
        return true;
    }
}
