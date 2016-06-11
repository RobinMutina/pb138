package cz.muni.fi.pb138.project.Impl;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DataAccess.UserDAO;
import cz.muni.fi.pb138.project.Interfaces.UserManager;


import java.util.List;

/**
 * implementation of UserManager interface
 * @author Martin Sevcik
 */
public class UserManagerImpl implements UserManager {

    private UserDAO userDAO;

    /**
     * Stores new user into database. Id for the new user is automatically
     * generated and stored into id attribute.
     *
     * @param user user to be created
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void createUser(User user) throws ServiceFailureException {
        try {
            userDAO.createUser(user);
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Creating user failed.", ex);
        }
    }

    /**
     * Updates user in database.
     *
     * @param user updated user to be stored into database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void updateUser(User user) throws ServiceFailureException {
        try {
            userDAO.updateUser(user);
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Updating user failed.", ex);
        }
    }

    /**
     * Deletes user from database.
     *
     * @param id id of user to be deleted from db.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void deleteUser(Long id) throws ServiceFailureException {

        try {
            if (id == null || id < 0) {
                throw new IllegalArgumentException("id is invalid.");
            }

            userDAO.deleteUser(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Deleting user failed.", ex);
        }
    }

    /**
     * Finds and returns user with given id.
     *
     * @param id primary key of requested user.
     * @return user with given id or null if such user does not exist.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public User getUser(Long id) throws ServiceFailureException {
        try {
            if (id == null || id < 0) {
                throw new IllegalArgumentException("id is invalid.");
            }

            return userDAO.getUser(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Getting user failed.", ex);
        }
    }

    /**
     * Returns list of all users in the database.
     *
     * @return list of all users in database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<User> getAllUsers() throws ServiceFailureException {
        try {
            return userDAO.getAllUsers();
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Deleting user failed.", ex);
        }
    }
}