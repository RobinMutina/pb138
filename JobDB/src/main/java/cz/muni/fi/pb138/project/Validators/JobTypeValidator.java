package cz.muni.fi.pb138.project.Validators;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;

import java.math.BigDecimal;

/**
 * Created by martin on 26.5.2016.
 */
public class JobTypeValidator {

    public static void canCreate(JobType jobType) throws ValidationException {
        validation(jobType);

        if (jobType.getId() != -1L) {
            throw new ValidationException("jobType.Id has been set");
        }
    }

    public static void canUpdate(JobType jobType) throws ValidationException {
        validation(jobType);

        if (jobType.getId() < 0L){
            throw new ValidationException("jobType.Id is less than zero");
        }
    }

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
