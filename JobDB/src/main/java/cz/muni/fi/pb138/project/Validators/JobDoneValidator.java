package cz.muni.fi.pb138.project.Validators;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;

/**
 * representing JobDoneValidator class
 * @author Martin Sevcik
 */
public class JobDoneValidator {

    /**
     * validates creating of jobDone
     * @param jobDone jobDone to validate
     * @throws ValidationException thrown if validation fails
     */
    public static void canCreate(JobDone jobDone) throws ValidationException {
        validation(jobDone);

        if (jobDone.getId() != -1L){
            throw new ValidationException("jobDone.id has been set");
        }
    }

    /**
     * validates updating of jobDone
     * @param jobDone jobDone to validate
     * @throws ValidationException thrown if validation fails
     */
    public static void canUpdate(JobDone jobDone) throws ValidationException {
        validation(jobDone);

        if (jobDone.getId() < 0){
            throw new ValidationException("jobDone.id has not been set");
        }
    }

    /**
     * basic validation for jobdone
     * @param jobDone jobDone to validate
     * @throws ValidationException thrown if validation fails
     */
    private static void validation(JobDone jobDone) throws ValidationException {
        if (jobDone == null){
            throw new ValidationException("jobDone is null");
        }

        if (jobDone.getEndTime() == null){
            throw new ValidationException("jobDone.EndTime is null");
        }

        if (jobDone.getStartTime() == null){
            throw new ValidationException("jobDone.StartTime is null");
        }

        if (jobDone.getStartTime().equals(jobDone.getEndTime()) ||
            jobDone.getStartTime().isAfter(jobDone.getEndTime())){
            throw new ValidationException("jobDone.StartTime and EndTime are invalid");
        }

        if (jobDone.getJobTypeId() == -1L){
            throw new ValidationException("jobDone.JobTypeId is not set");
        }

        if (jobDone.getUserId() == -1L){
            throw new ValidationException("jobDone.UserId is not set");
        }
    }
}
