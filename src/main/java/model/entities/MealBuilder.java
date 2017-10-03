package model.entities;

public class MealBuilder {
    private int id;
    private String name;
    private int price;
    private int weight;
    private MealCategory category;
    private int amount;
    private int categoryId;

    public MealBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public MealBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MealBuilder setPrice(int price) {
        this.price = price;
        return this;
    }

    public MealBuilder setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public MealBuilder setCategory(MealCategory category) {
        this.category = category;
        return this;
    }

    public MealBuilder setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public MealBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public Meal createMeal() {
        return new Meal(id, name, price, weight, category, categoryId, amount);
    }
}