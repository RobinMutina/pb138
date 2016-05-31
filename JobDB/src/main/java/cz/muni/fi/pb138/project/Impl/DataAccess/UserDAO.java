package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.UserValidator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import javax.xml.parsers.ParserConfigurationException;
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

    protected static void usage() {
        System.out.println("usage: org.exist.examples.xmldb.Put collection docName");
        System.exit(0);
    }

    public UserDAO(){
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

    public void createUser(User user){
        try {
            UserValidator.canCreate(user);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        throw new UnsupportedOperationException();
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

        throw new UnsupportedOperationException();
    }

    public void deleteUser(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getUser(id) == null){
            throw new IllegalArgumentException("DB doesn't contain user");
        }

        throw new UnsupportedOperationException();
    }

    public User getUser(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        try {
            String xquery = "for $user in /Users/User let $userId := $user/@id where $userId = \"" + id + "\" return $user";
            ResourceSet result = service.query(xquery);
            ResourceIterator iterator = result.getIterator();

            return getUserFromDocument(ResultDocument.getDocument(
                    iterator.nextResource().getContent().toString()));

        } catch (XMLDBException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
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
                IOException | IllegalArgumentException e){
            throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
        return userList;
    }

    private User getUserFromDocument(Document document){
        throw new UnsupportedOperationException();
    }
}
