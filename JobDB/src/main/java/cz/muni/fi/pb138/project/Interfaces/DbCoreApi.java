package cz.muni.fi.pb138.project.Interfaces;

import cz.muni.fi.pb138.project.Exceptions.DbException;

import java.nio.file.Path;

/**
 * Created by xtomasch on 6/1/16.
 */
public interface DbCoreApi {
    public String execXquery(String query) throws DbException;
    public String execXquery(String query, String collection) throws DbException;
    public void createCollection(String name, String path) throws DbException;
    public boolean collectionExists(String name);
    public void dropColection(String name) throws DbException;
    public void addToCollection(String name, String path) throws DbException;
}
