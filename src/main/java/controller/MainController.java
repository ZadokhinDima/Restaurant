package controller;

import controller.commands.Command;
import controller.commands.CommandFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "main", value = "/restaurant/*")
public class MainController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String query = (String) request.getAttribute("query");
        Command command = CommandFactory.create(query);
        String page = command.execute(request, response);
        if (page.startsWith("redirect:")) {
            request.setAttribute("query", page.replace("redirect:", ""));
            processQuery(request, response);
        } else {
            getServletContext().getRequestDispatcher(page).forward(request, response);
        }
    }

}
