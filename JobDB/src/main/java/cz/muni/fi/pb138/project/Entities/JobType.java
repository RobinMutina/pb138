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
    private Long id;
    private String name;
    private BigDecimal pricePerHour;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
