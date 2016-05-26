package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobType;

import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class JobTypeDAO {

    public void createJobType(JobType jobType){
        if (jobType == null){
            throw new IllegalArgumentException("jobType is null");
        }

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB already contains jobType");
        //}

        throw new UnsupportedOperationException();
    }

    public void updateJobType(JobType jobType){
        if (jobType == null){
            throw new IllegalArgumentException("jobType is null");
        }

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobType");
        //}

        throw new UnsupportedOperationException();
    }

    public void deleteJobType(long id){
        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobType");
        //}

        throw new UnsupportedOperationException();
    }

    public JobType getJobType(long id){
        //if (!db.contains(id)){
        //    return null;
        //}

        throw new UnsupportedOperationException();
    }

    public List<JobType> getAllJobTypes(){
        throw new UnsupportedOperationException();
    }
}
