package model.dao;

import java.util.List;

public interface GenericDAO<T> {
	public List<T> getAll();
	public T getForId(int id);
	public boolean insert(T item);
	public boolean update(T item);
}	
