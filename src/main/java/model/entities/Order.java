package model.entities;

import model.dao.FactoryDAO;

import java.sql.Timestamp;
import java.util.List;

public class Order {
	private int id;
	private List<Meal> meals;
	private User client;
	private Timestamp ordered;
	private Timestamp done;

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
	}

	public Timestamp getOrdered() {
		return ordered;
	}

	public void make() {
		this.ordered = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getDone() {
		return done;
	}

	public void setDone(Timestamp done) {
		this.done = done;
	}

	public Order(int id, int client, Timestamp ordered, Timestamp done) {
		this.id = id;
		this.client = FactoryDAO.getInstance().getUserDAO().getForId(client).get();
		this.ordered = ordered;
		this.done = done;
	}
	
	
}
