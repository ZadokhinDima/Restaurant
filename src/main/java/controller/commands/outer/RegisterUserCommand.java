package controller.commands.outer;

import controller.commands.Command;
import controller.util.PasswordSecurityUtil;
import model.entities.*;
import model.service.AccountService;
import model.service.impl.AccountServiceImpl;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegisterUserCommand implements Command {


    //Parameters and Attributes
    private final String NAME_PARAMETER = "name";
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";
    private final String PASSWORD_REPEAT_PARAMETER = "repeatedPass";
    private final String BIRTH_DATE_PARAMETER = "birthDay";
    private final String ERRORS = "registration_errors";
    private final String USER_ATTRIBUTE = "user";


    //Errors
    private final String NAME_ERROR = "error.name";
    private final String EMAIL_ERROR = "errors.email";
    private final String PASSWORD_ERROR = "error.password";
    private final String PASSWORDS_ARE_NOT_EQUAL = "error.val.password";
    private final String WRONG_DATE = "error.date";
    private final String SERVER_ERROR = "errors.not.unique";

    //Regular expressions
    private final String NAME_REGEX = "([A-Z][a-z]{1,13} ?){1,5}";
    private final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*";
    private final String PASSWORD_REGEX = ".{6,}";
    private final String DATE_FORMAT = "yyyy-MM-dd";

    private static final Logger LOGGER = Logger.getLogger(RegisterUserCommand.class);

    private AccountService service = new AccountServiceImpl();

    private final Timestamp MIN_TIMESTAMP = Timestamp.valueOf(LocalDateTime.now().minusYears(18));

    private HttpServletRequest request;
    private String name;
    private String email;
    private String password;
    private String repeatedPassword;
    private Date birthDay;

    private Login login;
    private User user;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        initCommand(request);
        List<String> errors = validate();

        if (!errors.isEmpty()) {
            processInputErrors(errors);
            return REGISTRATION_JSP;
        }

        buildAccount();

        if (service.registerUser(login, user)) {
            processSuccessfulLogin();
            return CLIENT_PAGE_JSP;
        } else {
            processServerError();
            return REGISTRATION_JSP;
        }
    }

    private void initCommand(HttpServletRequest request) {
        this.request = request;
        name = request.getParameter(NAME_PARAMETER);
        email = request.getParameter(EMAIL_PARAMETER);
        password = request.getParameter(PASSWORD_PARAMETER);
        repeatedPassword = request.getParameter(PASSWORD_REPEAT_PARAMETER);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String dateString = request.getParameter(BIRTH_DATE_PARAMETER);
        if (dateString == null) {
            birthDay = null;
        } else {
            birthDay = Date.valueOf(LocalDate.parse(dateString, dtf));
        }
    }

    private void buildAccount(){
        login = new LoginBuilder()
                .setName(email)
                .setPassword(PasswordSecurityUtil.getSecurePassword(password))
                .createLogin();

        user = new UserBuilder()
                .setName(name)
                .setBirthDate(birthDay)
                .setRole(Role.CLIENT.name())
                .createUser();
    }

    private List<String> validate() {
        List<String> errors = new ArrayList<>();

        if (name == null || !name.matches(NAME_REGEX)) {
            errors.add(NAME_ERROR);
        }

        if (email == null || !email.matches(EMAIL_REGEX)) {
            errors.add(EMAIL_ERROR);
        }

        if (password == null || !password.matches(PASSWORD_REGEX)) {
            errors.add(PASSWORD_ERROR);
        }

        if (repeatedPassword == null || !password.equals(repeatedPassword)) {
            errors.add(PASSWORDS_ARE_NOT_EQUAL);
        }

        if (birthDay == null || birthDay.after(MIN_TIMESTAMP)) {
            errors.add(WRONG_DATE);
        }

        return errors;
    }

    private void processInputErrors(List<String> errors) {
        String toLog = "Invalid attempt of registration.";
        for (String message : errors) {
            toLog += "\n" + message;
        }
        LOGGER.info(toLog);
        request.setAttribute(ERRORS, errors);
    }

    private void processServerError(){
        LOGGER.info("Invalid attempt of registration with used email: " + email);
        List<String> errors = new ArrayList<>();
        errors.add(SERVER_ERROR);
        request.setAttribute(ERRORS, errors);
    }

    private void processSuccessfulLogin(){
        LOGGER.info("USER " + user.getId() + "registered");
        request.getSession().setAttribute(USER_ATTRIBUTE, user);
    }
}
