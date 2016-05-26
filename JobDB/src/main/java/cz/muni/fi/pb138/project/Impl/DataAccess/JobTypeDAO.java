package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Validators.JobTypeValidator;

import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class JobTypeDAO {

    public void createJobType(JobType jobType){
        try {
            JobTypeValidator.canCreate(jobType);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        throw new UnsupportedOperationException();
    }

    public void updateJobType(JobType jobType){
        try {
            JobTypeValidator.canUpdate(jobType);
        } catch (ValidationException e) {
            throw new ServiceFailureException(e);
        }

        if (this.getJobType(jobType.getId()) == null){
            throw new IllegalArgumentException("DB doesn't contain jobType");
        }

        throw new UnsupportedOperationException();
    }

    public void deleteJobType(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        if (this.getJobType(id) == null){
            throw new IllegalArgumentException("DB doesn't contain jobType");
        }

        throw new UnsupportedOperationException();
    }

    public JobType getJobType(long id){
        if (id < 0){
            throw new IllegalArgumentException("id is invalid");
        }

        //if (!db.contains(id)){
        //    return null;
        //}

        throw new UnsupportedOperationException();
    }

    public List<JobType> getAllJobTypes(){
        throw new UnsupportedOperationException();
    }
}
