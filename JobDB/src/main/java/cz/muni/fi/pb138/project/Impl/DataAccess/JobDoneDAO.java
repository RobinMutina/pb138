package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.JobDoneValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class JobDoneDAO {

    public void createJobDone(JobDone jobDone){
        try {
            JobDoneValidator.canCreate(jobDone);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        throw new UnsupportedOperationException();
    }

    public void updateJobDone(JobDone jobDone){
        try {
            JobDoneValidator.canUpdate(jobDone);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getJobDoneById(jobDone.getId()) == null){
            throw new IllegalArgumentException("DB doesn't contain jobDone");
        }

        throw new UnsupportedOperationException();
    }

    public void deleteJobDone(long id) {
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getJobDoneById(id) == null){
            throw new IllegalArgumentException("DB doesn't contain jobDone");
        }

        throw new UnsupportedOperationException();
    }

    public JobDone getJobDoneById(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        //if (!DB.contains(id)){
        //    return null;
        //}

        throw new UnsupportedOperationException();
    }

    public List<JobDone> getAllJobDone(){
        throw new UnsupportedOperationException();
    }

    public List<JobDone> getAllJobDoneByUserId(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain user");
        //}

        throw new UnsupportedOperationException();
    }

    public List<JobDone> getAllJobDoneByUserAtTime(long id, LocalDateTime startTime, LocalDateTime endTime){

        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (startTime == null){
            throw new IllegalArgumentException("startTime is null");
        }

        if (endTime == null){
            throw new IllegalArgumentException("endTime is null");
        }

        if (startTime.equals(endTime) ||
                startTime.isAfter(endTime)){
            throw new IllegalArgumentException("startTime and endTime are invalid");
        }

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain user");
        //}
        throw new UnsupportedOperationException();
    }

    public BigDecimal getUserTotalSalary(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    public BigDecimal getUserSalaryAtTime(Long id, LocalDateTime startTime, LocalDateTime endTime){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (startTime == null){
            throw new IllegalArgumentException("startTime is null");
        }

        if (endTime == null){
            throw new IllegalArgumentException("endTime is null");
        }

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }
}
