package controller.commands.outer;

import controller.commands.Command;
import controller.commands.CommandFactory;
import controller.commands.admin.AdminHomeCommand;
import controller.commands.client.ClientHomeCommand;
import controller.util.PasswordSecurityUtil;
import model.entities.Role;
import model.entities.User;
import model.service.AccountService;
import model.service.impl.AccountServiceImpl;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


public class LoginCommand implements Command {


    private static final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*";

    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private AccountService service = AccountServiceImpl.getInstance();

    private String email;
    private String password;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        initCommand(request);

        if(!validateEmail()){
            request.setAttribute(ERROR_ATTRIBUTE, "errors.email");
            return INDEX_JSP;
        }

        Optional<User> user = service.logIn(email, password);
        if(user.isPresent()){
            LOGGER.info("User " + user.get().getId() + " logged in.");
            request.getSession().setAttribute(USER_ATTRIBUTE, user.get());
            if(user.get().getRole().equals(Role.ADMIN)){
                return "redirect:client.home.page";
            }
            else{
                return "redirect:admin.home.page";
            }
        }
        else{
            LOGGER.info("Invalid attempt to log in.");
            request.setAttribute(ERROR_ATTRIBUTE, "errors.login");
            return INDEX_JSP;
        }
    }

    private void initCommand(HttpServletRequest request){
        email = request.getParameter(EMAIL_PARAMETER);
        password = PasswordSecurityUtil.getSecurePassword(request.getParameter(PASSWORD_PARAMETER));
    }

    private boolean validateEmail(){
        return email.matches(EMAIL_REGEX);
    }

}
