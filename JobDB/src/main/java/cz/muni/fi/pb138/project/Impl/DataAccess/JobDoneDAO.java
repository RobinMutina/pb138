package cz.muni.fi.pb138.project.Impl.DataAccess;

import cz.muni.fi.pb138.project.Entities.JobDone;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class JobDoneDAO {

    public void createJobeDone(JobDone jobDone){
        if (jobDone == null){
            throw new IllegalArgumentException("jobDone is null");
        }

        //if (DB.contains(jobDone)){
        //    throw new IllegalArgumentException("DB already contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    public void updateJobDone(JobDone jobDone){
        if (jobDone == null){
            throw new IllegalArgumentException("jobDone is null");
        }

        //if (!DB.contains(jobDone)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    public void deleteJobDone(JobDone jobDone) {
        if (jobDone == null){
            throw new IllegalArgumentException("jobDone is null");
        }

        //if (!DB.contains(jobDone)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    public JobDone getJobDoneById(long id){
        //if (!DB.contains(id)){
        //    return null;
        //}

        throw new UnsupportedOperationException();
    }

    public List<JobDone> getAllJobDone(){
        throw new UnsupportedOperationException();
    }

    public List<JobDone> getAllJobDoneByUser(long id){
        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    public List<JobDone> getAllJobDoneByUserAtTime(long id, LocalDateTime startTime, LocalDateTime endTime){

        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        if (startTime == null){
            throw new IllegalArgumentException("startTime is null");
        }

        if (endTime == null){
            throw new IllegalArgumentException("endTime is null");
        }

        throw new UnsupportedOperationException();
    }

    public BigDecimal getUserTotalSalary(long id){
        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        throw new UnsupportedOperationException();
    }

    public BigDecimal getUserSalaryAtTime(Long id, LocalDateTime startTime, LocalDateTime endTime){
        //if (!DB.contains(id)){
        //    throw new IllegalArgumentException("DB doesn't contain jobDone");
        //}

        if (startTime == null){
            throw new IllegalArgumentException("startTime is null");
        }

        if (endTime == null){
            throw new IllegalArgumentException("endTime is null");
        }

        throw new UnsupportedOperationException();
    }
}
