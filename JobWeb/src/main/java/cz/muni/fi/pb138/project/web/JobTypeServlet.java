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
@WebServlet(urlPatterns = {"/jobtypes/*","/jobtypemanager/*", "jobdone/*"})
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
                    request.setAttribute("ErrMsg","Chyba pri spracovaní ceny za hodinu: " +e.getMessage() );
                    log.debug("Form data invalid");
                    showJobTypeList(request, response);
                    return;}
                //form data validity check
                if (name == null || name.isEmpty()) {
                    request.setAttribute("ErrMsg", "Neplatne meno");
                    log.debug("Form data invalid");
                    showJobTypeList(request, response);
                    return;
                }
                JobType jt =  new JobType();
                jt.setName(name);
                jt.setPricePerHour(pricePerHour);
                try {
                    jobTypeManager.createJobType(jt);
                    request.setAttribute("SucsMsg","Novy typ prace bol vytvoreny");
                }catch (Exception e){
                    request.setAttribute("ErrMsg","Nepodarilo vytvorit novy typ prace, chyba: " + e.getMessage());
                    showJobTypeList(request,response);
                    return;
                }
                showJobTypeList(request, response);
                break;

            case "/update":
                //if u change the value on button you have to update this ... sry
                Long jobTypeId = Long.parseLong(request.getParameter("jobtypeid"));
                if (request.getParameter("formButton").equals("Delete")){
                    System.out.println("delete");
                    try {
                        jobTypeManager.deleteJobType(jobTypeId);
                    }catch (Exception e){
                        String emsg;
                        if(e.getCause()!=null){
                            emsg = e.getMessage() + "; "+ e.getCause().getMessage();
                        }else{
                            emsg =e.getMessage();
                        }
                        request.setAttribute("ErrMsg","Nepodarilo sa zmazat typ prace, chyba: " + emsg);
                        showJobTypeList(request,response);
                        return;
                    }
                    showJobTypeList(request,response);
                }else{
                    System.out.println("update");
                    String jobTypeName = request.getParameter("jobtypename");
                    if (jobTypeName == null ||jobTypeName.isEmpty()){
                        request.setAttribute("ErrMsg", "Neplatne meno");
                        showJobTypeList(request, response);
                        return;
                    }
                    System.out.println("update - jobtypename " + jobTypeName);
                    BigDecimal jobTypePPH = null;
                    try {
                        jobTypePPH = new BigDecimal(request.getParameter("jobtypepph"));
                    }
                    catch (Exception e){
                        String emsg;
                        if(e.getCause()!=null) {
                            emsg = e.getMessage() + "; " + e.getCause().getMessage();
                        }else{
                            emsg = e.getMessage();
                        }
                        request.setAttribute("ErrMsg", "Chyba pri spracovaní ceny za hodinu: " + emsg );
                        showJobTypeList(request, response);
                        return;
                    }
                    System.out.println("update - pph " + jobTypePPH);

                    JobType jobType =  new JobType();
                    jobType.setName(jobTypeName);
                    jobType.setPricePerHour(jobTypePPH);
                    try {
                        jobType.setId(jobTypeId);
                    }catch (Exception e){
                        //should never happen
                        throw new RuntimeException(e);
                    }
                    try{
                        jobTypeManager.updateJobType(jobType);
                        request.setAttribute("ScsMsg","Typ prace bol prepisany");
                    }catch (Exception e){
                        String emsg;
                        if(e.getCause()!=null) {
                            emsg = e.getMessage() + "; " + e.getCause().getMessage();
                        }else{
                            emsg =e.getMessage();
                        }
                        request.setAttribute("ErrMsg","Nepodarilo sa prepisat typ prace, chyba: " + emsg);
                        showJobTypeList(request,response);
                        return;
                    }
                    System.out.println("update - complete");
                    showJobTypeList(request,response);
                }

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
