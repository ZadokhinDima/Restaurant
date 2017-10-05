package controller.commands.outer;

import controller.commands.Command;
import controller.commands.admin.AdminHomeCommand;
import controller.commands.client.ClientHomeCommand;
import controller.util.BundleUtil;
import controller.util.PasswordSecurityUtil;
import model.entities.Role;
import model.entities.User;
import model.service.AccountService;
import model.service.impl.AccountServiceImpl;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;


public class LoginCommand implements Command {

    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private AccountService service = new AccountServiceImpl();

    private String email;
    private String password;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        initCommand(request);

        Optional<User> user = service.logIn(email, password);
        if(user.isPresent()){
            LOGGER.info("User " + user.get().getId() + " logged in.");
            request.getSession().setAttribute(USER_ATTRIBUTE, user.get());
            if(user.get().getRole().equals(Role.ADMIN)){
                return new AdminHomeCommand().execute(request, response);
            }
            else{
                return new ClientHomeCommand().execute(request, response);
            }
        }
        else{
            LOGGER.info("Invalid attempt to log in.");
            request.setAttribute(ERROR_ATTRIBUTE,
                    BundleUtil.getString("errors.login",(Locale)request.getSession().getAttribute("locale")));
            return INDEX_JSP;
        }
    }

    private void initCommand(HttpServletRequest request){
        email = request.getParameter(EMAIL_PARAMETER);
        password = PasswordSecurityUtil.getSecurePassword(request.getParameter(PASSWORD_PARAMETER));
    }
}
