/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busmanagement.StatisticObject;

/**
 *
 * @author Admin
 */
public class Statistic01 {
    private String licensePlate;
    private String ticketType;
    private int ticketSold;
    private String workDay;

    public Statistic01(String licensePlate, String ticketType, int ticketSold, String workDay) {
        this.licensePlate = licensePlate;
        this.ticketType = ticketType;
        this.ticketSold = ticketSold;
        this.workDay = workDay;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getTicketType() {
        return ticketType;
    }

    public int getTicketSold() {
        return ticketSold;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setTicketSold(int ticketSold) {
        this.ticketSold = ticketSold;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }
    
    
    
}
