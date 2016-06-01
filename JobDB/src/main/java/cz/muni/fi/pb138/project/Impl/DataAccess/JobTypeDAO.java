package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.JobTypeValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class JobTypeDAO {

    public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    private static final String fileName = "JobType.xml";

    private static final String collection = "db/PB138";

    private static String driver = "org.exist.xmldb.DatabaseImpl";

    private XQueryService service;

    protected static void usage() {
        System.out.println("usage: org.exist.examples.xmldb.Put collection docName");
        System.exit(0);
    }

    public JobTypeDAO(){
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

    public void createJobType(JobType jobType){
        try {
            JobTypeValidator.canCreate(jobType);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getJobType(jobType.getId()) != null){
            throw new IllegalArgumentException("DB does contain jobType");
        }

        long maxId = -1;

        for (JobType type : getAllJobTypes()) {
            if (type.getId() > maxId) {
                maxId = type.getId();
            }
        }

        try{
            maxId++;
            jobType.setId(maxId);
            String query = "update insert " + XMLTransformer.nodeToString(createJobTypeElement(jobType)) + " into /JobTypes";
            service.query(query);
        }catch (XMLDBException | ParserConfigurationException | TransformerException | IllegalAccessException e) {
            throw new ServiceFailureException("Error creating JobType.", e);
        }
    }

    public void updateJobType(JobType jobType) throws ServiceFailureException{
        try {
            JobTypeValidator.canUpdate(jobType);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getJobType(jobType.getId()) == null){
            throw new IllegalArgumentException("DB doesn't contain jobType");
        }

        try{
            String query = "update replace /JobTypes/JobType[@id=\"" + jobType.getId() + "\"] " + "with "+ XMLTransformer.nodeToString(createJobTypeElement(jobType));
            service.query(query);
        }catch (XMLDBException | ParserConfigurationException | TransformerException e) {
            throw new ServiceFailureException("Error updating JobType.", e);
        }
    }

    public void deleteJobType(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getJobType(id) == null){
            throw new IllegalArgumentException("DB doesn't contain jobType");
        }

        try {
            String query = "update delete doc('JobType.xml')/JobTypes/JobType[@id = \"" + id + "\"]";
            service.query(query);
        } catch (XMLDBException e) {
            throw new ServiceFailureException("Error deleting JobType.", e);
        }
    }

    public JobType getJobType(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        try {
            String xquery = "for $jobType in /JobTypes/JobType let $jobTypeId := $jobType/@id where $jobTypeId = \"" + id + "\" return $jobType";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){
                return this.getJobTypeFromDocument(ResultDocument.getDocument(
                        iterator.nextResource().getContent().toString()));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException| IllegalAccessException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
        return null;
    }

    public List<JobType> getAllJobTypes(){
        List<JobType> jobTypeList = new ArrayList<JobType>();

        try {
            String xquery = "for $jobType in /JobTypes/JobType return $jobType";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()) {
                jobTypeList.add(getJobTypeFromDocument(ResultDocument.getDocument(
                        iterator.nextResource().getContent().toString())));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }

        return jobTypeList;
    }

    private JobType getJobTypeFromDocument(Document document) throws IllegalAccessException {
        if (document == null){
            throw new IllegalArgumentException("document is null");
        }

        JobType jobType = new JobType();

        Element root = document.getDocumentElement();

        jobType.setId(Long.parseLong(root.getAttribute("id")));

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node node = root.getChildNodes().item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                switch (node.getNodeName()){
                    case "pricePerHour":
                        jobType.setPricePerHour(new BigDecimal(node.getTextContent()));
                        break;
                    case "name":
                        jobType.setName(node.getTextContent());
                        break;
                }
            }
        }
        return jobType;
    }

    private Element createJobTypeElement(JobType jobType) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.newDocument();

        Element jobTypeElement = document.createElement("JobType");
        jobTypeElement.setAttribute("id", Long.toString(jobType.getId()));

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(jobType.getName());

        Element priceElement = document.createElement("pricePerHour");
        priceElement.setTextContent(jobType.getPricePerHour().toString());

        jobTypeElement.appendChild(nameElement);
        jobTypeElement.appendChild(priceElement);
        document.appendChild(jobTypeElement);

        return jobTypeElement;
    }
}
