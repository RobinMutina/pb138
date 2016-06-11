package cz.muni.fi.pb138.project.web;

import cz.muni.fi.pb138.project.Impl.UserManagerImpl;
import cz.muni.fi.pb138.project.Interfaces.UserManager;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marcel on 11. 6. 2016.
 */
@WebServlet(urlPatterns = {"/get/*"})
public class UserServlet extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse response;

    private UserManager userManager = new UserManagerImpl();

    private List<String> userError = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("guests", userManager.getAllUsers());
        request.getRequestDispatcher("/UserManager.jsp").forward(request, response);

    }
}
