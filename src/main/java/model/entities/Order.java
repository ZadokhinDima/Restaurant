package model.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
	private int id;
	private List<Meal> meals;
	private User client;
	private LocalDateTime ordered;
	private LocalDateTime done;
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
	public LocalDateTime getOrdered() {
		return ordered;
	}
	public void setOrdered(LocalDateTime ordered) {
		this.ordered = ordered;
	}
	public LocalDateTime getDone() {
		return done;
	}
	public void setDone(LocalDateTime done) {
		this.done = done;
	}
	public Order(int id, List<Meal> meals, User client, LocalDateTime ordered, LocalDateTime done) {
		this.id = id;
		this.meals = meals;
		this.client = client;
		this.ordered = ordered;
		this.done = done;
	}
	
	
}
