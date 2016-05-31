package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.JobDoneValidator;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.exist.xmldb.XmldbURI;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;


/**
 * Created by martin on 26.5.2016.
 */
public class JobDoneDAO {

    public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    private static final String fileName = "JobType.xml";

    private static final String collection = "db/PB138";

    private static String driver = "org.exist.xmldb.DatabaseImpl";

    private XQueryService service;

    protected static void usage() {
        System.out.println("usage: org.exist.examples.xmldb.Put collection docName");
        System.exit(0);
    }

    public static void main(String[] args) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JobDoneDAO jobDoneDAO = new JobDoneDAO();
        //jobDoneDAO.getAllJobDone();
    }

    public JobDoneDAO() throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException {


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

        //if (!DB.contains(id)){
        //    return null;
        //}

        throw new UnsupportedOperationException();
    }

    public List<JobDone> getAllJobDone(){
        String xquery = "for $x in /JobsDone/JobDone/userId/text() return $x ";
        try {
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()){
                System.out.println(iterator.nextResource().getContent().toString());
            }
        } catch (XMLDBException e) {
            throw new ServiceFailureException("Error getting all JobDone.", e);
        }
        return null;
    }

    public List<JobDone> getAllJobDoneByUserId(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain user");
        //}

        throw new UnsupportedOperationException();
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

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain user");
        //}
        throw new UnsupportedOperationException();
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
}
