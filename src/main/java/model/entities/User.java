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
	
	public User(int id, String name, Date birthDate, Role role) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (!name.equals(user.name)) return false;
		if (!birthDate.equals(user.birthDate)) return false;
		return role == user.role;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + name.hashCode();
		result = 31 * result + birthDate.hashCode();
		result = 31 * result + role.hashCode();
		return result;
	}
}
