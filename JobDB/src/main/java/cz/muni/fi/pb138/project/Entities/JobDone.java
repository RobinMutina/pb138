/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * representing JobDone class
 * @author Vladislav Malynych
 */
public class JobDone {
    private final Long NOT_INITIALISED_ID = -1L;
    private Long id = NOT_INITIALISED_ID;
    private Long userId = NOT_INITIALISED_ID;
    private Long jobTypeId = NOT_INITIALISED_ID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    /**
     * basic setter
     * @param id id
     * @throws IllegalAccessException thrown when id is already set
     * @throws IllegalArgumentException thrown when id is less than 0
     */
    public void setId(Long id) throws IllegalAccessException {
        if (this.id.equals(NOT_INITIALISED_ID)) {
            if(id < 0L) {
                throw new IllegalArgumentException("Id cannot be negative");
            }
            this.id = id;
        }else{
            throw new IllegalAccessException("id is already set");
        }
    }

    /**
     * basic getter
     * @return id
     */
    public long getId(){
        return id;
    }
    /**
     * basic setter
     * @param userId userId
     * @throws IllegalAccessException thrown when id is already set
     * @throws IllegalArgumentException thrown when id is less than 0
     */
    public void setUserId(Long userId){
        if (this.userId.equals(NOT_INITIALISED_ID)) {
            if(userId < 0L) {
                throw new IllegalArgumentException("Id cannot be negative");
            }
            this.userId = userId;
        }else{
            this.userId = userId;
        }
    }
    /**
     * basic getter
     * @return userId
     */
    public long getUserId(){
        return userId;
    }

    /**
     * basic setter
     * @param jobTypeId jobTypeId
     * @throws IllegalAccessException thrown when id is already set
     * @throws IllegalArgumentException thrown when id is less than 0
     */
    public void setJobTypeId(Long jobTypeId){
        if (this.jobTypeId.equals(NOT_INITIALISED_ID)) {
            if(jobTypeId < 0L) {
                throw new IllegalArgumentException("Id cannot be negative");
            }
            this.jobTypeId = jobTypeId;
        }else{
            this.jobTypeId = jobTypeId;
        }
    }
    /**
     * basic getter
     * @return jobTypeId
     */
    public long getJobTypeId(){
        return jobTypeId;
    }

    /**
     * basic setter
     * @param startTime startTime
     */
    public void setStrartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    /**
     * basic getter
     * @return startTime
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

    /**
     * basic setter
     * @param endTime endTime
     */
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }

    /**
     * basic getter
     * @return endTime
     */
    public LocalDateTime getEndTime(){
        return endTime;
    }

    /**
     * overridden toString method
     * @return String JobDone
     */
    @Override
    public String toString() {
        return "JobDone{" + "id=" + id + ", userId=" + userId + ", jobTypeId=" + jobTypeId + ", startTime=" + startTime.toString() + ", endTime=" + endTime.toString()+"}";
    }

    /**
     * overridden equals method
     * @param obj JobDone
     * @return true if equal false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobDone other = (JobDone) obj;
        if (obj != this && this.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    /**
     * overridden hashCode method
     * @return hash of JobDone
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
