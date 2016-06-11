package cz.muni.fi.pb138.project.Impl;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;
import cz.muni.fi.pb138.project.Exceptions.ValidationException;
import cz.muni.fi.pb138.project.Impl.DataAccess.JobDoneDAO;
import cz.muni.fi.pb138.project.Interfaces.JobDoneManager;

import javax.xml.parsers.ParserConfigurationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * implementation of JobDoneManager interface
 * @author Martin Sevcik
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
        try {
            jobDoneDAO.createJobDone(jobDone);
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Creating jobDone failed.", ex);
        }
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
        try {
            jobDoneDAO.updateJobDone(jobDone);
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Updating jobDone failed.", ex);
        }
    }

    /**
     * Deletes JobDone with current id from a database
     *
     * @param id id that should be deleted
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public void deleteJobDone(Long id) throws ServiceFailureException {
        try {
            if (id == null || id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            jobDoneDAO.deleteJobDone(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Deleting jobDone failed.", ex);
        }
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
        try {
            if (id == null || id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            return jobDoneDAO.getJobDoneById(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Getting jobDone failed.", ex);
        }
    }

    /**
     * Finds all JobDone elements at the database
     *
     * @return returns List of all found jobDone elements
     * @throws ServiceFailureException when db operations fails
     */
    @Override
    public List<JobDone> getAllJobDone() throws ServiceFailureException {
        try {
            return jobDoneDAO.getAllJobDone();
        } catch (ServiceFailureException ex){
            throw new ServiceFailureException("Getting all jobDone failed.", ex);
        }
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
    public List<JobDone> getAllJobDoneByUserId(Long id) throws ServiceFailureException {
        try {
            if (id == null || id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            return jobDoneDAO.getAllJobDoneByUserId(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Getting all jobDone failed.", ex);
        }
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
    public List<JobDone> getAllJobDoneByUserAtTime(Long id, LocalDateTime startTime, LocalDateTime endTime) throws ServiceFailureException {
        try {
            if (id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            if (startTime == null){
                throw new IllegalArgumentException("startTime is null");
            }

            if (endTime == null){
                throw new IllegalArgumentException("endTime is null");
            }

            return jobDoneDAO.getAllJobDoneByUserAtTime(id, startTime, endTime);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Getting all jobDone failed.", ex);
        }
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
        try {
            if (id == null || id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            return jobDoneDAO.getUserTotalSalary(id);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Getting all jobDone failed.", ex);
        }
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
        try {
            if (id < 0){
                throw new IllegalArgumentException("id is invalid");
            }

            if (startTime == null){
                throw new IllegalArgumentException("startTime is null");
            }

            if (endTime == null){
                throw new IllegalArgumentException("endTime is null");
            }

            return jobDoneDAO.getUserSalaryAtTime(id, startTime, endTime);
        } catch (IllegalArgumentException | ServiceFailureException ex){
            throw new ServiceFailureException("Getting all jobDone failed.", ex);
        }
    }
}
