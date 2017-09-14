package model.impl.mysql;

import model.dao.OrderDAO;
import model.entities.Meal;
import model.entities.Order;

import java.util.List;

public class MySQLOrderDAO implements OrderDAO {

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order getForId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Order item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Order item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Meal> loadOrderMeals(Order order) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
