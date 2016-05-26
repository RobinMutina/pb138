package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.User;

import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class UserDAO {

    public void createUser(User user){
        if (user == null){
            throw new IllegalArgumentException("jobDone is null");
        }

        //if (DB.contains(user)){
        //    throw new IllegalArgumentException("DB already contain user");
        //}

        throw new UnsupportedOperationException();
    }

    public void updateUser(User user){
        if (user == null){
            throw new IllegalArgumentException("jobDone is null");
        }

        //if (!DB.contains(user)){
        //    throw new IllegalArgumentException("DB doesn't contain user");
        //}

        throw new UnsupportedOperationException();
    }

    public void deleteUser(long id){
        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain user");
        //}

        throw new UnsupportedOperationException();
    }

    public User getUser(long id){
        //if (!DB.contains(id)){
        //    return null;
        //}

        throw new UnsupportedOperationException();
    }

    public List<User> getAllUsers(){
        throw new UnsupportedOperationException();
    }
}
