/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calendar;

import java.time.LocalDate;

/**
 *
 * @author Admin
 * 
 */

enum Months{
    January,
    February,
    March, 
    April,
    May, 
    June,
    July,
    August,
    September,
    October,
    November,
    December
}

public class Calendar {
    private int day, month, year;
    
    public static void main(String[] args)
    {
        LocalDate lc = LocalDate.of(2020, 1, 12);
        System.out.println(lc.getMonth());
    }

    public Calendar(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
    
}
