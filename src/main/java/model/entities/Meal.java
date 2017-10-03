package model.entities;

public class Meal {
	private int id;
	private String name;
	private int price;
	private int weight;
	private MealCategory category;
	private int categoryId;
	private int amount;



	public Meal(int id, String name, int price, int weight, MealCategory category, int categoryId, int amount) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.category = category;
		this.categoryId = categoryId;
		this.amount = amount;
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
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public MealCategory getCategory() {
		return category;
	}
	
	public void setCategory(MealCategory category) {
		this.category = category;
		this.categoryId = category.getId();
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        if (id != meal.id) return false;
        if (price != meal.price) return false;
        if (weight != meal.weight) return false;
        return name.equals(meal.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + price;
        result = 31 * result + weight;
        return result;
    }

	public int getCategoryId() {
		return categoryId;
	}
}
