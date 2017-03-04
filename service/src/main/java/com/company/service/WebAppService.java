package com.company.service;

import com.company.model.Employee;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by frombrest on 28.2.17.
 */


public interface WebAppService {

    public Map<Integer, String> getAverageSalary();
    public List<Employee> getEmployeesByDepartmentId(int id);
}
