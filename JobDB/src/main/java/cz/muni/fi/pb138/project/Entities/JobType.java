/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.project.Entities;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * representing JobType class
 * @author Vladislav Malynych
 */
public class JobType {
    private final Long NOT_INITIALISED_ID = -1L;
    private Long id = NOT_INITIALISED_ID;
    private String name;
    private BigDecimal pricePerHour;

    /**
     * basic getter
     * @return id
     */
    public Long getId() {
        return id;
    }
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
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * basic setter
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * basic getter
     * @return pricePerHour
     */
    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    /**
     * basic setter
     * @param pricePerHour pricePerHour
     */
    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    /**
     * overridden toString method
     * @return String JobType
     */
    @Override
    public String toString() {
        return "JobType{" + "id=" + id + ", name=" + name + ", PricePerHour=" + pricePerHour + "}";
    }

    /**
     * overridden equals method
     * @param obj JobType
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
        final JobType other = (JobType) obj;
        if (obj != this && this.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    /**
     * overridden hashCode method
     * @return hash of JobType
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
}
