package Models;

import Components.Auth;
import Components.Model;
import Exceptions.InvalidPasswordException;
import Exceptions.ModelNotFoundException;

import java.util.List;
import java.util.Map;

public class User extends Model implements Auth {
    private String password, firstName, lastName, nationalCode, birthdate, address;

    public Map<String, String[]> rules(){
        return Map.of(
                "id", new String[]{ "Required", "Unique" },
                "password", new String[]{ "Required" },
                "firstName", new String[]{"Required"},
                "lastName", new String[]{"Required"},
                "nationalCode", new String[]{"Required"},
                "birthdate", new String[]{ "Required", "Date" },
                "address", new String[]{ "Required" }
        );
    }

    @Override
    public User identity() {
        return this;
    }

    @Override
    public Auth authenticate() throws InvalidPasswordException, ModelNotFoundException {
        List<Model> models;
        try {
            models = User.find(User.class, Map.of(
                    "id", this.getId(),
                    "password", password
            ));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        if(models.size() == 0)
            throw new ModelNotFoundException();
        User user = (User)models.get(0);
        if(!user.getPassword().equals(this.password)){
            throw new InvalidPasswordException();
        }
        return user;
    }

    @Override
    public void enterPassword(String password) {
        setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
