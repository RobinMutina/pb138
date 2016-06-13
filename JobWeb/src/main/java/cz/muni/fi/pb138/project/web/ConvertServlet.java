package cz.muni.fi.pb138.project.web;

import cz.muni.fi.pb138.project.Converter.StartConverter;

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

/**
 * Created by Vladislav Malynych on 13-06-2016.
 */
@WebServlet(urlPatterns = {"convert/*"})
public class ConvertServlet extends HttpServlet{

        private final static Logger log = LoggerFactory.getLogger(ConvertServlet.class);

        private StartConverter converter = new StartConverter();

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            //support non-ASCII characters in form
            request.setCharacterEncoding("utf-8");
            //action specified by pathInfo
            String action = request.getPathInfo();
            log.debug("POST ... {}", action);
            switch (action) {
                case "/create":
                    LocalDateTime startTime;
                    LocalDateTime endTime;
                    try {
                        String from = request.getParameter("from");
                        String to = request.getParameter("to");
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        startTime = LocalDateTime.parse(from, formatter);
                        System.out.println(startTime.toString());
                        endTime = LocalDateTime.parse(to, formatter);
                        System.out.println(endTime.toString());
                    }catch(Exception ex){
                        log.error("Date is not entered ");
                        showCoverter(request, response,"/CountBill.jsp" );
                        return;
                    }
                    try {
                        converter.convertAll(startTime, endTime);
                    }catch(Exception ex){
                        log.error("Could not convert data");
                        showCoverter(request, response,"/CountBill.jsp" );
                        return;
                    }
                    System.out.println("Coverting finished");
                    showCoverter(request,response, "/CountBillFin.jsp");
                    break;


                default:
                    log.error("Unknown action " + action);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
            }

        }
        private void showCoverter(HttpServletRequest request, HttpServletResponse response,String page) throws ServletException, IOException{
            request.getRequestDispatcher(page).forward(request, response);
        }
}
