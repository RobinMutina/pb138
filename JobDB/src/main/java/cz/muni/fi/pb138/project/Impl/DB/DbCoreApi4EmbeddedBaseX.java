package cz.muni.fi.pb138.project.Impl.DB;

import cz.muni.fi.pb138.project.Exceptions.DbException;
import cz.muni.fi.pb138.project.Interfaces.DbCoreApi;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.*;

import java.util.Iterator;

/**
 * Created by xtomasch on 6/1/16.
 */
public class DbCoreApi4EmbeddedBaseX implements DbCoreApi {

    static private Context context = new Context();
    static private String openedCollection;
    static private DbCoreApi4EmbeddedBaseX instance;

    private DbCoreApi4EmbeddedBaseX(){};

    public static DbCoreApi4EmbeddedBaseX getInstance() {
        if (instance == null){
            return new DbCoreApi4EmbeddedBaseX();
        }
        return instance;
    }

    @Override
    public String execXquery(String query) throws DbException{
        String result;
        try {
            result = new XQuery(query).execute(context);
        }catch (BaseXException e){
            throw new DbException(e);
        }
        return  result;
    }

    @Override
    public void openCollection(String name) throws DbException {
        if (openedCollection != null){
            throw new DbException("A collection is already opened");
        }
        try {
            new Open(name).execute(context);
            openedCollection = name;
        }catch (BaseXException e){
            throw new DbException(e);
        }
    }

    @Override
    public void closeCollection() throws DbException {
        try {
            new Close().execute(context);
            openedCollection = null;
        }catch (BaseXException e){
            throw new DbException(e);
        }
    }

    @Override
    public void createCollection(String name, String path) throws DbException {
        try {
            if (path != null){
                new CreateDB(name, path).execute(context);
                openedCollection = name;
            }else{
                new CreateDB(name).execute(context);
                openedCollection = name;
            }
        }catch (BaseXException e){
            throw new DbException(e);
        }
    }

    @Override
    public boolean collectionExists(String name) {
        try {
            new Open(name).execute(context);
            closeCollection();
        }catch (DbException f){
            throw new RuntimeException(f);
        }catch (BaseXException e){
            return false;
        }
        return  true;
    }

    @Override
    public void dropColection(String name) throws DbException{
        if (name.equals(openedCollection)){
            openedCollection = null;
        }
        try {
            new DropDB(name).execute(context);
        }catch (BaseXException e){
            throw new DbException(e);
        }finally {
            context.close();
        }
    }

    @Override
    public void addToCollection(String path) throws DbException {
        try {
            new Add("", path).execute(context);
            new Optimize().execute(context);
        }catch (BaseXException e){
            throw new DbException(e);
        }
    }

    @Override
    public Iterator<String> execXqueryIterated(String query) throws DbException {
        try (EmbeddedBaseXResultIterator it = new EmbeddedBaseXResultIterator(query,context)){
            return it;
        }catch (Exception e){
            throw new DbException(e);
        }
    }
}
