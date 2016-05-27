/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Entities;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Vladislav Malynych
 */
public class JobType {
    private final Long NOT_INITIALISED_ID = -1L;
    private Long id = NOT_INITIALISED_ID;
    private String name;
    private BigDecimal pricePerHour;
    
    public Long getId() {
        return id;
    }
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
     
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }
    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
    
    @Override
    public String toString() {
        return "JobType{" + "id=" + id + ", name=" + name + ", PricePerHour=" + pricePerHour + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobType other = (JobType) obj;
        if (obj != this && this.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
}
