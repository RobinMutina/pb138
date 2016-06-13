package cz.muni.fi.pb138.project.web;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Impl.JobDoneManagerImpl;
import cz.muni.fi.pb138.project.Impl.JobTypeManagerImpl;
import cz.muni.fi.pb138.project.Impl.UserManagerImpl;
import cz.muni.fi.pb138.project.Interfaces.JobTypeManager;
import cz.muni.fi.pb138.project.Interfaces.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Long id = Long.parseLong(request.getParameter("id"));
        switch (action) {
            case "/update":
                //getting POST parameters from form
                String name = request.getParameter("userName");

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
            case "/delete":
                userManager.deleteUser(id);
                response.sendRedirect("/users");
                break;
            case "/addjob":
                System.out.println("/addjob");
                Long jobTypeId = Long.parseLong(request.getParameter("jobTypeId"));

                System.out.println(jobTypeId);
                String from = request.getParameter("from");
                String to = request.getParameter("to");
                JobDone jobDone= new JobDone();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime startTime = LocalDateTime.parse(from, formatter);
                System.out.println(startTime.toString());
                LocalDateTime endTime = LocalDateTime.parse(to, formatter);
                System.out.println(endTime.toString());
                jobDone.setStrartTime(startTime);
                jobDone.setEndTime(endTime);
                jobDone.setJobTypeId(jobTypeId);
                jobDone.setUserId(id);
                new JobDoneManagerImpl().createJobDone(jobDone);
                showUser(request, response,id);
                break;
            case "/deletejob":
                Long jobId = Long.parseLong(request.getParameter("jobid"));
                new JobDoneManagerImpl().deleteJobDone(jobId);
                showUser(request, response, id);
                break;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);

        }

    }

    private void showUser(HttpServletRequest request, HttpServletResponse response, Long id) throws ServletException, IOException{
        User u = userManager.getUser(id);
        JobTypeManager jobTypeManager = new JobTypeManagerImpl();
        System.out.println(u.getName());
        List<JobDone> jobdone = new JobDoneManagerImpl().getAllJobDoneByUserId(u.getId());
        Map<JobDone,JobType> jobtotype = new HashMap<>();
        for (JobDone j: jobdone) {
            jobtotype.put(j,jobTypeManager.getJobType(j.getJobTypeId()));
        }
        request.setAttribute("user", u);
        request.setAttribute("jobs", jobtotype);
        request.setAttribute("jobtypes", jobTypeManager.getAllJobTypes());
        request.getRequestDispatcher("/EditUser.jsp").forward(request, response);
    }

}
