package model.entities;

import model.dao.CategoryDAO;
import model.dao.FactoryDAO;

public class Meal {
	private int id;
	private String name;
	private int price;
	private int weight;
	private MealCategory category;
	private String image;
	private int minutes;
	
	public Meal(int id, String name, int price, int weight, int category, String image, int minutes) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.weight = weight;
		CategoryDAO dao = FactoryDAO.getInstance().getCategoryDAO();
		this.category = dao.getForId(category).get();
		this.image = image;
		this.minutes = minutes;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
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


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
