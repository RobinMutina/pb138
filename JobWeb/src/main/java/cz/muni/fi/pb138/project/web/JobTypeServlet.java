package cz.muni.fi.pb138.project.web;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Impl.JobTypeManagerImpl;
import cz.muni.fi.pb138.project.Interfaces.JobTypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Marcel on 11. 6. 2016.
 */
@WebServlet(urlPatterns = {"/jobtypes/*","/jobtypemanager/*"})
public class JobTypeServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(JobTypeServlet.class);

    private JobTypeManager jobTypeManager = new JobTypeManagerImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //support non-ASCII characters in form
        request.setCharacterEncoding("utf-8");
        //action specified by pathInfo
        String action = request.getPathInfo();
        log.debug("POST ... {}",action);
        switch (action) {
            case "/add":

                //getting POST parametgetParameterers from form
                String name = request.getParameter("jobName");
                BigDecimal pricePerHour = null;
                try {
                    pricePerHour = new BigDecimal(request.getParameter("jobPrice"));
                }
                catch (Exception e){
                    request.setAttribute("error", "Je nutné vyplnit všetky hodnoty!");
                    log.debug("Form data invalid");
                    showJobTypeList(request, response);
                    return;}
                //form data validity check
                if (name == null || name.isEmpty()) {
                    request.setAttribute("error", "Je nutné vyplnit všetky hodnoty!");
                    log.debug("Form data invalid");
                    showJobTypeList(request, response);
                    return;
                }
                JobType jt =  new JobType();
                jt.setName(name);
                jt.setPricePerHour(pricePerHour);
                jobTypeManager.createJobType(jt);
                showJobTypeList(request, response);
                break;



            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);


        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET ...");
        showJobTypeList(request, response);
    }

    private void showJobTypeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("jobTypes", jobTypeManager.getAllJobTypes());
        request.getRequestDispatcher("/JobTypeManager.jsp").forward(request, response);
    }
}
