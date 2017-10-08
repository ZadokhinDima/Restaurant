package controller.commands.admin;

import model.entities.*;
import model.exeptions.ConcurrentProcessingException;
import model.service.CheckService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AcceptOrderCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private CheckService service;

    @InjectMocks
    private AcceptOrderCommand command;


    @Test
    public void executeNormal() throws Exception {
        Order order = new OrderBuilder().createOrder();
        User user = new UserBuilder().createUser();
        Check check = new CheckBuilder().createCheck();

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("currentOrder")).thenReturn(order);
        when(session.getAttribute("user")).thenReturn(user);
        when(service.acceptOrder(order, user)).thenReturn(check);

        String redirect = command.execute(request, response);

        verify(session).getAttribute("currentOrder");
        verify(session).getAttribute("user");
        verify(service).acceptOrder(order, user);

        assertEquals("redirect:admin.home.page", redirect);
    }

    @Test
    public void executeConcurrent(){
        Order order = new OrderBuilder().createOrder();
        User user = new UserBuilder().createUser();

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("currentOrder")).thenReturn(order);
        when(session.getAttribute("user")).thenReturn(user);
        doThrow(new ConcurrentProcessingException("")).when(service).acceptOrder(order, user);

        String redirect = command.execute(request, response);

        verify(request).setAttribute("message", "error.concurensy.processed");
        verify(session).getAttribute("currentOrder");
        verify(session).getAttribute("user");
        verify(service).acceptOrder(order, user);

        assertEquals("redirect:admin.home.page", redirect);
    }

}