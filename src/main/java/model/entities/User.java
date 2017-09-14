package model.entities;

import java.time.LocalDate;

public class User {
	private int id;
	private String name;
	private LocalDate birthDate;
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
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Role getRole() {
		return role;
	}
	
	public User(int id, String name, LocalDate birthDate, Role role) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.role = role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}	
