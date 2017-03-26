package com.company.model;

import java.sql.Date;

/**
 * Class describes the entity of the employee
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
public class Employee {

    /**
     * Field for storing the id of the employee
     */
    private int id;

    /**
     * Field for storing the full name of the employee
     */
    private String full_name;

    /**
     * Field for storing the date of birth of the employee
     */
    private Date date_of_birth;

    /**
     * Field for storing the department id of the employee
     */
    private int department_id;

    /**
     * Field for storing the salary of the employee
     */
    private double salary;

    /**
     * Default object constructor
     */
    public Employee() {
    }

    /**
     * Parameterized object constructor
     * @param full_name Full name of the employee
     * @param date_of_birth Date of birth of the employee
     * @param department_id Department id of the employee
     * @param salary Salary of the employee
     */
    public Employee(String full_name, Date date_of_birth, int department_id, double salary) {
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.department_id = department_id;
        this.salary = salary;
    }

    /**
     * Parameterized object constructor
     * @param id ID of the employee
     * @param full_name Full name of the employee
     * @param date_of_birth Date of birth of the employee
     * @param department_id Department id of the employee
     * @param salary Salary of the employee
     */
    public Employee(int id, String full_name, Date date_of_birth, int department_id, double salary) {
        this.id = id;
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.department_id = department_id;
        this.salary = salary;
    }

    /**
     * Method for obtaining the employee id
     * @return ID of the employee
     */
    public int getId() {
        return id;
    }

    /**
     * Method for changing the employee id
     * @param id ID of the employee
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method for obtaining the employee full name
     * @return Full name of the employee
     */
    public String getFull_name() {
        return full_name;
    }

    /**
     * Method for changing the employee full name
     * @param full_name Full name of the employee
     */
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    /**
     * Method for obtaining the employee date of birth
     * @return Date of birth of the employee
     */
    public Date getDate_of_birth() {
        return date_of_birth;
    }

    /**
     * Method for changing the employee date of birth
     * @param date_of_birth  Date of birth of the employee
     */
    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    /**
     * Method for obtaining the employee department id
     * @return Department id of the employee
     */
    public int getDepartment_id() {
        return department_id;
    }

    /**
     * Method for changing the employee department id
     * @param department_id Department id of the employee
     */
    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    /**
     * Method for obtaining the employee salary
     * @return Salary of the employee
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Method for changing the employee salary
     * @param salary Salary of the employee
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Method for comparing objects of this type
     * @param o the object for comparison
     * @return true if the objects are identical
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (getId() != employee.getId()) return false;
        if (getDepartment_id() != employee.getDepartment_id()) return false;
        if (Double.compare(employee.getSalary(), getSalary()) != 0) return false;
        if (getFull_name() != null ? !getFull_name().equals(employee.getFull_name()) : employee.getFull_name() != null)
            return false;
        return getDate_of_birth() != null ? getDate_of_birth().equals(employee.getDate_of_birth()) : employee.getDate_of_birth() == null;
    }

    /**
     * Method to get the hash code of the object
     * @return int value of the hash code of the object
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        result = 31 * result + (getFull_name() != null ? getFull_name().hashCode() : 0);
        result = 31 * result + (getDate_of_birth() != null ? getDate_of_birth().hashCode() : 0);
        result = 31 * result + getDepartment_id();
        temp = Double.doubleToLongBits(getSalary());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Method to get a string representation of the object
     * @return a string description of the object
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", full_name='" + full_name + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", department_id=" + department_id +
                ", salary=" + salary +
                '}';
    }

}
