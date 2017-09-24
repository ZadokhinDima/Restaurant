package model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
	public List<T> getAll();
	public Optional<T> getForId(int id);
	public boolean insert(T item);
	public int update(T item);
}	
