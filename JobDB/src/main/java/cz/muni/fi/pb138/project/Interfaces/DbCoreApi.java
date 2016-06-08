package cz.muni.fi.pb138.project.Interfaces;

import cz.muni.fi.pb138.project.Exceptions.DbException;

import java.util.Iterator;

/**
 * Intended as abstraction layer, to allow simple querying regardless of the underlying database and connection to it.
 */
public interface DbCoreApi {
    /**
     * Executes XQuery and returns result as a string. A collection must be opened first.
     * @param query an XQuery (v3.0/3.1 for baseX)
     * @return result of the query
     * @throws DbException if something goes wrong
     */
    public String execXquery(String query) throws DbException;

    /**
     * Executes XQuery and retuns result as iterator over the result. Useful when using for clause in the query.
     * @param query an XQuery (v3.0/3.1 for baseX)
     * @return iterator which serves the result in parts.
     * @throws DbException
     */
    public Iterator<String> execXqueryIterated(String query) throws DbException;

    /**
     * Creates a collection from xmls at location pointed to by path.
     * @param name name of the collection
     * @param path path to folder containing the xmls to create db from, if null empty db willbe created
     * @throws DbException
     */
    public void createCollection(String name, String path) throws DbException;

    /**
     * Checks if a collection exists.
     * @param name name of the collection
     * @return true if it does, false else.
     */
    public boolean collectionExists(String name);

    /**
     * Deletes collection.
     * @param name name of the collection
     * @throws DbException
     */
    public void dropColection(String name) throws DbException;

    /**
     * Adds file to currently opened collection.
     * @param path to the file to add
     * @throws DbException
     */
    public void addToCollection(String path) throws DbException;

    public void openCollection(String name) throws DbException;

    public void closeCollection() throws DbException;
}
