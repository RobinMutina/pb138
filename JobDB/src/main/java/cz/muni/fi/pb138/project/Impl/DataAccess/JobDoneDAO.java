package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.JobDoneValidator;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.exist.xmldb.XmldbURI;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
        jobDoneDAO.getAllJobDone();
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

    public void createJobDone(JobDone jobDone){
        try {
            JobDoneValidator.canCreate(jobDone);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        throw new UnsupportedOperationException();
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

        throw new UnsupportedOperationException();
    }

    public JobDone getJobDoneById(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        try {
            String xquery = "for $jobDone in /JobsDone/JobDone let $jobDoneId := $jobDone/@id where $jobDoneId = \""+ id + "\" return $jobDone";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            return this.getJobDoneFromDocument(ResultDocument.getDocument(
                    iterator.nextResource().getContent().toString()));

        } catch (XMLDBException | ParserConfigurationException | SAXException | IOException | IllegalArgumentException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }
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
}
