package model.entities;



import java.sql.Timestamp;


public class Check {
	
	private int id;
	private Order order;
	private int price;
	private Timestamp paid;
	private User admin;
	private int orderId;
	private int adminId;

	public Check(int id, Order order, int price, Timestamp paid, User admin, int orderId, int adminId) {
		this.id = id;
		this.order = order;
		this.price = price;
		this.paid = paid;
		this.admin = admin;
		this.adminId = adminId;
		this.orderId = orderId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
        return order;
	}

	public int getPrice() {
		return price;
	}

	public Timestamp getPaid() {
		return paid;
	}

	public void pay() {
		paid = new Timestamp(System.currentTimeMillis());
	}

	public User getAdmin() {
        return admin;
    }

	public void setAdmin(User admin) {
		this.adminId = admin.getId();
		this.admin = admin;
	}

	public  void setOrder(Order order) {
		this.orderId = order.getId();
		this.order = order;
	}

	public int getAdminId() {
		return adminId;
	}

	public int getOrderId() {
		return orderId;
	}
}
