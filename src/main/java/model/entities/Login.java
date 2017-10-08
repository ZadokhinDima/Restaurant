package model.entities;


public class Login {
	private int id;
	private String name;
	private String password;


    public Login(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public Login(){

    }

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Login login = (Login) o;

		if (id != login.id) return false;
		if (!name.equals(login.name)) return false;
		return password.equals(login.password);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + name.hashCode();
		result = 31 * result + password.hashCode();
		return result;
	}
}
