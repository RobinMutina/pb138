/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Interfaces;

import cz.muni.fi.pb138.project.Entities.JobDone;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  JobDoneManager interface with the main operations
 *  for manipulating with a database getting adding modifying
 * @author Vladislav Malynych
 */
public interface JobDoneManager {
    
    /**
     * Creates new JobDone in the database.Id for the new user is automatically
     * generated and stored into id attribute.
     * @param jobDone JobDone that should be created
     * @throws ServiceFailureException when db operations fails
     */
    void createJobDone(JobDone jobDone) throws ServiceFailureException;
    
    /**
     * Updates JobDone in the database with a JobDone from a parameter
     * validates equality by id
     * @param jobDone update element
     * @throws ServiceFailureException when db operations fails
     */
    void updateJobDone(JobDone jobDone) throws ServiceFailureException;
    
    /**
     * Deletes JobDone with current id from a database
     * @param id id that should be deleted
     * @throws ServiceFailureException when db operations fails
     */
    void deleteJobDone(Long id) throws ServiceFailureException;
    
    /**
     * Finds and gets a JobDone from a database with current id
     * @param id id of the searched element
     * @return JobDone with given id or null if such does not exist.
     * @throws ServiceFailureException when db operations fails
     */
    JobDone getJobDoneById(Long id) throws ServiceFailureException;
    
    /**
     * Finds all JobDone elements at the database
     * @return returns List of all found jobDone elements
     * @throws ServiceFailureException when db operations fails
     */
    List<JobDone> getAllJobDone() throws ServiceFailureException;
    
    /**
     * Finds all JobDone elements for some specific user
     * searches user by his id
     * @param id id to be found
     * @return return current users list of JobDone elements
     * @throws ServiceFailureException when db operations fails
     */
    List<JobDone> getAllJobDoneByUser(Long id) throws ServiceFailureException;
    
    /**
     * Finds all JobDone elements for some specific user at some current time
     * @param id id of the searched user
     * @param startTime start searching time
     * @param endTime end of searching time
     * @return return List of JobDone elements for current user at a specified time period
     * @throws ServiceFailureException when db operations fails
     */
    List<JobDone> getAllJobDoneByUserAtTime(long id, LocalDateTime startTime, LocalDateTime endTime) throws ServiceFailureException;
    
    /**
     * Gets the total salary of specified user
     * @param id current user id
     * @return returns total salary
     * @throws ServiceFailureException when db operations fails
     */
    BigDecimal getUserTotalSalary(Long id) throws ServiceFailureException;
    
    /**
     * Gets specified user salary at some exact period of time
     * @param id user id
     * @param startTime start searching time
     * @param endTime end searching time
     * @return return total salary at a specified time
     * @throws ServiceFailureException when db operations fails
     */
    BigDecimal getUserSalaryAtTime(Long id, LocalDateTime startTime, LocalDateTime endTime) throws ServiceFailureException;
}
