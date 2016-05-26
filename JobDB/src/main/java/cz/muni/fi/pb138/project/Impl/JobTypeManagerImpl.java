package cz.muni.fi.pb138.project.Impl;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Impl.DataAccess.JobTypeDAO;
import cz.muni.fi.pb138.project.Interfaces.JobTypeManager;

import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class JobTypeManagerImpl implements JobTypeManager {

    private JobTypeDAO jobTypeDAO;

    /**
     * Stores new job type into database. Id for the new job type is automatically
     * generated and stored into id attribute.
     *
     * @param jobType JobType to be created
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void createJobType(JobType jobType) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates JobType in database.
     *
     * @param jobType updated JobType to be stored into database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void updateJobType(JobType jobType) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes JobType from database.
     *
     * @param id id of JobType to be deleted from db.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void deleteJobType(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns JobType with given id.
     *
     * @param id primary key of requested JobType.
     * @return JobType with given id or null if such JobType does not exist.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public JobType getJobType(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns list of all JobTypes in the database.
     *
     * @return list of all JobTypes in database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<JobType> getAllJobTypes() throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }
}
