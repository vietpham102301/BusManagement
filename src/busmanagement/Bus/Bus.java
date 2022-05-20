/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busmanagement.Bus;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Bus {
    int busId, routeId, status;
    String licensePlate, busType;
    Date startUse;

    public Bus(int busId, int routeId, int status, String licensePlate, String busType, Date startUse) {
        this.busId = busId;
        this.routeId = routeId;
        this.status = status;
        this.licensePlate = licensePlate;
        this.busType = busType;
        this.startUse = startUse;
    }

    public int getBusId() {
        return busId;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getStatus() {
        return status;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBusType() {
        return busType;
    }

    public Date getStartUse() {
        return startUse;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public void setStartUse(Date startUse) {
        this.startUse = startUse;
    }
    
    
    
}
