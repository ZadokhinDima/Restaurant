package controller.commands.outer;

import controller.util.PasswordSecurityUtil;
import model.entities.*;
import model.exeptions.EmailExistsException;
import model.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private AccountService service;

    @InjectMocks
    private RegisterUserCommand command;

    @Test
    public void successfulRegistrationTest() throws EmailExistsException {

        Login login = new LoginBuilder()
                .setName("test@test.com")
                .setPassword(PasswordSecurityUtil.getSecurePassword("testtesttest"))
                .createLogin();

        User user = new UserBuilder()
                .setName("Test Testing")
                .setBirthDate(Date.valueOf(LocalDate.parse("1970-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .setRole(Role.CLIENT.name())
                .createUser();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Test Testing");
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("testtesttest");
        when(request.getParameter("repeatedPass")).thenReturn("testtesttest");
        when(request.getParameter("birthDay")).thenReturn("1970-01-01");
        doNothing().when(service).registerUser(eq(login), eq(user));

        String path = command.execute(request, response);

        verify(request).getParameter("name");
        verify(request).getParameter("email");
        verify(request).getParameter("password");
        verify(request).getParameter("repeatedPass");
        verify(request).getParameter("birthDay");
        verify(service).registerUser(eq(login), eq(user));
        verify(session).setAttribute(eq("user"), eq(user));

        assertEquals("/WEB-INF/view/client/home_page.jsp", path);

    }


    @Test
    public void wrongDateRegistrationTest(){
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Test Testing");
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("testtesttest");
        when(request.getParameter("repeatedPass")).thenReturn("testtesttest");
        when(request.getParameter("birthDay")).thenReturn("2009-01-01");

        String path = command.execute(request, response);

        verify(request).getParameter("name");
        verify(request).getParameter("email");
        verify(request).getParameter("password");
        verify(request).getParameter("repeatedPass");
        verify(request).getParameter("birthDay");
        verify(request).setAttribute(eq("registration_errors"), anyList());

        assertEquals("/WEB-INF/view/general/registration.jsp", path);
    }

    @Test
    public void wrongEmailRegistrationTest(){
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Test Testing");
        when(request.getParameter("email")).thenReturn("testtest.com");
        when(request.getParameter("password")).thenReturn("testtesttest");
        when(request.getParameter("repeatedPass")).thenReturn("testtesttest");
        when(request.getParameter("birthDay")).thenReturn("1979-01-01");

        String path = command.execute(request, response);

        verify(request).getParameter("name");
        verify(request).getParameter("email");
        verify(request).getParameter("password");
        verify(request).getParameter("repeatedPass");
        verify(request).getParameter("birthDay");
        verify(request).setAttribute(eq("registration_errors"), anyList());

        assertEquals("/WEB-INF/view/general/registration.jsp", path);
    }

    @Test
    public void wrongPasswordRegistrationTest(){
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Test Testing");
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("repeatedPass")).thenReturn("test");
        when(request.getParameter("birthDay")).thenReturn("1979-01-01");

        String path = command.execute(request, response);

        verify(request).getParameter("name");
        verify(request).getParameter("email");
        verify(request).getParameter("password");
        verify(request).getParameter("repeatedPass");
        verify(request).getParameter("birthDay");
        verify(request).setAttribute(eq("registration_errors"), anyList());

        assertEquals("/WEB-INF/view/general/registration.jsp", path);
    }

    @Test
    public void notEqualsPasswordsRegistrationTest(){
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Test Testing");
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("testtesttest");
        when(request.getParameter("repeatedPass")).thenReturn("testtesttesttest");
        when(request.getParameter("birthDay")).thenReturn("1979-01-01");

        String path = command.execute(request, response);

        verify(request).getParameter("name");
        verify(request).getParameter("email");
        verify(request).getParameter("password");
        verify(request).getParameter("repeatedPass");
        verify(request).getParameter("birthDay");
        verify(request).setAttribute(eq("registration_errors"), anyList());

        assertEquals("/WEB-INF/view/general/registration.jsp", path);
    }

    @Test
    public void ExistingEmailRegistrationTest() throws EmailExistsException{
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Test Testing");
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("testtesttest");
        when(request.getParameter("repeatedPass")).thenReturn("testtesttest");
        when(request.getParameter("birthDay")).thenReturn("1979-01-01");
        doThrow(new EmailExistsException()).when(service).registerUser(any(), any());

        String path = command.execute(request, response);

        verify(request).getParameter("name");
        verify(request).getParameter("email");
        verify(request).getParameter("password");
        verify(request).getParameter("repeatedPass");
        verify(request).getParameter("birthDay");
        verify(request).setAttribute(eq("registration_errors"), anyList());

        assertEquals("/WEB-INF/view/general/registration.jsp", path);
    }
}