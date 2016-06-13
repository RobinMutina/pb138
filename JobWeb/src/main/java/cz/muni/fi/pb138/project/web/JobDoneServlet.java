package cz.muni.fi.pb138.project.web;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Impl.JobDoneManagerImpl;
import cz.muni.fi.pb138.project.Impl.JobTypeManagerImpl;
import cz.muni.fi.pb138.project.Impl.UserManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by xtomasch on 6/13/16.
 */
@WebServlet(urlPatterns = {"/jobs/*", "/jobmanager/*"})
public class JobDoneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        show(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //support non-ASCII characters in form
        request.setCharacterEncoding("utf-8");
        //action specified by pathInfo
        String action = request.getPathInfo();
        switch (action) {
            case "/add":
                Long jobTypeId = Long.parseLong(request.getParameter("jobTypeId"));
                Long userId = Long.parseLong(request.getParameter("userName"));
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime startTime = LocalDateTime.parse(request.getParameter("start"), formatter);
                LocalDateTime endTime = LocalDateTime.parse(request.getParameter("end"), formatter);
                JobDone jobDone = new JobDone();
                jobDone.setUserId(userId);
                jobDone.setJobTypeId(jobTypeId);
                jobDone.setStrartTime(startTime);
                jobDone.setEndTime(endTime);
                new JobDoneManagerImpl().createJobDone(jobDone);
                show(request,response);
                break;
            default:

                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);

        }
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("jobtypes", new JobTypeManagerImpl().getAllJobTypes());
        request.setAttribute("users", new UserManagerImpl().getAllUsers());
        request.getRequestDispatcher("/JobDoneManager.jsp").forward(request, response);
    }
}
