package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DataAccess.DB.DbConnection;
import cz.muni.fi.pb138.project.Impl.DataAccess.DB.XMLTransformer;
import cz.muni.fi.pb138.project.Validators.JobTypeValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO operations for JobType
 * @author Martin Sevcik
 */
public class JobTypeDAO {

    /**
     * representing URI
     */
    public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    /**
     * representing name of file
     */
    private static final String fileName = "JobType.xml";

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

    /**
     * non parametric constructor creates new db service
     * @throws ServiceFailureException if error occurs
     */
    public JobTypeDAO(){
        this.service = DbConnection.getConnection(this.driver, this.URI, this.collection, this.fileName);

        if (service == null){
            throw new IllegalArgumentException("service is null");
        }
    }

    /**
     * provides inserting jobType into db
     * @param jobType inserted into db
     * @throws ServiceFailureException if error occurs
     */
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

    /**
     * provides update of existing jobType
     * @param jobType updated jobType
     * @throws ServiceFailureException if error occurs
     */
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

    /**
     * provides delete of existing jobType
     * @param id id of deleted jobType
     * @throws ServiceFailureException if error occurs
     */
    public void deleteJobType(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getJobType(id) == null){
            throw new IllegalArgumentException("DB doesn't contain jobType");
        }

        JobDoneDAO jobDoneDAO = new JobDoneDAO();

        if (!jobDoneDAO.getAllJobDoneByJobTypeId(id).isEmpty()){
            throw new ServiceFailureException("Cannot delete jobType, because jobType has assigned jobDone");
        }

        try {
            String query = "update delete doc('JobType.xml')/JobTypes/JobType[@id = \"" + id + "\"]";
            service.query(query);
        } catch (XMLDBException e) {
            throw new ServiceFailureException("Error deleting JobType.", e);
        }
    }

    /**
     * getter for jobType from db
     * @param id id of required jobType
     * @return User
     * @throws IllegalArgumentException if id is less than 0
     * @throws ServiceFailureException if error occurs
     */
    public JobType getJobType(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        try {
            String xquery = "for $jobType in /JobTypes/JobType let $jobTypeId := $jobType/@id where $jobTypeId = \"" + id + "\" return $jobType";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){
                return this.getJobTypeFromDocument(XMLTransformer.stringToDocument(
                        iterator.nextResource().getContent().toString()));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException| IllegalAccessException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
        return null;
    }

    /**
     * getter for all jobTypes
     * @return list of all jobTypes from db
     * @throws ServiceFailureException if error occurs
     */
    public List<JobType> getAllJobTypes(){
        List<JobType> jobTypeList = new ArrayList<JobType>();

        try {
            String xquery = "for $jobType in /JobTypes/JobType return $jobType";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()) {
                jobTypeList.add(getJobTypeFromDocument(XMLTransformer.stringToDocument(
                        iterator.nextResource().getContent().toString())));
            }
        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }

        return jobTypeList;
    }

    /**
     * creates user from xml
     * @param document document containing user
     * @return JobType
     * @throws IllegalAccessException if error occurs during parsing
     * @throws IllegalArgumentException if document is null
     * @throws ServiceFailureException if error occurs
     */
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

    /**
     * creates xml element jobType
     * @param jobType jobType
     * @return xml representation of jobType
     * @throws ParserConfigurationException if creation of dBuilder fails
     */
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
