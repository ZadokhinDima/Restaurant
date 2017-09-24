package model.entities;

import java.sql.Date;
import java.time.LocalDate;

public class User {
	private int id;
	private String name;
	private Date birthDate;
	private Role role;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Role getRole() {
		return role;
	}
	
	public User(int id, String name, Date birthDate, String role) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.role = Role.valueOf(role.toUpperCase());
	}

}	
