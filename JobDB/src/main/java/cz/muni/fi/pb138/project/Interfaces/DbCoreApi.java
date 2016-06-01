package cz.muni.fi.pb138.project.Interfaces;

import cz.muni.fi.pb138.project.Exceptions.DbException;

/**
 * Intended as abstraction layer, to allow simple querying regardless of the underlying database and connection to it.
 */
public interface DbCoreApi {
    /**
     * Executes XQuery and retuns result as a string.
     * @param query a XQuery (v3.0/3.1 for baseX), should contain collection() (or db:open which is baseX specific) to
     *              open the database first
     * @return result of the query
     * @throws DbException if something goes wrong
     */
    public String execXquery(String query) throws DbException;

    /**
     * Executes XQuery and retuns result as a string.
     * @param query a XQuery (v3.0/3.1 for baseX)
     * @param collection designates collection to open and use in queries
     * @return result of the query
     * @throws DbException DbException if something goes wrong
     */
    public String execXquery(String query, String collection) throws DbException;

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
     * Adds file to collection.
     * @param name name of the collection
     * @param path to the file to add
     * @throws DbException
     */
    public void addToCollection(String name, String path) throws DbException;
}
