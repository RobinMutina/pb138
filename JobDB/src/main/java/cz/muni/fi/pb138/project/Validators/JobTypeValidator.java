package cz.muni.fi.pb138.project.Validators;

import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Entities.JobType;

import java.math.BigDecimal;

/**
 * representing JobTypeValidator class
 * @author Martin Sevcik
 */
public class JobTypeValidator {

    /**
     * validates creating of jobType
     * @param jobType jobType to validate
     * @throws ValidationException thrown if validation fails
     */
    public static void canCreate(JobType jobType) throws ValidationException {
        validation(jobType);

        if (jobType.getId() != -1L) {
            throw new ValidationException("jobType.Id has been set");
        }
    }

    /**
     * validates updating of jobType
     * @param jobType jobType to validate
     * @throws ValidationException thrown if validation fails
     */
    public static void canUpdate(JobType jobType) throws ValidationException {
        validation(jobType);

        if (jobType.getId() < 0L){
            throw new ValidationException("jobType.Id is less than zero");
        }
    }

    /**
     * basic validation for jobType
     * @param jobType jobType to validate
     * @throws ValidationException thrown if validation fails
     */
    private static void validation(JobType jobType) throws ValidationException {
        if (jobType == null){
            throw new ValidationException("jobType is null");
        }

        if (jobType.getName() == null || jobType.getName().isEmpty()) {
            throw new ValidationException("jobType.Name is null or empty");
        }

        if (jobType.getPricePerHour().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("jobType.PricePerHour is equal or less than zero");
        }

    }
}
