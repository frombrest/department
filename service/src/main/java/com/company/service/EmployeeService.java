package com.company.service;

import com.company.model.Employee;
import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */
public interface EmployeeService {

    public List<Employee> getAllEmployees();
    public List<Employee> getEmployeesByDepartmentId(int id);
    public Employee getEmployeeById(int id);
    public void addEmployee(Employee employee);
    public void updateEmployee(Employee employee);
    public void deleteEmployee(int id);
}