package cz.muni.fi.pb138.project.Validators;

import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;

/**
 * representing UserValidator class
 * @author Martin Sevcik
 */
public class UserValidator {

    /**
     * validates creating of user
     * @param user user to validate
     * @throws ValidationException thrown if validation fails
     */
    public static void canCreate(User user) throws ValidationException {
        validation(user);

        if (user.getId() != -1L){
            throw new ValidationException("user.Id has been set.");
        }
    }

    /**
     * validates updating of user
     * @param user user to validate
     * @throws ValidationException thrown if validation fails
     */
    public static void canUpdate(User user) throws ValidationException {
        validation(user);

        if (user.getId() < 0L){
            throw new ValidationException("user.Id has not been set.");
        }
    }

    /**
     * basic validation for user
     * @param user user to validate
     * @throws ValidationException thrown if validation fails
     */
    private static void validation(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("user is null");
        }

        if (user.getName() == null || user.getName().isEmpty()){
            throw new ValidationException("user.Name is null or empty");
        }
    }
}
