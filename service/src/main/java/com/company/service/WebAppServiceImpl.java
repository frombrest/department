package com.company.service;

import com.company.model.Department;
import com.company.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Method for checking date format (yyyy-MM-dd)
     * @param date String with date
     * @return boolean value
     */
    private boolean dateChecker(String date) {
        Pattern p = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        Matcher m = p.matcher(date);
        return m.matches();
    }

    /**
     * Default Web-app URL
     * http://localhost:8080/departmentrest/
     */
    @Value("${service.protocol}://${service.host}:${service.port}/${service.prefix}/")
    private String APPURL;

    /**
     * Default department URL
     */
    @Value("${point.department}")
    private String DEPARTMENTPOINT;

    /**
     * Default employee URL
     */
    @Value("${point.employee}")
    private String EMPLOYEEPOINT;

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
        if (!this.dateChecker(dateOfBirth))
            throw new RestClientException("Bad date format");
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
                        throw new RestClientException("Bad date format");
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
                    throw new RestClientException("Bad date format");
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
                    throw new RestClientException("Bad date format");
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
    public void createDepartment(Department department) throws RestClientException {
        restTemplate.postForObject(APPURL + DEPARTMENTPOINT, department, Department.class);
    }

    /**
     * Method perform request to REST service to get department entity with the same id
     * @param id ID of the target department
     * @return Department entity
     */
    @Override
    public Department getDepartmentById(int id) throws RestClientException {
        return restTemplate.getForObject(APPURL + DEPARTMENTPOINT + "/" + id, Department.class);
    }

    /**
     * Method perform request to REST service to get an array of all departments
     * @return Array of departments entity
     */
    @Override
    public Department[] getDepartments() throws RestClientException {
        return restTemplate.getForObject(APPURL + DEPARTMENTPOINT, Department[].class);
    }

    /**
     * Method perform request to REST service for updating department
     * @param department entity of the modified department
     */
    @Override
    public void updateDepartment(Department department) throws RestClientException {
        restTemplate.put(APPURL + DEPARTMENTPOINT, department, Department.class);
    }

    /**
     * Method perform request to REST service for delete employee
     * @param id of the deletable employee
     */
    @Override
    public void deleteDepartmentById(int id) throws RestClientException {
        restTemplate.delete(APPURL + DEPARTMENTPOINT + "/" + id);
    }

    /**
     * Method perform request to REST service for create employee
     * @param employee entity of the created employee
     */
    @Override
    public void createEmployee(Employee employee) throws RestClientException {
        restTemplate.postForObject(APPURL + EMPLOYEEPOINT, employee, Employee.class);
    }

    /**
     * Method perform request to REST service to get employee entity with the same id
     * @param id ID of the target employee
     * @return employee entity
     */
    @Override
    public Employee getEmployeeById(int id) throws RestClientException {
        return restTemplate.getForObject(APPURL + EMPLOYEEPOINT + "/" + id, Employee.class);
    }

    /**
     * Method perform request to REST service to get an array of
     * employees of the target department
     *
     * @param id ID of the target department
     * @return array of emploees entity
     */
    @Override
    public Employee[] getEmployeesByDepartmentId(int id) throws RestClientException {
        return restTemplate.getForObject(APPURL + EMPLOYEEPOINT + "/?department=" + id, Employee[].class);
    }

    /**
     * Method perform request to REST service for updating employee
     * @param employee entity of the modified employee
     */
    @Override
    public void updateEmployee(Employee employee) throws RestClientException {
        restTemplate.put(APPURL + EMPLOYEEPOINT, employee, Employee.class);
    }

    /**
     * Method perform request to REST service for removal employee with the same id
     * @param id ID of the deletable employee
     */
    @Override
    public void deleteEmployeeById(int id) throws RestClientException {
        restTemplate.delete(APPURL + EMPLOYEEPOINT + "/" + id);
    }

}
