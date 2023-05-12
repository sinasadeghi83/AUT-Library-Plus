package Models;

import Components.Auth;
import Components.Model;
import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User extends Model implements Auth {
    protected String password, firstName, lastName, nationalCode, birthdate, address;

    public User(){
        super();
    }

    public User(String id, String password, String firstName, String lastName, String nationalCode, String birthdate, String address) {
        super();
        super.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.birthdate = birthdate;
        this.address = address;
    }

    @Override
    public Map<String, String[]> rules(){
        return Map.of(
                "id", new String[]{ "Required", "Unique" },
                "password", new String[]{ "Required" },
                "firstName", new String[]{"Required"},
                "lastName", new String[]{"Required"},
                "nationalCode", new String[]{"Required"},
                "birthdate", new String[]{ "Required", "Year" },
                "address", new String[]{ "Required" }
        );
    }

    @Override
    public String getMapName(){
        return Auth.class.getName();
    }

    public static List<User> find(Map<String, Object> where){
        List<Model> models;
        List<User> users = new ArrayList<>();
        try {
            models = Model.find(Auth.class, where);
            for (Model model :
                    models) {
                users.add((User) model);
            }
            return users;
        } catch (Exception e) {
            return users;
        }
    }

    @Override
    public User identity() {
        return this;
    }

    @Override
    public Auth authenticate() throws InvalidPasswordException, ModelNotFoundException {
        List<User> models = find(Map.of(
                "id", this.getId()
        ));
        if(models.size() == 0)
            throw new ModelNotFoundException();
        Auth auth = models.get(0);
        if(!auth.getPassword().equals(this.password)){
            throw new InvalidPasswordException();
        }
        return auth;
    }

    @Override
    public void enterPassword(String password) {
        setPassword(password);
    }
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Fine getFine(){
        List<Model> fines = Model.find(Fine.class, Map.of(
                "userId", this.getId()
        ));

        if(fines.size() == 0)
            return null;
        return (Fine)fines.get(0);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
