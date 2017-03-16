package com.company.service;

import com.company.model.Department;
import com.company.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class implements methods for working
 * with data used in functioning web application
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
@Service(value = "webappService")
public class WebAppServiceImpl implements WebAppService {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(WebAppServiceImpl.class);

    /**
     * Default Web-app URL
     */
    private final static String APPURL = "http://localhost:8080/departmentrest/";

    /**
     * Field for injection Spring REST Template bean
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Method returns a map of appropriate values id of the departments and the average salary
     * @return  Map{Key: Department id; Value: Average salary} for all departments
     */
    @Override
    public Map<Integer, Double> getAverageSalary() {
        Map<Integer, Double> result = new HashMap<>();
        DecimalFormatSymbols separatorSymbol = new DecimalFormatSymbols();
        separatorSymbol.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00", separatorSymbol);
        Department[] deps = this.getDepartments();
        for (Department department:deps){
            logger.debug("A.S. for Department with id: "+ department.getId());
            Employee[] empls = this.getEmployeesByDepartmentId(department.getId());
            double avsalary = 0;
            if(empls.length>0) {
                for (Employee employee : empls)
                    avsalary += employee.getSalary();
                avsalary /= empls.length;
                logger.debug("A.S. for Department "+ department.getId()+" is: " + new Double(df.format(avsalary)));
                result.put(department.getId(), new Double(df.format(avsalary)));
            } else {
                logger.debug("A.S. for Department "+ department.getId()+" is: 0.00");
                result.put(department.getId(),0.00);
            }
        }
        return result;
    }

    /**
     * Method return an array of employees of the target
     * department were born in the target date
     * @param departmentId ID of the target department
     * @param dateOfBirth target date
     * @return list of entity employees
     */
    @Override
    public List<Employee> searchEmployeesByDateOfBirth(int departmentId, String dateOfBirth) {
        List<Employee> result = new ArrayList<>();
        logger.debug("Show employees form department:" + departmentId + " with filter by date of birth:" + dateOfBirth);
        Employee[] employees = this.getEmployeesByDepartmentId(departmentId);
        for (Employee employee:employees){
            if (employee.getDate_of_birth().toString().equals(dateOfBirth)){
                result.add(employee);
            }
        }
        return result;
    }

    /**
     * Method return an array of employees of the target
     * department born between the dates
     * @param departmentId ID of the target department
     * @param dateFrom start date of period
     * @param dateTo date of end of period
     * @return list of entity employees
     */
    @Override
    public List<Employee> searchEmployeesByIntervalOfBirthdates(int departmentId, String dateFrom, String dateTo) {
        logger.debug("Show employees form department:" + departmentId + " with filter by interval of birth dates from:" + dateFrom + " to:"+ dateTo);
        List<Employee> result = new ArrayList<>();
        Employee[] employees = this.getEmployeesByDepartmentId(departmentId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date employerDate = new java.util.Date();
        Date toDate = new java.util.Date();
        Date fromDate = new java.util.Date();
        if ((dateFrom.length()==0) & (dateTo.length()!=0)){
            //born before date_to
            for (Employee employee:employees){
                try {
                    employerDate = sdf.parse(employee.getDate_of_birth().toString());
                    toDate = sdf.parse(dateTo);
                } catch (ParseException exc) {
                    logger.debug(exc);
                }

                if (employerDate.compareTo(toDate)<=0){
                    result.add(employee);
                }
            }
        } else if ((dateFrom.length()!=0) & (dateTo.length()==0)){
            //born after date_from
            for (Employee employee:employees){
                try {
                    employerDate = sdf.parse(employee.getDate_of_birth().toString());
                    fromDate = sdf.parse(dateFrom);
                } catch (ParseException exc) {
                    logger.debug(exc);
                }
                if (employerDate.compareTo(fromDate)>=0){
                    result.add(employee);
                }
            }
        } else {
            //born between date_from and date_to
            for (Employee employee:employees){
                try {
                    employerDate = sdf.parse(employee.getDate_of_birth().toString());
                    fromDate = sdf.parse(dateFrom);
                    toDate = sdf.parse(dateTo);
                } catch (ParseException exc) {
                    logger.debug(exc);
                }
                if ((employerDate.compareTo(fromDate)>=0) & (employerDate.compareTo(toDate)<=0)){
                    result.add(employee);
                }
            }
        }
        return result;
    }

    /**
     * Method perform request to REST service for create department
     * @param department entity of the created department
     */
    @Override
    public void createDepartment(Department department) {
        restTemplate.postForObject(APPURL + "department/", department, Department.class);
    }

    /**
     * Method perform request to REST service to get department entity with the same id
     * @param id ID of the target department
     * @return Department entity
     */
    @Override
    public Department getDepartmentById(int id) {
        return restTemplate.getForObject(APPURL + "department/" + id, Department.class);
    }

    /**
     * Method perform request to REST service to get an array of all departments
     * @return Array of departments entity
     */
    @Override
    public Department[] getDepartments() {
        return restTemplate.getForObject(APPURL + "department", Department[].class);
    }

    /**
     * Method perform request to REST service for updating department
     * @param department entity of the modified department
     */
    @Override
    public void updateDepartment(Department department) {
        restTemplate.put(APPURL + "department/", department, Department.class);
    }

    /**
     * Method perform request to REST service for delete employee
     * @param id of the deletable employee
     */
    @Override
    public void deleteDepartmentById(int id) {
        restTemplate.delete(APPURL + "department/" + id);
    }

    /**
     * Method perform request to REST service for create employee
     * @param employee entity of the created employee
     */
    @Override
    public void createEmployee(Employee employee) {
        restTemplate.postForObject(APPURL + "employee/", employee, Employee.class);
    }

    /**
     * Method perform request to REST service to get employee entity with the same id
     * @param id ID of the target employee
     * @return employee entity
     */
    @Override
    public Employee getEmployeeById(int id) {
        return restTemplate.getForObject(APPURL + "employee/"+id, Employee.class);
    }

    /**
     * Method perform request to REST service to get an array of
     * employees of the target department
     *
     * @param id ID of the target department
     * @return array of emploees entity
     */
    @Override
    public Employee[] getEmployeesByDepartmentId(int id) {
        return restTemplate.getForObject(APPURL + "employee/?department=" + id, Employee[].class);
    }

    /**
     * Method perform request to REST service for updating employee
     * @param employee entity of the modified employee
     */
    @Override
    public void updateEmployee(Employee employee) {
        restTemplate.put(APPURL + "employee/", employee, Employee.class);
    }

    /**
     * Method perform request to REST service for removal employee with the same id
     * @param id ID of the deletable employee
     */
    @Override
    public void deleteEmployeeById(int id) {
        restTemplate.delete(APPURL + "employee/" + id);
    }

}
