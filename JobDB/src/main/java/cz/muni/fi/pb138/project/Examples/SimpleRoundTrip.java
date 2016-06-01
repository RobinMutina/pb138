package cz.muni.fi.pb138.project.Examples;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.DbException;
import cz.muni.fi.pb138.project.Impl.DB.DbCoreApi4EmbeddedBaseX;
import cz.muni.fi.pb138.project.Interfaces.DbCoreApi;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Random;

/**
 * Proof of concept, showcases use of DbCoreApi, creates db stores 1 user in it and retrieves it.
 * Created by xtomasch on 6/1/16.
 */
public class SimpleRoundTrip {
    public static void main(String[] args){
        //Maybe il'l make all the api method static so this is not needed
        DbCoreApi dbApi = new DbCoreApi4EmbeddedBaseX();

        //Checks if collection exists, and creates new one if needed
        if (!dbApi.collectionExists("test")) {
            try {
                dbApi.createCollection("test", SimpleRoundTrip.class.getResource("test.xml").getPath());
            } catch (DbException e) {
                throw new RuntimeException(e);
            }
        }

        // id are generated randomly and checked for uniqueness, because xml dbs do not seem to provide any means of
        // generating new id's, tried generate-id from xslt but that didn't work as expected
        // FIXME: why limit id to positive numbers ?? and assign -1 default? i thing it's better to leave it null so may throw NullPointerException when not set

        Random randomno = new Random();
        Long id =  randomno.nextLong();
        while (id < new Long(0) || !isUniqueID(id)){
            id = randomno.nextLong();
        }


        // this inserts new user into users with the generated id
        try {
            dbApi.execXquery(
                    "let $users := //Users \n"+
                    "let $user :=   <User id='"+id+"'>"+
                                        "<name>Pako</name>"+
                                    "</User> \n"+
                    "return insert node $user into $users\n",
                    "test"
            );
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        String result;

        // now we read the user from the database
        try {
            result = dbApi.execXquery(
                    "let $user := //User[@id='"+id.toString()+"']\n" +
                            "return $user",
                    "test"
            );
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        // this is what we get
        System.out.println(result+"\n");

        // use a helper funct to construct DOM from xml string
        // FIXME: it may be faster and simpler to use regex instead of dom for this parsing
        Document doc;
        try {
            doc = loadXMLFromString(result);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        User a = new User();

        //get the values from DOM
        try {
            a.setId(Long.parseLong(doc.getDocumentElement().getAttribute("id")));
            a.setName(doc.getDocumentElement().getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        System.out.println(a);

        //and u can drop collection like this if u want to
        try {
            dbApi.dropColection("test");
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Utility funct to check if id is unique
     * @param id
     * @return true if the id unique, else false
     */
    public static Boolean isUniqueID(Long id){
        String result;
        DbCoreApi dbApi = new DbCoreApi4EmbeddedBaseX();
        // FIXME: optimize the query not to return whole user but somthing shorter, more efficient
        try {
            result = dbApi.execXquery(
                    "let $user := //User[@id='"+id.toString()+"']\n" +
                            "return $user",
                    "test"
            );
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        // if result is empty user with that id is not present in db
        return result.isEmpty();
    }


    /**
     * Constructs w3c DOM document from xml string. Got it from stackoverflow and works ok.
     * @param xml
     * @return
     * @throws Exception
     */
    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new InputSource(new StringReader(xml)));
    }
}
