package controller.commands.client;

import model.entities.User;
import model.entities.UserBuilder;
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
public class PayForCheckCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private CheckService service;

    @InjectMocks
    private PayForCheckCommand command;

    @Test
    public void successfulPaymentTest(){
        User user = new UserBuilder().createUser();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("check.id")).thenReturn("1");
        doNothing().when(service).payCheck(1);

        String path = command.execute(request, response);

        verify(session).getAttribute("user");
        verify(request).getSession();
        verify(request).getParameter("check.id");
        verify(service).payCheck(1);

        assertEquals("redirect:client.checks.page", path);
    }

    @Test
    public void concurrentPaymentTest(){
        User user = new UserBuilder().createUser();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("check.id")).thenReturn("1");
        doThrow(new ConcurrentProcessingException("")).when(service).payCheck(1);

        String path = command.execute(request, response);

        verify(session).getAttribute("user");
        verify(request).getSession();
        verify(request).getParameter("check.id");
        verify(service).payCheck(1);
        verify(request).setAttribute("message", "error.concurensy.check");

        assertEquals("redirect:client.checks.page", path);
    }

}