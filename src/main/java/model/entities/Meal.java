package model.entities;

public class Meal {
	private int id;
	private String name;
	private int price;
	private int weight;
	private MealCategory category;
	
	public Meal(int id, String name, int price, int weight, MealCategory category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.category = category;
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
	}
	
}
