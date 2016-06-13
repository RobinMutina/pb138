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
                Long jobTypeId;
                Long userId;
                LocalDateTime startTime;
                LocalDateTime endTime;
                JobDone jobDone;

                try {
                    jobTypeId = Long.parseLong(request.getParameter("jobTypeId"));
                }catch (Exception e){
                    request.setAttribute("ErrMsg","Neplatne pole typ prace, chyba: " + e.getMessage());
                    show(request,response);
                    return;
                }

                try {
                    userId = Long.parseLong(request.getParameter("userName"));
                }catch (Exception e){
                    request.setAttribute("ErrMsg","Neplatne pole odberatel, chyba:" + e.getMessage());
                    show(request,response);
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                try {
                    startTime = LocalDateTime.parse(request.getParameter("start"), formatter);
                }catch (Exception e){
                    request.setAttribute("ErrMsg","Neplatne pole zaciatok prace, chyba:" + e.getMessage());
                    show(request,response);
                    return;
                }
                try {
                    endTime = LocalDateTime.parse(request.getParameter("end"), formatter);
                }catch (Exception e){
                    request.setAttribute("ErrMsg","Neplatne pole koniec prace, chyba:" + e.getMessage());
                    show(request,response);
                    return;
                }

                jobDone = new JobDone();
                jobDone.setUserId(userId);
                jobDone.setJobTypeId(jobTypeId);
                jobDone.setStrartTime(startTime);
                jobDone.setEndTime(endTime);

                try {
                    new JobDoneManagerImpl().createJobDone(jobDone);
                    request.setAttribute("ScsMsg","Nova praca zadana");
                }catch (Exception e){
                    request.setAttribute("ErrMsg","Nepodarilo sa vytvorit zaznam v DB:" + e.getMessage());
                }
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
