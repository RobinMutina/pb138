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
 * Created by martin on 4.6.2016.
 */
public class DbConnection {

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
                document.setContent(new File(fileName));
                col.storeResource(document);
            }

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            service.setProperty("indent", "yes");
            System.out.println("connected");

            return service;
        }catch (XMLDBException | ClassNotFoundException | IllegalAccessException | InstantiationException e){
            throw new ServiceFailureException("Setting up database failed.", e);
        }
    }
}
