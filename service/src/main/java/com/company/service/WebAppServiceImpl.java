package com.company.service;

import com.company.model.Department;
import com.company.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by frombrest on 28.2.17.
 */

@Service(value = "webappService")
public class WebAppServiceImpl implements WebAppService {

    private final static Logger logger = LogManager.getLogger(WebAppServiceImpl.class);
    private final static String APPURL = "http://localhost:8080/departmentrest/";
    private RestTemplate restTemplate = new RestTemplate();

    public WebAppServiceImpl() {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public Map<Integer, Double> getAverageSalary() {
        Map<Integer, Double> result = new HashMap<>();
        DecimalFormatSymbols separatorSymbol = new DecimalFormatSymbols();
        separatorSymbol.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00", separatorSymbol);
        Department[] deps = restTemplate.getForObject(APPURL + "department", Department[].class);
        for (Department department:deps){
            logger.debug("A.S. for Department with id: "+ department.getId());
            Employee[] empls = restTemplate.getForObject(APPURL + "employee/?department=" + department.getId(), Employee[].class);
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

    @Override
    public List<Employee> searchEmployeesByDateOfBirth(int departmentId, String dateOfBirth) {
        List<Employee> result = new ArrayList<>();
        logger.debug("Show employees form department:" + departmentId + " with filter by date of birth:" + dateOfBirth);
        Employee[] employees = restTemplate.getForObject(APPURL + "employee/?department=" + departmentId, Employee[].class);
        for (Employee employee:employees){
            if (employee.getDate_of_birth().toString().equals(dateOfBirth)){
                result.add(employee);
            }
        }
        return result;
    }

    @Override
    public List<Employee> searchEmployeesByIntervalOfBirthdates(int departmentId, String dateFrom, String dateTo) {
        logger.debug("Show employees form department:" + departmentId + " with filter by interval of birth dates from:" + dateFrom + " to:"+ dateTo);
        List<Employee> result = new ArrayList<>();
        Employee[] employees = restTemplate.getForObject(APPURL + "employee/?department=" + departmentId, Employee[].class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date employerDate = new java.util.Date();
        Date toDate = new java.util.Date();
        Date fromDate = new java.util.Date();
        if ((dateFrom.length()==0) & (dateTo.length()!=0)){
            //все работники старше чем date_to
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
            //все работники младше чем date_from
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
            //все работники в промежутке от date_from до date_to
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

    @Override
    public void createDepartment(Department department) {
        logger.debug("New department: " + department.getName());
        restTemplate.postForObject(APPURL + "department/", department, Department.class);
    }

    @Override
    public Department getDepartmentById(int id) {
        return restTemplate.getForObject(APPURL + "department/" + id, Department.class);
    }

    @Override
    public Department[] getDepartments() {
        return restTemplate.getForObject(APPURL + "department", Department[].class);
    }

    @Override
    public void updateDepartment(Department department) {
        logger.debug("Edit department with id: " + department.getId() + " and new name: " + department.getName());
        restTemplate.put(APPURL + "department/", department, Department.class);
    }

    @Override
    public void deleteDepartmentById(int id) {
        logger.debug("Delete department with id:" + id);
        restTemplate.delete(APPURL + "department/" + id);
    }

    @Override
    public void createEmployee(Employee employee) {
        logger.debug("Add employee with name: " + employee.getFull_name());
        restTemplate.postForObject(APPURL + "employee/", employee, Employee.class);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return restTemplate.getForObject(APPURL + "employee/"+id, Employee.class);
    }

    @Override
    public Employee[] getEmployeesByDepartmentId(int id) {
        logger.debug("Get employees form department with id:" + id);
        return restTemplate.getForObject(APPURL + "employee/?department=" + id, Employee[].class);
    }

    @Override
    public void updateEmployee(Employee employee) {
        logger.debug("Edit employee with id:" + employee.getId());
        restTemplate.put(APPURL + "employee/", employee, Employee.class);
    }

    @Override
    public void deleteEmployeeById(int id) {
        logger.debug("Delete employee with id:" + id);
        restTemplate.delete(APPURL + "employee/" + id);
    }


}
