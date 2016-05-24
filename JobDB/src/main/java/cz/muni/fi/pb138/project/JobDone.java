/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Vladislav Malynych
 */
public class JobDone {
    private Long id;
    private Long userId;
    private Long jobTypeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public void setId(Long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public long getUserId(){
        return userId;
    }
    
    public void setJobTypeId(Long jobTypeId){
        this.jobTypeId = jobTypeId;
    }
    public long getJobTypeId(){
        return jobTypeId;
    }
    
    public void setStrartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }
    
    @Override
    public String toString() {
        return "JobDone{" + "id=" + id + ", userId=" + userId + ", jobTypeId=" + jobTypeId + ", startTime=" + startTime.toString() + ", endTime=" + endTime.toString()+"}";
    }

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
