package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DataAccess.DB.DbConnection;
import cz.muni.fi.pb138.project.Impl.DataAccess.DB.XMLTransformer;
import cz.muni.fi.pb138.project.Validators.JobDoneValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


/**
 * DAO operations for JobDone
 * @author Martin Sevcik
 */
public class JobDoneDAO {

    /**
     * representing URI
     */
    public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    /**
     * representing name of file
     */
    private static final String fileName = "JobDone.xml";

    /**
     * representing collection
     */
    private static final String collection = "db/PB138";

    /**
     * representing db driver
     */
    private static String driver = "org.exist.xmldb.DatabaseImpl";

    /**
     * representing db service
     */
    private XQueryService service;

    public static void main(String[] args) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        LocalDateTime x = LocalDateTime.MAX;
        String date = x.toLocalDate().toString();
        String time = x.toLocalTime().toString();

        JobDoneDAO jobDoneDAO = new JobDoneDAO();
        List<JobDone> jb = jobDoneDAO.getAllJobDone();
        System.out.println(jb);
    }//TODO javadoc

    /**
     * non parametric constructor creates new db service
     * @throws ServiceFailureException if error occurs
     * @throws IllegalArgumentException if service is not created
     */
    public JobDoneDAO() throws ServiceFailureException {
        this.service = DbConnection.getConnection(this.driver, this.URI, this.collection, this.fileName);

        if (service == null){
            throw new IllegalArgumentException("service is null");
        }
    }

    /**
     * provides inserting jobDone into db
     * @param jobDone inserted into db
     * @throws ServiceFailureException if error occurs
     */
    public void createJobDone(JobDone jobDone) throws ServiceFailureException {
        try {
            JobDoneValidator.canCreate(jobDone);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getJobDoneById(jobDone.getId()) != null){
            throw new IllegalArgumentException("DB does contain jobDone");
        }

        long maxId = -1;
        for (JobDone done : getAllJobDone()) {
            if (done.getId() > maxId) {
                maxId = done.getId();
            }
        }

        UserDAO userDao = new UserDAO();
        JobTypeDAO jobTypeDAO = new JobTypeDAO();

        try{
            if (userDao.getUser(jobDone.getUserId()) == null) {
                throw new ServiceFailureException("Database doesn'contain user.");
            }

            if (jobTypeDAO.getJobType(jobDone.getJobTypeId()) == null) {
                throw new ServiceFailureException("Database doesn'contain jobType.");
            }

            maxId++;
            jobDone.setId(maxId);

            String query = "update insert " +
                            XMLTransformer.nodeToString(createJobDoneElement(jobDone)) +
                            " into /JobsDone";
            service.query(query);
        }catch (XMLDBException | IllegalAccessException | ParserConfigurationException | TransformerException e) {
            throw new ServiceFailureException("Error creating JobDone.", e);
        }
    }

    /**
     * provides update of existing jobDone
     * @param jobDone updated jobDone
     * @throws ServiceFailureException if error occurs
     */
    public void updateJobDone(JobDone jobDone) throws ServiceFailureException {
        try {
            JobDoneValidator.canUpdate(jobDone);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        try{
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
            service.query(query);
        }catch (XMLDBException | ParserConfigurationException | TransformerException | IllegalAccessException e) {
            throw new ServiceFailureException("Error updating JobDone.", e);
        }
    }

    /**
     * provides delete of existing jobDone
     * @param id id of deleted jobDone
     * @throws ServiceFailureException if error occurs
     */
    public void deleteJobDone(long id) {
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getJobDoneById(id) == null){
            throw new IllegalArgumentException("DB doesn't contain jobDone");
        }

        try {
            String query = "update delete doc('JobDone.xml')/JobsDone/JobDone[@id = \"" + id + "\"]";
            service.query(query);
        } catch (XMLDBException e) {
            throw new ServiceFailureException("Error deleting JobDone.", e);
        }
    }

    /**
     * getter for jobDone from db
     * @param id id of required jobDone
     * @return User
     * @throws IllegalArgumentException if id is less than 0
     * @throws ServiceFailureException if error occurs
     */
    public JobDone getJobDoneById(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        try {
            String xquery = "for $jobDone in /JobsDone/JobDone let $jobDoneId := $jobDone/@id where $jobDoneId = \""+ id + "\" return $jobDone";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){
                return this.getJobDoneFromDocument(XMLTransformer.stringToDocument(
                        iterator.nextResource().getContent().toString()));
            }

        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }
        return null;
    }

    /**
     * getter for all jobsDone
     * @return list of all jobsDone from db
     * @throws ServiceFailureException if error occurs
     */
    public List<JobDone> getAllJobDone(){

        List<JobDone> jobDoneList = new ArrayList<JobDone>();

        try {
            String xquery = "for $jobDone in /JobsDone/JobDone return $jobDone";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){

                jobDoneList.add(this.getJobDoneFromDocument(
                        XMLTransformer.stringToDocument(iterator.nextResource().getContent().toString())));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalAccessException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }

        return jobDoneList;
    }

    /**
     * getter for all jobsDone for specified user
     * @param id id of jobDone
     * @return list of jobsDone from db
     * @throws ServiceFailureException if error occurs
     * @throws IllegalArgumentException if id is less than 0
     */
    public List<JobDone> getAllJobDoneByUserId(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        List<JobDone> jobDoneList = new ArrayList<JobDone>();

        try {
            String xquery = "for $jobDone in /JobsDone/JobDone let $userId := $jobDone/userId where $userId = \""+ id + "\" return $jobDone";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){

                jobDoneList.add(this.getJobDoneFromDocument(XMLTransformer.stringToDocument(iterator.nextResource().getContent().toString())));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }

        return jobDoneList;
    }

    /**
     * getter for all jobsDone for specified user and time
     * @return list of jobsDone from db
     * @throws ServiceFailureException if error occurs
     * @throws IllegalArgumentException if id is less than 0 or time is null
     */
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

    /**
     * getter total salary
     * @param id id of user
     * @return total salary
     * @throws ServiceFailureException if error occurs
     * @throws IllegalArgumentException if id is less than 0
     */
    public BigDecimal getUserTotalSalary(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        String query = "for $jobDone in /JobsDone/JobDone let $jobTypeId := $jobDone/jobTypeId \n" +
                "where $jobDone/userId = \"" + id + "\" \n" +
                "return \n" +
                "<Work>\n" +
                "{$jobDone/startTime}\n" +
                "{$jobDone/endTime}\n" +
                "{/JobTypes/JobType[@id = $jobTypeId]/pricePerHour}\n" +
                "</Work>";

        BigDecimal totalWage = new BigDecimal(0);
        try {
            ResourceSet result = service.query(query);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){
                totalWage.add(this.getWageFromDocument(XMLTransformer.stringToDocument(
                        iterator.nextResource().getContent().toString())));
            }

            return totalWage;
        } catch (XMLDBException | ParserConfigurationException | IOException | SAXException e) {
            throw new ServiceFailureException("Error getting users wage.", e);
        }
    }

    /**
     * getter total salary for specified time
     * @param id id of user
     * @param endTime end time
     * @param startTime start time
     * @return total salary
     * @throws ServiceFailureException if error occurs
     * @throws IllegalArgumentException if id is less than 0 or time is null
     */
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
        String query = "for $jobDone in /JobsDone/JobDone let $jobTypeId := $jobDone/jobTypeId \n" +
                "where $jobDone/userId = \"" + id + "\" \n" +
                "return \n" +
                "<Work>\n" +
                "{$jobDone/startTime}\n" +
                "{$jobDone/endTime}\n" +
                "{/JobTypes/JobType[@id = $jobTypeId]/pricePerHour}\n" +
                "</Work>";

        BigDecimal totalWage = new BigDecimal(0);
        try {
            ResourceSet result = service.query(query);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){
                totalWage.add(this.getWageFromDocument(XMLTransformer.stringToDocument(
                        iterator.nextResource().getContent().toString()), startTime, endTime));
            }

            return totalWage;
        } catch (XMLDBException | ParserConfigurationException | IOException | SAXException e) {
            throw new ServiceFailureException("Error getting users wage.", e);
        }
    }

    /**
     * creates jobDone from xml
     * @param document document containing jobDone
     * @return jobDone
     * @throws IllegalAccessException if error occurs during parsing
     * @throws IllegalArgumentException if document is null
     * @throws ServiceFailureException if error occurs
     */
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

    /**
     * creates xml element jobDone
     * @param jobDone jobDone
     * @return xml representation of jobDone
     * @throws ParserConfigurationException if creation of dBuilder fails
     */
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

    /**
     * gets wage from document
     * @param document xml representation of wage
     * @return wage
     */
    private BigDecimal getWageFromDocument(Document document) {
        if (document == null){
            throw new IllegalArgumentException("document is null");
        }

        Element root = document.getDocumentElement();

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        BigDecimal pricePerHour = null;

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node node = root.getChildNodes().item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (node.getNodeName()) {
                    case "pricePerHour":
                        pricePerHour = new BigDecimal(node.getTextContent());
                        if (pricePerHour.compareTo(BigDecimal.ZERO) < 0)
                            return BigDecimal.ZERO;
                        break;
                    case "startTime":
                        startTime = LocalDateTime.parse(node.getTextContent());
                        break;
                    case "endTime":
                        endTime = LocalDateTime.parse(node.getTextContent());
                        break;
                }
            }
        }

        if (startTime == null || endTime == null || pricePerHour == null){
            throw new IllegalArgumentException("arguments not initialized");
        }

        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);

        return pricePerHour.multiply(new BigDecimal((double) minutes / 60.0));
    }

    /**
     *
     * @param document xml representation of wage
     * @param startTimeInterval start time
     * @param endTimeInterval end time
     * @return wage
     */
    private BigDecimal getWageFromDocument(Document document, LocalDateTime startTimeInterval, LocalDateTime endTimeInterval) {
        if (document == null){
            throw new IllegalArgumentException("document is null");
        }

        Element root = document.getDocumentElement();

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        BigDecimal pricePerHour = null;

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node node = root.getChildNodes().item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (node.getNodeName()) {
                    case "pricePerHour":
                        pricePerHour = new BigDecimal(node.getTextContent());
                        if (pricePerHour.compareTo(BigDecimal.ZERO) < 0)
                            return BigDecimal.ZERO;
                        break;
                    case "startTime":
                        startTime = LocalDateTime.parse(node.getTextContent());
                        if (startTime.isBefore(startTimeInterval) || startTime.isAfter(endTimeInterval))
                            return BigDecimal.ZERO;
                        break;
                    case "endTime":
                        endTime = LocalDateTime.parse(node.getTextContent());
                        if (endTime.isBefore(startTimeInterval))
                            return BigDecimal.ZERO;
                        if (endTime.isAfter(endTimeInterval))
                            endTime = endTimeInterval;
                        break;
                }
            }
        }

        if (startTime == null || endTime == null || pricePerHour == null){
            throw new IllegalArgumentException("arguments not initialized");
        }

        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);

        return pricePerHour.multiply(new BigDecimal((double) minutes / 60.0));
    }
}
