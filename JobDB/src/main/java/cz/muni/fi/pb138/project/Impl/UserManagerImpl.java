package cz.muni.fi.pb138.project.Impl;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Impl.DataAccess.UserDAO;
import cz.muni.fi.pb138.project.Interfaces.UserManager;


import java.util.List;

/**
 * Created by martin on 26.5.2016.
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
        throw new UnsupportedOperationException();
    }

    /**
     * Updates user in database.
     *
     * @param user updated user to be stored into database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void updateUser(User user) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes user from database.
     *
     * @param id id of user to be deleted from db.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void deleteUser(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    /**
     * Returns list of all users in the database.
     *
     * @return list of all users in database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<User> getAllUsers() throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }
}
