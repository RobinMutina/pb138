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
    @Override
    public String execXquery(String query) throws DbException{
        Context context = new Context();
        String result;
        try {
            result = new XQuery(query).execute(context);
        }catch (BaseXException e){
            throw new DbException(e);
        }finally {
            context.close();
        }
        return  result;
    }

    @Override
    public String execXquery(String query, String collection) throws DbException{
        Context context = new Context();
        String result;
        try {
            new Open(collection).execute(context);
            result = new XQuery(query).execute(context);
        }catch (BaseXException e){
            throw new DbException(e);
        }finally {
            context.close();
        }
        return  result;
    }

    @Override
    public void createCollection(String name, String path) throws DbException {
        Context context = new Context();
        try {
            if (path != null){
                new CreateDB(name, path).execute(context);
            }else{
                new CreateDB(name).execute(context);
            }
        }catch (BaseXException e){
            throw new DbException(e);
        }finally {
            context.close();
        }
    }

    @Override
    public boolean collectionExists(String name) {
        Context context = new Context();
        try {
            new Open(name).execute(context);
        }catch (BaseXException e){
            return false;
        }finally {
            context.close();
        }
        return  true;
    }

    @Override
    public void dropColection(String name) throws DbException{
        Context context = new Context();
        try {
            new DropDB(name).execute(context);

        }catch (BaseXException e){
            throw new DbException(e);
        }finally {
            context.close();
        }
    }

    @Override
    public void addToCollection(String name, String path) throws DbException {
        Context context = new Context();
        try {
            new Open(name).execute(context);
            new Add("", path).execute(context);
            new Optimize().execute(context);

        }catch (BaseXException e){
            throw new DbException(e);
        }finally {
            context.close();
        }
    }

    @Override
    public Iterator<String> execXqueryIterated(String query, String collection) throws DbException {
        try (EmbeddedBaseXResultIterator it = new EmbeddedBaseXResultIterator(query, collection)){
            return it;
        }catch (Exception e){
            throw new DbException(e);
        }
    }
}
