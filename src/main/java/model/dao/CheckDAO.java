package model.dao;

import model.entities.Check;
import model.entities.User;

import java.util.List;

public interface CheckDAO extends GenericDAO<Check> {

    List<Check> getAllChecksForUser(User user);

}
