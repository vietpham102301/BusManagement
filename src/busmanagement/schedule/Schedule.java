/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busmanagement.schedule;

/**
 *
 * @author Admin
 */
public class Schedule {
    
    private int busNumber;

   
    private String name;
    private String startTime;
    private String endTime;
    private String workDay;
    private String licensePlate;
    private String startPoint;

   public Schedule() {
    }
    public Schedule(int busNumber, String name, String startTime, String endTime, String workDay, String licensePlate, String startPoint) {
        this.busNumber = busNumber;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDay = workDay;
        this.licensePlate = licensePlate;
        this.startPoint = startPoint;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getWorkDay() {
        return workDay;
    }
    public String getLicensePlate()
    {
        return licensePlate;
    }
    public void setLicensePlate(String license)
    {
        this.licensePlate = license;
    }
    public String getStartPoint()
    {
        return startPoint;
    }
    public void setStartPoint(String startPoint)
    {
        this.startPoint = startPoint;
    }
}
