package model.entities;

import model.dao.FactoryDAO;
import model.dao.OrderDAO;
import model.dao.UserDAO;

import java.sql.Timestamp;


public class Check {
	
	private int id;
	private Order order;
	private int orderId;
	private int adminId;
	private int price;
	private Timestamp paid;
	private User admin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
        if (order == null) {
            OrderDAO dao = FactoryDAO.getInstance().getOrderDAO();
            order = dao.getForId(orderId).get();
        }
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
        if (admin == null) {
            UserDAO dao = FactoryDAO.getInstance().getUserDAO();
            admin = dao.getForId(adminId).get();
        }
        return admin;
    }

	public void setAdmin(User admin) {
		this.admin = admin;
		adminId = admin.getId();
	}

	public Check(int id, int orderId, int price, Timestamp paid, int adminId) {
		this.id = id;
		this.orderId = orderId;
		this.price = price;
		this.paid = paid;
		this.adminId = adminId;
	}
	
	
	
	
}
