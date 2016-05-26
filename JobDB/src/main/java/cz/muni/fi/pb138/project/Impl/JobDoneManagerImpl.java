package cz.muni.fi.pb138.project.Impl;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Impl.DataAccess.JobDoneDAO;
import cz.muni.fi.pb138.project.Interfaces.JobDoneManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by martin on 26.5.2016.
 */
public class JobDoneManagerImpl implements JobDoneManager {

    private JobDoneDAO jobDoneDAO;

    /**
     * Creates new JobDone in the database.Id for the new user is automatically
     * generated and stored into id attribute.
     *
     * @param jobDone JobDone that should be created
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void createJobDone(JobDone jobDone) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates JobDone in the database with a JobDone from a parameter
     * validates equality by id
     *
     * @param jobDone update element
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void updateJobDone(JobDone jobDone) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes JobDone with current id from a database
     *
     * @param id id that should be deleted
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void deleteJobDone(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Finds and gets a JobDone from a database with current id
     *
     * @param id id of the searched element
     * @return JobDone with given id or null if such does not exist.
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public JobDone getJobDoneById(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Finds all JobDone elements at the database
     *
     * @return returns List of all found jobDone elements
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<JobDone> getAllJobDone() throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Finds all JobDone elements for some specific user
     * searches user by his id
     *
     * @param id id to be found
     * @return return current users list of JobDone elements
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<JobDone> getAllJobDoneByUser(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Finds all JobDone elements for some specific user at some current time
     *
     * @param id        id of the searched user
     * @param startTime start searching time
     * @param endTime   end of searching time
     * @return return List of JobDone elements for current user at a specified time period
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<JobDone> getAllJobDoneByUserAtTime(long id, LocalDateTime startTime, LocalDateTime endTime) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the total salary of specified user
     *
     * @param id current user id
     * @return returns total salary
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public BigDecimal getUserTotalSalary(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets specified user salary at some exact period of time
     *
     * @param id        user id
     * @param startTime start searching time
     * @param endTime   end searching time
     * @return return total salary at a specified time
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public BigDecimal getUserSalaryAtTime(Long id, LocalDateTime startTime, LocalDateTime endTime) throws ServiceFailureException {
        throw new UnsupportedOperationException();
    }
}
