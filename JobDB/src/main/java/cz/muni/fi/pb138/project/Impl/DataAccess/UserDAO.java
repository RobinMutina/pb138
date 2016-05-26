package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.UserValidator;

import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class UserDAO {

    public void createUser(User user){
        try {
            UserValidator.canCreate(user);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        throw new UnsupportedOperationException();
    }

    public void updateUser(User user){
        try {
            UserValidator.canUpdate(user);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getUser(user.getId()) == null){
            throw new IllegalArgumentException("DB doesn't contain user");
        }

        throw new UnsupportedOperationException();
    }

    public void deleteUser(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getUser(id) == null){
            throw new IllegalArgumentException("DB doesn't contain user");
        }

        throw new UnsupportedOperationException();
    }

    public User getUser(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        //if (!DB.contains(id)){
        //    return null;
        //}

        throw new UnsupportedOperationException();
    }

    public List<User> getAllUsers(){
        throw new UnsupportedOperationException();
    }
}
