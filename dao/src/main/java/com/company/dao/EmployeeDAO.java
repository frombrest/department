package com.company.dao;

import com.company.model.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */
public interface EmployeeDAO {
    public Employee getById(int id);
    public List<Employee> getByDepartmentId(int id);
    //public List<Employee> getByDateOfBirth(LocalDate date);
    public List<Employee> getAll();
    //public List<Employee> getByIntervalOfBirthDates(LocalDate dateFrom, LocalDate dateTo);
    public void delete(int id);
    public void create(Employee employee);
    public void update(Employee employee);
}
