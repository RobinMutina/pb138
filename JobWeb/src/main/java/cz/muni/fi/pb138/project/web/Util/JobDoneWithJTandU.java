package cz.muni.fi.pb138.project.web.Util;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Entities.User;
import cz.muni.fi.pb138.project.Impl.JobDoneManagerImpl;
import cz.muni.fi.pb138.project.Impl.JobTypeManagerImpl;
import cz.muni.fi.pb138.project.Impl.UserManagerImpl;
import cz.muni.fi.pb138.project.Interfaces.JobDoneManager;
import cz.muni.fi.pb138.project.Interfaces.JobTypeManager;
import cz.muni.fi.pb138.project.Interfaces.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xtomasch on 6/14/16.
 */
public class JobDoneWithJTandU {
    public JobDone jobDone;
    public JobType jobType;
    public User user;

    public static List<JobDoneWithJTandU> getAllJobs(){
        List<JobDone>jobDones = new JobDoneManagerImpl().getAllJobDone();
        List<JobDoneWithJTandU> jobs = new ArrayList<>();

        JobTypeManager jobTypeMan = new JobTypeManagerImpl();
        UserManager userMan = new UserManagerImpl();

        for (JobDone jobDone:jobDones) {
            JobDoneWithJTandU job = new JobDoneWithJTandU();
            job.jobDone = jobDone;
            job.jobType = jobTypeMan.getJobType(jobDone.getJobTypeId());
            job.user = userMan.getUser(jobDone.getUserId());
            jobs.add(job);
        }
        return jobs;
    }
}
