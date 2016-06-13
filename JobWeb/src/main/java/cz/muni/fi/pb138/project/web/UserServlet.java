package cz.muni.fi.pb138.project.web;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Impl.UserManagerImpl;
import cz.muni.fi.pb138.project.Interfaces.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Marcel on 11. 6. 2016.
 */
@WebServlet(urlPatterns = {"/users/*","/usermanager/*"})
public class UserServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(UserServlet.class);

    private UserManager userManager = new UserManagerImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //support non-ASCII characters in form
        request.setCharacterEncoding("utf-8");
        //action specified by pathInfo
        String action = request.getPathInfo();
        log.debug("POST ... {}",action);
        switch (action) {
            case "/add":
                //getting POST parameters from form
                String name = request.getParameter("userName");
                //form data validity check
                if (name == null || name.isEmpty()) {
                    request.setAttribute("error", "Je nutné vyplnit všechny hodnoty!");
                    log.debug("Form data invalid");
                    showUserList(request, response);
                    return;
                }
                User u =  new User();
                u.setName(name);
                userManager.createUser(u);
                showUserList(request, response);
                break;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET ...");
        showUserList(request, response);
    }

    private void showUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("users", userManager.getAllUsers());
        request.getRequestDispatcher("/UserManager.jsp").forward(request, response);
    }
}
