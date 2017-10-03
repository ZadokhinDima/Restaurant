package model.entities;

import java.sql.Timestamp;

public class CheckBuilder {
    private int id;
    private Order order;
    private int price;
    private Timestamp paid;
    private User admin;
    private int orderId;
    private int adminId;

    public CheckBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CheckBuilder setOrder(Order order) {
        this.order = order;
        return this;
    }

    public CheckBuilder setPrice(int price) {
        this.price = price;
        return this;
    }

    public CheckBuilder setPaid(Timestamp paid) {
        this.paid = paid;
        return this;
    }

    public CheckBuilder setAdmin(User admin) {
        this.admin = admin;
        return this;
    }

    public CheckBuilder setAdminId(int adminId) {
        this.adminId = adminId;
        return this;
    }
    public CheckBuilder setOrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }

    public Check createCheck() {
        return new Check(id, order, price, paid, admin, orderId, adminId);
    }
}