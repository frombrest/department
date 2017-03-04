package com.company.service;

import com.company.model.Department;
import com.company.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frombrest on 28.2.17.
 */

@Service(value = "webappService")
public class WebAppServiceImpl implements WebAppService {

    private final static Logger logger = LogManager.getLogger(WebAppServiceImpl.class);

    @Override
    public Map<Integer, String> getAverageSalary() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        RestTemplate restTemplate = new RestTemplate();
        DecimalFormat df = new DecimalFormat("#.##");
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Department[] deps = restTemplate.getForObject("http://localhost:8080/departmentrest/department", Department[].class);
        for (Department department:deps){
            logger.debug("A.S. for Department with id: "+ department.getId());
            Employee[] empls = restTemplate.getForObject("http://localhost:8080/departmentrest/employee/?department=" + department.getId(), Employee[].class);
            double avsalary = 0;
            if(empls.length>0) {
                for (Employee employee : empls)
                    avsalary += employee.getSalary();
                avsalary /= empls.length;
                logger.debug("A.S. for Department "+ department.getId()+" is: "+df.format(avsalary));
                result.put(department.getId(),df.format(avsalary));
            } else {
                logger.debug("A.S. for Department "+ department.getId()+" is: 0,00");
                result.put(department.getId(),"0,00");
            }
        }
        return result;
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(int id) {
        return null;
    }
}
