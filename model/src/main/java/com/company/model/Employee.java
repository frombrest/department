package com.company.model;

import java.sql.Date;

/**
 * Created by frombrest on 24.2.17.
 */
public class Employee {
    private int id;
    private String full_name;
    private Date date_of_birth;
    private int department_id;
    private double salary;

    public Employee() {
    }

    public Employee(String full_name, Date date_of_birth, int department_id, double salary) {
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.department_id = department_id;
        this.salary = salary;
    }

    public Employee(int id, String full_name, Date date_of_birth, int department_id, double salary) {
        this.id = id;
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.department_id = department_id;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
