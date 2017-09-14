package model.entities;

import java.time.LocalDateTime;

public class Check {
	
	private int id;
	private Order order;
	private int price;
	private LocalDateTime paid;
	private User admin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public LocalDateTime getPaid() {
		return paid;
	}
	public void setPaid(LocalDateTime paid) {
		this.paid = paid;
	}
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public Check(int id, Order order, int price, LocalDateTime paid, User admin) {
		super();
		this.id = id;
		this.order = order;
		this.price = price;
		this.paid = paid;
		this.admin = admin;
	}
	
	
	
	
}
