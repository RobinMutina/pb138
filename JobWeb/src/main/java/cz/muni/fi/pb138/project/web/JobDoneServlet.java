package cz.muni.fi.pb138.project.web;

import cz.muni.fi.pb138.project.Impl.JobDoneManagerImpl;
import cz.muni.fi.pb138.project.Impl.JobTypeManagerImpl;
import cz.muni.fi.pb138.project.Impl.UserManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xtomasch on 6/13/16.
 */
@WebServlet(urlPatterns = {"/jobs/*", "/jobmanager/*"})
public class JobDoneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        show(request, response);
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("jobtypes", new JobTypeManagerImpl().getAllJobTypes());
        request.setAttribute("users", new UserManagerImpl().getAllUsers());
        request.getRequestDispatcher("/JobDoneManager.jsp").forward(request, response);
    }
}
