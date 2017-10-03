package model.entities;

public class LoginBuilder {

    private int id;
    private String name;
    private String password;


    public LoginBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public LoginBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public LoginBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Login createLogin() {
        return new Login(id, name, password);
    }
}