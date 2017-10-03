package model.entities;

import java.sql.Date;

public class UserBuilder {

    private int id;
    private String name;
    private Date birthDate;
    private Role role;

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder setRole(String role) {
        this.role = Role.valueOf(role.toUpperCase());
        return this;
    }



    public User createUser() {
        return new User(id, name, birthDate, role);
    }
}