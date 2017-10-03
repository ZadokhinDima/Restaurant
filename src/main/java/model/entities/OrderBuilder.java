package model.entities;

import java.sql.Timestamp;
import java.util.List;

public class OrderBuilder {
    private int id;
    private User client;
    private Timestamp ordered;
    private int accepted;
    private List<Meal> meals;
    private int clientId;

    public OrderBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setClient(User client) {
        this.client = client;
        return this;
    }

    public OrderBuilder setOrdered(Timestamp ordered) {
        this.ordered = ordered;
        return this;
    }

    public OrderBuilder setAccepted(int accepted) {
        this.accepted = accepted;
        return this;
    }

    public OrderBuilder setClientId(int clientId) {
        this.clientId = clientId;
        return this;
    }

    public OrderBuilder setMeals(List<Meal> meals) {
        this.meals = meals;
        return this;
    }

    public Order createOrder() {
        return new Order(id, client, ordered, accepted, meals, clientId);
    }
}