package controller.commands.admin;

import model.entities.Order;
import model.entities.OrderBuilder;
import model.entities.User;
import model.entities.UserBuilder;
import model.exeptions.ConcurrentProcessingException;
import model.service.CheckService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RunWith(MockitoJUnitRunner.class)
public class AdminDeclineOrderCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private CheckService service;

    @InjectMocks
    private AdminDeclineOrderCommand command;


    @Test
    public void executeTest() throws Exception {

        Order order = new OrderBuilder().createOrder();
        User user = new UserBuilder().createUser();

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("currentOrder")).thenReturn(order);
        when(session.getAttribute("user")).thenReturn(user);

        doNothing().when(service).declineOrder(order.getId());

        String redirect = command.execute(request, response);

        verify(session).getAttribute("currentOrder");
        verify(session).getAttribute("user");
        verify(service).declineOrder(order.getId());

        assertEquals("redirect:admin.home.page", redirect);
    }

    @Test
    public void concurrentExceptionExecute() {
        Order order = new OrderBuilder().createOrder();
        User user = new UserBuilder().createUser();

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("currentOrder")).thenReturn(order);
        when(session.getAttribute("user")).thenReturn(user);

        doThrow(new ConcurrentProcessingException("")).when(service).declineOrder(order.getId());

        String redirect = command.execute(request, response);

        verify(request).setAttribute("message", "error.concurensy.processed");
        verify(session).getAttribute("currentOrder");
        verify(session).getAttribute("user");
        verify(service).declineOrder(order.getId());

        assertEquals("redirect:admin.home.page", redirect);

    }

}