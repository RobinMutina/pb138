package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DataAccess.DB.DbConnection;
import cz.muni.fi.pb138.project.Impl.DataAccess.DB.ResultDocument;
import cz.muni.fi.pb138.project.Impl.DataAccess.DB.XMLTransformer;
import cz.muni.fi.pb138.project.Validators.UserValidator;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class UserDAO {

    public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    private static final String fileName = "User.xml";

    private static final String collection = "db/PB138";

    private static String driver = "org.exist.xmldb.DatabaseImpl";

    private XQueryService service;

    public UserDAO(){
        this.service = DbConnection.getConnection(this.driver, this.URI, this.collection, this.fileName);

        if (service == null){
            throw new IllegalArgumentException("service is null");
        }
    }

    public void createUser(User user){
        try {
            UserValidator.canCreate(user);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getUser(user.getId()) != null){
            throw new IllegalArgumentException("DB does contain jobType");
        }

        long maxId = -1;

        for (User userIter : getAllUsers()) {
            if (userIter.getId() > maxId) {
                maxId = userIter.getId();
            }
        }

        try{
            maxId++;
            user.setId(maxId);
            String query = "update insert " + XMLTransformer.nodeToString(createUserElement(user)) + " into /Users";
            service.query(query);
        }catch (XMLDBException | ParserConfigurationException | TransformerException | IllegalAccessException e) {
            throw new ServiceFailureException("Error creating User.", e);
        }
    }

    public void updateUser(User user){
        try {
            UserValidator.canUpdate(user);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getUser(user.getId()) == null){
            throw new IllegalArgumentException("DB doesn't contain user");
        }

        try{
            String query = "update replace /Users/User[@id=\"" + user.getId() + "\"] " + "with "+ XMLTransformer.nodeToString(createUserElement(user));
            service.query(query);
        }catch (XMLDBException | ParserConfigurationException | TransformerException e) {
            throw new ServiceFailureException("Error updating User.", e);
        }
    }

    public void deleteUser(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getUser(id) == null){
            throw new IllegalArgumentException("DB doesn't contain user");
        }

        try {
            String query = "update delete doc('User.xml')/Users/User[@id = \"" + id + "\"]";
            service.query(query);
        } catch (XMLDBException e) {
            throw new ServiceFailureException("Error deleting User.", e);
        }
    }

    public User getUser(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        try {
            String xquery = "for $user in /Users/User let $userId := $user/@id where $userId = \"" + id + "\" return $user";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()){
                return this.getUserFromDocument(ResultDocument.getDocument(
                        iterator.nextResource().getContent().toString()));
            }

        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
        return null;
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>();
        try {
            String xquery = "for $user in /Users/User return $user";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            while (iterator.hasMoreResources()) {
                userList.add(getUserFromDocument(ResultDocument.getDocument(
                        iterator.nextResource().getContent().toString())));
            }

        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
        return userList;
    }

    private User getUserFromDocument(Document document) throws IllegalAccessException {
        if (document == null){
            throw new IllegalArgumentException("document is null");
        }

        User user = new User();

        Element root = document.getDocumentElement();

        user.setId(Long.parseLong(root.getAttribute("id")));

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node node = root.getChildNodes().item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == "name"){
                user.setName(node.getTextContent());
            }
        }
        return user;
    }

    private Element createUserElement(User user) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.newDocument();

        Element userElement = document.createElement("User");
        userElement.setAttribute("id", Long.toString(user.getId()));

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(user.getName());

        userElement.appendChild(nameElement);
        document.appendChild(userElement);

        return userElement;
    }
}
