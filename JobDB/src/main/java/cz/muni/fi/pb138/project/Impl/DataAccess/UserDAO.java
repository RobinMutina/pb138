package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.DbException;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DB.DbCoreApi4EmbeddedBaseX;
import cz.muni.fi.pb138.project.Impl.DB.EmbeddedBaseXResultIterator;
import cz.muni.fi.pb138.project.Validators.UserValidator;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by martin on 26.5.2016.
 */
public class UserDAO {

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
                    "let $user := //User[@id='" + id.toString() + "']\n" +
                            "return fn:empty($user)"
            );
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
        return result.equals("true");
    }

    private static final DbCoreApi4EmbeddedBaseX dbApi = DbCoreApi4EmbeddedBaseX.getInstance();

    public void createUser(User user){
        try {
            UserValidator.canCreate(user);

            if (this.getUser(user.getId()) != null){
                throw new IllegalArgumentException("DB does contain jobType");
            }

            long id = getNewUniqueID();

            user.setId(id);
            String query = "update insert " + XMLTransformer.nodeToString(createUserElement(user)) + " into /Users";
            dbApi.execXquery(query);
        }catch (DbException | ParserConfigurationException | ValidationException |
                TransformerException | IllegalAccessException e) {
            throw new ServiceFailureException("Error creating User.", e);
        }
    }

    public void updateUser(User user){
        try{
            UserValidator.canUpdate(user);

            if (this.getUser(user.getId()) == null){
                throw new IllegalArgumentException("DB doesn't contain user");
            }

            String query = "update replace /Users/User[@id=\"" + user.getId() + "\"] " + "with "+ XMLTransformer.nodeToString(createUserElement(user));
            dbApi.execXquery(query);
        }catch (DbException | ParserConfigurationException | ValidationException |
                 IllegalArgumentException | TransformerException e) {
            throw new ServiceFailureException("Error updating User.", e);
        }
    }

    public void deleteUser(long id){
        try {
            if (id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            if (this.getUser(id) == null){
                throw new IllegalArgumentException("DB doesn't contain user");
            }

            String query = "update delete doc('User.xml')/Users/User[@id = \"" + id + "\"]";
            dbApi.execXquery(query);
        } catch (DbException | IllegalArgumentException e) {
            throw new ServiceFailureException("Error deleting User.", e);
        }
    }

    public User getUser(long id){

        try {
            if (id < 0){
                throw new IllegalArgumentException("id is invalid");
            }


            String xquery = "for $user in /Users/User let $userId := $user/@id where $userId = \"" + id + "\" return $user";
            String result = dbApi.execXquery(xquery);

            if (result.isEmpty()){
                return null;
            }

            return this.getUserFromDocument(ResultDocument.getDocument(result));

        } catch (DbException | ParserConfigurationException | SAXException |
                IOException | IllegalArgumentException | IllegalAccessException e){
              throw new ServiceFailureException("Error getting all jobTypes.", e);
        }
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>();

        String xquery = "for $user in /Users/User return $user";
        try (EmbeddedBaseXResultIterator it = (EmbeddedBaseXResultIterator) dbApi.execXqueryIterated(
                xquery))
        {

            it.forEachRemaining(new Consumer() {
                @Override
                public void accept(Object o) {
                    try {
                        userList.add(getUserFromDocument(ResultDocument.getDocument(o.toString())));
                    } catch (ParserConfigurationException | SAXException |
                            IOException | IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            throw new ServiceFailureException("Error getting all users.", e);
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
