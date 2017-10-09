package model.service.impl;

import controller.Config;
import model.dao.ConnectionDAO;
import model.dao.FactoryDAO;
import model.dao.MealsDAO;
import model.entities.Meal;
import model.entities.MealBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest {

    @Mock
    FactoryDAO factory;

    @Mock
    ConnectionDAO connectionDAO;

    @Mock
    MealsDAO mealsDAO;

    @InjectMocks
    MenuServiceImpl service;


    @Test
    public void getMealWithAmount() throws Exception {
        Meal meal = new MealBuilder()
                .setName("test")
                .setCategoryId(1)
                .createMeal();
        Meal expected = new MealBuilder()
                .setName("test")
                .setCategoryId(1)
                .setAmount(5)
                .createMeal();

        when(factory.getConnectionDAO()).thenReturn(connectionDAO);
        when(factory.getMealsDAO(any())).thenReturn(mealsDAO);
        when(mealsDAO.getForId(anyInt())).thenReturn(Optional.of(meal));

        service.getMealWithAmount(1, 5);

        verify(factory).getConnectionDAO();
        verify(factory).getMealsDAO(any());
        verify(mealsDAO).getForId(1);


        assertEquals(expected, meal);

    }

    @Test
    public void addMealToList() throws Exception {
    }

    @Test
    public void removeMealFromList() throws Exception {
    }

    @Test
    public void getMealsForCategory() throws Exception {
    }

    @Test
    public void getAllCategories() throws Exception {
    }

    @Test
    public void getForId() throws Exception {
    }

}