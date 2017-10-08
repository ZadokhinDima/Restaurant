package controller.commands.client;

import controller.commands.Command;
import model.dao.CategoryDAO;
import model.dao.FactoryDAO;
import model.entities.MealCategory;
import model.service.MenuService;
import model.service.impl.MenuServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchMealsCommand implements Command {


    private static final String ATTRIBUTE_MEALS = "meals";
    private static final String ATTRIBUTE_CATEGORIES = "categories";
    private static final String ATTRIBUTE_CURRENT = "currentCategory";
    private static final String PARAMETER_CATEGORY = "category";

    MenuService service = MenuServiceImpl.getInstance();

    private int category;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        saveCommandResults(request);
        return CLIENT_MENU_PAGE;
    }

    private void initCommand(HttpServletRequest request){
        if(request.getParameter(PARAMETER_CATEGORY)!= null) {
            category = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY));
            return;
        }
        if(request.getSession().getAttribute(ATTRIBUTE_CURRENT) != null ){
            category = ((MealCategory)request.getSession().getAttribute(ATTRIBUTE_CURRENT)).getId();
            return;
        }
        category = 1;
    }

    private void saveCommandResults(HttpServletRequest request){
        request.getSession().setAttribute(ATTRIBUTE_MEALS, service.getMealsForCategory(category));
        request.setAttribute(ATTRIBUTE_CATEGORIES, service.getAllCategories());
        request.getSession().setAttribute(ATTRIBUTE_CURRENT, service.getForId(category));
    }
}
