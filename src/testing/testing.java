/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class testing {
    
    private static int getCurrentWeek() {
    LocalDate date = LocalDate.of(2022, 3, 31);
    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    return date.get(weekFields.weekOfWeekBasedYear());
}
    
    public static void main(String args[])
    {
        
        LocalDate currentdate = LocalDate.now();
         DayOfWeek dayOfWeek
            = DayOfWeek.from(currentdate);
        System.out.println(getCurrentWeek());
    }

}
