/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busmanagement.employee;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Employee {
    private int empId;
    private String fName, lName, adress, position, sex, identityNum;
    private Date birthdate, startWorkingDate;
 
    private double salary;

    public Employee(int empId, String fName, String lName, String adress, String position, Date birthdate, Date startWorkingDate, String sex, double salary, String identityNum) {
        this.empId = empId;
        this.fName = fName;
       
        this.lName = lName;
        this.adress = adress;
        this.position = position;
        this.birthdate = birthdate;
        this.startWorkingDate = startWorkingDate;
        this.sex = sex;
        this.salary = salary;
        this.identityNum = identityNum;
    }

    public int getEmpId() {
        return empId;
    }

    public String getfName() {
        return fName;
    }


    public String getlName() {
        return lName;
    }

    public String getAdress() {
        return adress;
    }

    public String getPosition() {
        return position;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Date getStartWorkingDate() {
        return startWorkingDate;
    }

    public String getSex() {
        return sex;
    }

    public double getSalary() {
        return salary;
    }
    
    public String getIdentityNum()
    {
        return identityNum;
    }
}
