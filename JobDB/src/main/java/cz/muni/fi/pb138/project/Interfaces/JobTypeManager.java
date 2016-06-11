/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Interfaces;

import cz.muni.fi.pb138.project.Entities.JobType;
import cz.muni.fi.pb138.project.Exceptions.ServiceFailureException;

import java.util.List;

/**
 * JobTypeManager interface with the main operations
 * for manipulating with a database getting adding modifying
 * @author Vladislav Malynych
 */
public interface JobTypeManager {
    /**
     * Stores new job type into database. Id for the new job type is automatically
     * generated and stored into id attribute.
     * 
     * @param jobType JobType to be created
     * @throws ServiceFailureException when db operations fails
     */
    void createJobType(JobType jobType) throws ServiceFailureException;
    
    /**
     * Updates JobType in database.
     * 
     * @param jobType updated JobType to be stored into database.
     * @throws ServiceFailureException when db operations fails
     */
    void updateJobType(JobType jobType) throws ServiceFailureException;
    
    /**
     * Deletes JobType from database. 
     * 
     * @param id id of JobType to be deleted from db.
     * @throws ServiceFailureException when db operations fails
     */
    void deleteJobType(Long id) throws ServiceFailureException;

    /**
     * Returns JobType with given id. 
     * 
     * @param id primary key of requested JobType.
     * @return JobType with given id or null if such JobType does not exist.
     * @throws ServiceFailureException when db operations fails
     */
    JobType getJobType(Long id) throws ServiceFailureException;
    
    /**
     * Returns list of all JobTypes in the database.
     * 
     * @return list of all JobTypes in database.
     * @throws ServiceFailureException when db operations fails
     */
    List<JobType> getAllJobTypes() throws ServiceFailureException;
}
