package cz.muni.fi.pb138.project.Impl;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Impl.DataAccess.JobTypeDAO;
import cz.muni.fi.pb138.project.Interfaces.JobTypeManager;

import java.util.List;

/**
 * implementation of JobTypeManager interface
 * @author Martin Sevcik
 */
public class JobTypeManagerImpl implements JobTypeManager {

    private JobTypeDAO jobTypeDAO = new JobTypeDAO();

    /**
     * Stores new job type into database. Id for the new job type is automatically
     * generated and stored into id attribute.
     *
     * @param jobType JobType to be created
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void createJobType(JobType jobType) throws ServiceFailureException {
        try{
            jobTypeDAO.createJobType(jobType);
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Creation of jobType failed.", ex);
        }
    }

    /**
     * Updates JobType in database.
     *
     * @param jobType updated JobType to be stored into database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void updateJobType(JobType jobType) throws ServiceFailureException {
        try{
            jobTypeDAO.updateJobType(jobType);
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Update of jobType failed.", ex);
        }
    }

    /**
     * Deletes JobType from database.
     *
     * @param id id of JobType to be deleted from db.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void deleteJobType(Long id) throws ServiceFailureException {
        try{
            if (id == null || id < 0) {
                throw new IllegalArgumentException("id is invalid.");
            }

            jobTypeDAO.deleteJobType(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Deletion of jobType failed.", ex);
        }
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
        try{
            if (id == null || id < 0) {
                throw new IllegalArgumentException("id is invalid.");
            }

            return jobTypeDAO.getJobType(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Creation of jobType failed.", ex);
        }
    }

    /**
     * Returns list of all JobTypes in the database.
     *
     * @return list of all JobTypes in database.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<JobType> getAllJobTypes() throws ServiceFailureException {
        try{
            return jobTypeDAO.getAllJobTypes();
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Creation of jobType failed.", ex);
        }
    }
}
