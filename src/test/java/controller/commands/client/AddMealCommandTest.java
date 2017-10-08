package controller.commands.client;

import model.entities.Meal;
import model.entities.User;
import model.entities.UserBuilder;
import model.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddMealCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private MenuService service;

    @InjectMocks
    private AddMealCommand command;

    @Test
    public void testMealAddition(){

        User user = new UserBuilder().createUser();

        List<Meal> meals = new ArrayList<>();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter(anyString())).thenReturn("1");
        when(session.getAttribute("currentOrder")).thenReturn(meals);
        when(session.getAttribute("user")).thenReturn(user);

        doNothing().when(service).addMealToList(1,1, meals);

        String path = command.execute(request, response);

        verify(request).getParameter("meal");
        verify(request).getParameter("amount");
        verify(session).getAttribute("currentOrder");
        verify(session).getAttribute("user");
        verify(service).addMealToList(1, 1, meals);
        verify(request).setAttribute("message", "message.added");
        verify(session).setAttribute("currentOrder", meals);

        assertEquals("redirect:search.meals", path);


    }


}