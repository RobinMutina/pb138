package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.DbException;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DB.DbCoreApi4EmbeddedBaseX;
import cz.muni.fi.pb138.project.Impl.DB.EmbeddedBaseXResultIterator;
import cz.muni.fi.pb138.project.Interfaces.DbCoreApi;
import cz.muni.fi.pb138.project.Validators.JobDoneValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;


/**
 * Created by martin on 26.5.2016.
 */
public class JobDoneDAO {
    private static final DbCoreApi dbApi = DbCoreApi4EmbeddedBaseX.getInstance();

    public void createJobDone(JobDone jobDone) throws ServiceFailureException {
        try{
            JobDoneValidator.canCreate(jobDone);

            Long id = getNewUniqueID();

            UserDAO userDao = new UserDAO();
            JobTypeDAO jobTypeDAO = new JobTypeDAO();

            if (userDao.getUser(jobDone.getUserId()) == null) {
                throw new ServiceFailureException("Database doesn'contain user.");
            }

            if (jobTypeDAO.getJobType(jobDone.getJobTypeId()) == null) {
                throw new ServiceFailureException("Database doesn'contain jobType.");
            }

            jobDone.setId(id);

            System.out.println(XMLTransformer.nodeToString(createJobDoneElement(jobDone)));

            String query = "insert node " +
                    XMLTransformer.nodeToString(createJobDoneElement(jobDone)) +
                    " into /JobsDone";
            dbApi.execXquery(query);

        }catch (DbException | ValidationException | IllegalAccessException | ParserConfigurationException | TransformerException e) {
            throw new ServiceFailureException("Error creating JobDone.", e);
        }
    }

    Long getNewUniqueID(){
        Random randomno = new Random();
        Long id =  randomno.nextLong();
        while (id < new Long(0) || !isUniqueID(id)){
            id = randomno.nextLong();
        }
        return  id;
    }

    /**
     * Utility funct to check if id is unique
     * @param id
     * @return true if the id unique, else false
     */
    public static Boolean isUniqueID(Long id) {
        String result;
        try {
            result = dbApi.execXquery(
                    "let $job := //JobDone[@id='" + id.toString() + "']\n" +
                            "return fn:empty($job)"
            );
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
        return result.equals("true");
    }


    public void updateJobDone(JobDone jobDone) throws ServiceFailureException {
        try{
            JobDoneValidator.canUpdate(jobDone);

            JobDone originalJobDone = this.getJobDoneById(jobDone.getId());

            if (originalJobDone == null){
                throw new IllegalArgumentException("DB doesn't contain jobDone");
            }

            if (originalJobDone.getUserId() != jobDone.getUserId()){
                throw new IllegalAccessException("Cannot update userId");
            }

            if (originalJobDone.getJobTypeId() != jobDone.getJobTypeId()){
                throw new IllegalAccessException("Cannot update jobTypeId");
            }

            String query = "update replace /JobsDone/JobDone[@id=\"" + jobDone.getId() + "\"] " + "with "+ XMLTransformer.nodeToString(createJobDoneElement(jobDone));
            dbApi.execXquery(query);
        }catch (DbException | ParserConfigurationException | ValidationException |
                TransformerException | IllegalAccessException e) {
            throw new ServiceFailureException("Error updating JobDone.", e);
        }
    }

    public void deleteJobDone(long id) {
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getJobDoneById(id) == null){
            throw new IllegalArgumentException("DB doesn't contain jobDone");
        }

        try {
            String query = "update delete doc('JobDone.xml')/JobsDone/JobDone[@id = \"" + id + "\"]";
            dbApi.execXquery(query);
        } catch (DbException e) {
            throw new ServiceFailureException("Error deleting JobDone.", e);
        }
    }

    public JobDone getJobDoneById(long id){
        try {
            if (id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            String xquery = "for $jobDone in /JobsDone/JobDone let $jobDoneId := $jobDone/@id where $jobDoneId = \""+ id + "\" return $jobDone";
            String result = dbApi.execXquery(xquery);

            if (result.isEmpty()){
                return null;
            }

            return this.getJobDoneFromDocument(ResultDocument.getDocument(result));

        } catch (DbException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }
    }

    public List<JobDone> getAllJobDone(){

        String xquery = "for $jobDone in /JobsDone/JobDone return $jobDone";

        return this.getJobsDone(xquery);
    }

    public List<JobDone> getAllJobDoneByUserId(long id){

        String xquery = "for $jobDone in /JobsDone/JobDone let $userId := $jobDone/userId where $userId = \""+ id + "\" return $jobDone";

        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        return this.getJobsDone(xquery);
    }

    private List<JobDone> getJobsDone(String query){
        List<JobDone> jobDoneList = new ArrayList<JobDone>();

        if (query == null || query.isEmpty()){
            throw new IllegalArgumentException("query is null or empty");
        }

        try (EmbeddedBaseXResultIterator it = (EmbeddedBaseXResultIterator) dbApi.execXqueryIterated(
                query))
        {

            it.forEachRemaining(new Consumer() {
                @Override
                public void accept(Object o) {
                    try {
                        jobDoneList.add(getJobDoneFromDocument(ResultDocument.getDocument(o.toString())));
                    } catch (ParserConfigurationException | SAXException |
                            IOException | IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            throw new ServiceFailureException("Error getting all jobsDone.", e);
        }

        return jobDoneList;
    }

    public List<JobDone> getAllJobDoneByUserAtTime(long id, LocalDateTime startTime, LocalDateTime endTime){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (startTime == null){
            throw new IllegalArgumentException("startTime is null");
        }

        if (endTime == null){
            throw new IllegalArgumentException("endTime is null");
        }

        if (startTime.equals(endTime) ||
                startTime.isAfter(endTime)){
            throw new IllegalArgumentException("startTime and endTime are invalid");
        }

        List<JobDone> jobDoneList = new ArrayList<JobDone>();

        for (JobDone jobDone : getAllJobDoneByUserId(id)) {
            if (startTime.isBefore(jobDone.getStartTime()) && endTime.isAfter(jobDone.getEndTime())){
                jobDoneList.add(jobDone);
            }
        }

        return jobDoneList;
    }

    public BigDecimal getUserTotalSalary(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }



        throw new UnsupportedOperationException();
    }

    public BigDecimal getUserSalaryAtTime(Long id, LocalDateTime startTime, LocalDateTime endTime){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (startTime == null){
            throw new IllegalArgumentException("startTime is null");
        }

        if (endTime == null){
            throw new IllegalArgumentException("endTime is null");
        }
        //TODO
        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    private JobDone getJobDoneFromDocument(Document document) throws IllegalAccessException {
        if (document == null){
            throw new IllegalArgumentException("document is null");
        }

        JobDone jobDone = new JobDone();

        Element root = document.getDocumentElement();

        jobDone.setId(Long.parseLong(root.getAttribute("id")));

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node node = root.getChildNodes().item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                switch (node.getNodeName()){
                    case "userId":
                        jobDone.setUserId(Long.parseLong(node.getTextContent()));
                        break;
                    case "jobTypeId":
                        jobDone.setJobTypeId(Long.parseLong(node.getTextContent()));
                        break;
                    case "startTime":
                        jobDone.setStrartTime(LocalDateTime.parse(node.getTextContent()));
                        break;
                    case "endTime":
                        jobDone.setEndTime(LocalDateTime.parse(node.getTextContent()));
                        break;
                }
            }
        }
        return jobDone;
    }

    private Element createJobDoneElement(JobDone jobDone) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.newDocument();

        Element jobDoneElement = document.createElement("JobDone");
        jobDoneElement.setAttribute("id", Long.toString(jobDone.getId()));

        Element userIdElement = document.createElement("userId");
        userIdElement.setTextContent(Long.toString(jobDone.getUserId()));

        Element jobTypeIdElement = document.createElement("jobTypeId");
        jobTypeIdElement.setTextContent(Long.toString(jobDone.getJobTypeId()));

        Element startTimeIdElement = document.createElement("startTime");
        startTimeIdElement.setTextContent(jobDone.getStartTime().toString());

        Element endTimeIdElement = document.createElement("endTime");
        endTimeIdElement.setTextContent(jobDone.getEndTime().toString());

        jobDoneElement.appendChild(userIdElement);
        jobDoneElement.appendChild(jobTypeIdElement);
        jobDoneElement.appendChild(startTimeIdElement);
        jobDoneElement.appendChild(endTimeIdElement);
        document.appendChild(jobDoneElement);

        return jobDoneElement;
    }
}
