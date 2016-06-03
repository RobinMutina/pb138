package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.DbException;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DB.DbCoreApi4EmbeddedBaseX;
import cz.muni.fi.pb138.project.Impl.DB.EmbeddedBaseXResultIterator;
import cz.muni.fi.pb138.project.Validators.JobTypeValidator;
import org.basex.query.value.item.Str;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by martin on 26.5.2016.
 */
public class JobTypeDAO {

    private DbCoreApi4EmbeddedBaseX dbApi;

    private static final String classSpec = "jobType";

    public JobTypeDAO(){
        this.dbApi = new DbCoreApi4EmbeddedBaseX();
        if (!dbApi.collectionExists(classSpec)) {
            try {
                dbApi.createCollection(classSpec, JobTypeDAO.class.getResource("JobType.xml").getPath());
            } catch (DbException e) {
                throw new ServiceFailureException(e);
            }
        }
    }

    public void createJobType(JobType jobType){
        try{
            JobTypeValidator.canCreate(jobType);

            if (this.getJobType(jobType.getId()) != null){
                throw new IllegalArgumentException("DB does contain jobType");
            }

            long maxId = -1;

            for (JobType type : getAllJobTypes()) {
                if (type.getId() > maxId) {
                    maxId = type.getId();
                }
            }

            maxId++;
            jobType.setId(maxId);
            String query = "update insert " + XMLTransformer.nodeToString(createJobTypeElement(jobType)) + " into /JobTypes";

            dbApi.execXquery(query);
        }catch (DbException | ParserConfigurationException | ValidationException |
                TransformerException | IllegalAccessException e) {
            throw new ServiceFailureException("Error creating JobType.", e);
        }
    }

    public void updateJobType(JobType jobType){
        try{
            JobTypeValidator.canUpdate(jobType);

            if (this.getJobType(jobType.getId()) == null){
                throw new IllegalArgumentException("DB doesn't contain jobType");
            }

            String query = "update replace /JobTypes/JobType[@id=\"" + jobType.getId() + "\"] " + "with "+ XMLTransformer.nodeToString(createJobTypeElement(jobType));
            dbApi.execXquery(query);
        }catch (DbException | ParserConfigurationException | ValidationException | TransformerException e) {
            throw new ServiceFailureException("Error updating JobType.", e);
        }
    }

    public void deleteJobType(long id){
        try {
            if (id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            if (this.getJobType(id) == null){
                throw new IllegalArgumentException("DB doesn't contain jobType");
            }


            String query = "update delete doc('JobType.xml')/JobTypes/JobType[@id = \"" + id + "\"]";
            dbApi.execXquery(query);
        } catch (DbException | IllegalArgumentException e) {
            throw new ServiceFailureException("Error deleting JobType.", e);
        }
    }

    public JobType getJobType(long id){
        try {
            if (id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            String xquery = "for $jobType in /JobTypes/JobType let $jobTypeId := $jobType/@id where $jobTypeId = \"" + id + "\" return $jobType";
            String result = dbApi.execXquery(xquery);

            if (result.isEmpty()){
                return null;
            }

            return this.getJobTypeFromDocument(ResultDocument.getDocument(result));
        } catch (DbException | ParserConfigurationException |  SAXException |
                IOException | IllegalArgumentException| IllegalAccessException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
    }

    public List<JobType> getAllJobTypes(){
        List<JobType> jobTypeList = new ArrayList<JobType>();

        String xquery = "for $jobType in /JobTypes/JobType return $jobType";
        try (EmbeddedBaseXResultIterator it = (EmbeddedBaseXResultIterator) dbApi.execXqueryIterated(
                xquery,
                classSpec))
        {

            it.forEachRemaining(new Consumer() {
                @Override
                public void accept(Object o) {
                    try {
                        jobTypeList.add(getJobTypeFromDocument(ResultDocument.getDocument(o.toString())));
                    } catch (ParserConfigurationException | SAXException |
                            IOException | IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            throw new ServiceFailureException("Error getting all users.", e);
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
