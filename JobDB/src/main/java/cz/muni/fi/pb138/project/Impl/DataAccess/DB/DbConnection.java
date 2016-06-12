package cz.muni.fi.pb138.project.Impl.DataAccess.DB;

import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import org.exist.xmldb.XmldbURI;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import java.io.File;

/**
 * Appi for connecting to database
 * @author Martin Sevcik
 */
public class DbConnection {

    /**
     * provides creation of new XQueryService
     * @param driver representing db driver
     * @param URI  representing db uri
     * @param collection  representing db collection
     * @param fileName  representing db filename
     * @return new XQueryService
     * @throws ServiceFailureException if error occurs
     */
    public static XQueryService getConnection(String driver, String URI, String collection, String fileName){
        try {
            Class<?> cl = Class.forName(driver);
            Database database = (Database)cl.newInstance();
            DatabaseManager.registerDatabase(database);
            Collection col = DatabaseManager.getCollection(URI + collection);

            if (col == null){
                Collection root = DatabaseManager.getCollection(URI + XmldbURI.ROOT_COLLECTION);
                CollectionManagementService mgtService =
                        (CollectionManagementService)root.getService("CollectionManagementService", "1.0");
                col = mgtService.createCollection(collection.substring((XmldbURI.ROOT_COLLECTION + "/").length()));
            }

            XMLResource document = (XMLResource)col.getResource(fileName);
            if (document == null){
                document = (XMLResource)col.createResource(fileName, "XMLResource");
                document.setContent(new File(fileName));
                col.storeResource(document);
            }

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            service.setProperty("indent", "yes");
            System.out.println("connected");

            if (service == null){
                throw new IllegalArgumentException("service is null");
            }

            return service;
        }catch (Exception e){
            throw new ServiceFailureException("Setting up database failed.", e);
        }
    }
}
