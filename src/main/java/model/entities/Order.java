package model.entities;


import java.sql.Timestamp;
import java.util.List;

public class Order {
	private int id;
	private List<Meal> meals;
	private User client;
	private int clientId;
	private Timestamp ordered;
	private int accepted;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
		this.clientId = client.getId();
	}

	public Timestamp getOrdered() {
		return ordered;
	}

    public void setOrdered(Timestamp ordered) {
        this.ordered = ordered;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public Order(int id, User client, Timestamp ordered, int accepted, List<Meal> meals, int clientId) {
		this.id = id;
		this.client = client;
		this.ordered = ordered;
		this.accepted = accepted;
		this.meals = meals;
		this.clientId = clientId;
	}


	public int getClientId() {
		return clientId;
	}
}
