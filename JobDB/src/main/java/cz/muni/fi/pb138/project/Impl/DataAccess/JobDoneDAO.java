package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.JobDoneValidator;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.exist.dom.ElementImpl;
import org.exist.xmldb.XmldbURI;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Created by martin on 26.5.2016.
 */
public class JobDoneDAO {

    public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    private static final String fileName = "JobDone.xml";

    private static final String collection = "db/PB138";

    private static String driver = "org.exist.xmldb.DatabaseImpl";

    private XQueryService service;

    protected static void usage() {
        System.out.println("usage: org.exist.examples.xmldb.Put collection docName");
        System.exit(0);
    }

    public static void main(String[] args) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JobDoneDAO jobDoneDAO = new JobDoneDAO();
        JobDone jobDone = new JobDone();
        jobDone.setEndTime(LocalDateTime.MAX);
        jobDone.setStrartTime(LocalDateTime.MIN);
        jobDone.setUserId(2L);
        jobDone.setJobTypeId(2L);

        jobDoneDAO.createJobDone(jobDone);
    }

    public JobDoneDAO() throws ServiceFailureException {

        try {
            Class<?> cl = Class.forName(driver);
            Database database = (Database)cl.newInstance();
            DatabaseManager.registerDatabase(database);
            Collection col = DatabaseManager.getCollection(URI + collection);

            this.service = (XQueryService) col.getService("XQueryService", "1.0");
            this.service.setProperty("indent", "yes");
            System.out.println("connected");
        /*
        // initialize driver
        Class<?> cl = Class.forName(driver);
        Database database = (Database)cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        // try to get collection
        Collection col =
                DatabaseManager.getCollection(URI + collection);
        if(col == null) {
            // collection does not exist: get root collection and create.
            // for simplicity, we assume that the new collection is a
            // direct child of the root collection, e.g. /db/test.
            // the example will fail otherwise.
            Collection root = DatabaseManager.getCollection(URI + XmldbURI.ROOT_COLLECTION);
            CollectionManagementService mgtService =
                    (CollectionManagementService)root.getService("CollectionManagementService", "1.0");
            col = mgtService.createCollection(collection.substring((XmldbURI.ROOT_COLLECTION + "/").length()));
        }
        File f = new File(fileName);

        // create new XMLResource
        XMLResource document = (XMLResource)col.createResource(f.getName(), "XMLResource");

        document.setContent(f);
        System.out.print("storing document " + document.getId() + "...");
        col.storeResource(document);
        System.out.println("ok.");
        */
        }catch (XMLDBException | ClassNotFoundException | IllegalAccessException | InstantiationException e){
            throw new ServiceFailureException("Setting up database failed.", e);
        }
    }

    public void createJobDone(JobDone jobDone) throws ServiceFailureException {
        try {
            JobDoneValidator.canCreate(jobDone);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        //if (this.getJobDoneById(jobDone.getId()) != null){
        //    throw new IllegalArgumentException("DB does contain jobDone");
        //}

        //TODO validation of ID
        //TODO set to proper ID
        try{

            jobDone.setId(1L);

            String query = "update insert " +
                            XMLTransformer.nodeToString(createJobDoneElement(jobDone)) +
                            " into /JobsDone";
            service.query(query);
        }catch (XMLDBException | IllegalAccessException | ParserConfigurationException | TransformerException e) {
            throw new ServiceFailureException("Error creating JobDone.", e);
        }
    }

    public void updateJobDone(JobDone jobDone){
        try {
            JobDoneValidator.canUpdate(jobDone);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getJobDoneById(jobDone.getId()) == null){
            throw new IllegalArgumentException("DB doesn't contain jobDone");
        }

        throw new UnsupportedOperationException();
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
            service.query(query);
        } catch (XMLDBException e) {
            throw new ServiceFailureException("Error deleting JobDone.", e);
        }
    }

    public JobDone getJobDoneById(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        try {
            String xquery = "for $jobDone in /JobsDone/JobDone let $jobDoneId := $jobDone/@id where $jobDoneId = \""+ id + "\" return $jobDone";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){
                return this.getJobDoneFromDocument(ResultDocument.getDocument(
                        iterator.nextResource().getContent().toString()));
            }

        } catch (XMLDBException | ParserConfigurationException | SAXException | IOException | IllegalArgumentException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }
        return null;
    }

    public List<JobDone> getAllJobDone(){

        List<JobDone> jobDoneList = new ArrayList<JobDone>();

        try {
            String xquery = "for $jobDone in /JobsDone/JobDone return $jobDone";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){

                jobDoneList.add(this.getJobDoneFromDocument(
                        ResultDocument.getDocument(iterator.nextResource().getContent().toString())));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }

        return jobDoneList;
    }

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

                jobDoneList.add(this.getJobDoneFromDocument(ResultDocument.getDocument(iterator.nextResource().getContent().toString())));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException | IOException | IllegalArgumentException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
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

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

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

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    private JobDone getJobDoneFromDocument(Document document) {

        throw new UnsupportedOperationException();
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
