/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busmanagement.StatisticObject;

/**
 *
 * @author Admin
 */
public class Statistic02 {
    private String licensePlate;
    private float gas_cost;
    private float fix_cost;
    private String work_day;

    public Statistic02(String licensePlate, float gas_cost, float fix_cost, String work_day) {
        this.licensePlate = licensePlate;
        this.gas_cost = gas_cost;
        this.fix_cost = fix_cost;
        this.work_day = work_day;
    }
    
    
    

    public String getLicensePlate() {
        return licensePlate;
    }

    public float getGas_cost() {
        return gas_cost;
    }

    public float getFix_cost() {
        return fix_cost;
    }

    public String getWork_day() {
        return work_day;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setGas_cost(float gas_cost) {
        this.gas_cost = gas_cost;
    }

    public void setFix_cost(float fix_cost) {
        this.fix_cost = fix_cost;
    }

    public void setWork_day(String work_day) {
        this.work_day = work_day;
    }
    
    
    
}
