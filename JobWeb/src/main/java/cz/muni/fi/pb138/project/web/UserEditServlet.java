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

/**
 * Created by xtomasch on 6/13/16.
 */
@WebServlet(urlPatterns = {"/user/*"})
public class UserEditServlet extends HttpServlet{
    private final static Logger log = LoggerFactory.getLogger(UserServlet.class);

    private UserManager userManager = new UserManagerImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        showUser(request, response,id);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //support non-ASCII characters in form
        request.setCharacterEncoding("utf-8");
        //action specified by pathInfo
        String action = request.getPathInfo();
        log.debug("POST ... {}",action);
        switch (action) {
            case "/update":
                //getting POST parameters from form
                String name = request.getParameter("userName");
                Long id = Long.parseLong(request.getParameter("id"));
                //form data validity check
                if (name == null || name.isEmpty()) {
                    request.setAttribute("error", "Je nutné vyplnit všechny hodnoty!");
                    log.debug("Form data invalid");
                    showUser(request, response,id);
                    return;
                }
                User u =  userManager.getUser(id);
                u.setName(name);
                userManager.updateUser(u);
                showUser(request, response,id);
                break;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);

        }

    }

    private void showUser(HttpServletRequest request, HttpServletResponse response, Long id) throws ServletException, IOException{
        User u = userManager.getUser(id);
        System.out.println(u.getName());
        request.setAttribute("user", u);
        request.getRequestDispatcher("/EditUser.jsp").forward(request, response);
    }

}
