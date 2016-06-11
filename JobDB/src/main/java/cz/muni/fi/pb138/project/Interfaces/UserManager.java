/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Interfaces;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;

import java.util.List;

/**
 * UserManager interface with the main operations
 * for manipulating with a database getting adding modifying
 * @author Vladislav Malynych
 */
public interface UserManager {
    /**
     * Stores new user into database. Id for the new user is automatically
     * generated and stored into id attribute.
     * 
     * @param user user to be created
     * @throws ServiceFailureException when db operations fails
     */
    void createUser(User user) throws ServiceFailureException;
    
    /**
     * Updates user in database.
     * 
     * @param user updated user to be stored into database.
     * @throws ServiceFailureException when db operations fails
     */
    void updateUser(User user) throws ServiceFailureException;
    
    /**
     * Deletes user from database. 
     * 
     * @param id id of user to be deleted from db.
     * @throws ServiceFailureException when db operations fails
     */
    void deleteUser(Long id) throws ServiceFailureException;
  
    /**
     * Finds and returns user with given id.
     * 
     * @param id primary key of requested user.
     * @return user with given id or null if such user does not exist.
     * @throws ServiceFailureException when db operations fails
     */
    User getUser(Long id) throws ServiceFailureException;
    
    /**
     * Returns list of all users in the database.
     * 
     * @return list of all users in database.
     * @throws ServiceFailureException when db operations fails
     */
    List<User> getAllUsers() throws ServiceFailureException;
}
