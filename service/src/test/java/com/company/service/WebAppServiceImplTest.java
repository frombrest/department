package com.company.service;

import com.company.model.Department;
import com.company.model.Employee;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import java.sql.Date;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by frombrest on 9.3.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-webservice.xml"})
public class WebAppServiceImplTest {

    private static final String RawDataDepartment6 = "{\"id\":6,\"name\":\"Отдел номер 6\"}";
    private static final String RawDataDepartmentList = "[{\"id\":6,\"name\":\"Отдел номер 6\"}]";
    private static final String RawDataEmployee6 = "{\"id\":6,\"full_name\":\"Михаленя Андрей Сергеевич\",\"date_of_birth\":\"1989-02-24\",\"department_id\":2,\"salary\":475.14}";
    private static final String RawDataEmployees = "[{\"id\":0,\"full_name\":\"Михаленя Андрей Сергеевич\",\"date_of_birth\":\"1989-02-24\",\"department_id\":2,\"salary\":475.14},{\"id\":1,\"full_name\":\"Парфенюк Александр Иванович\",\"date_of_birth\":\"1990-11-19\",\"department_id\":2,\"salary\":535.17},{\"id\":2,\"full_name\":\"Тунчик Вадим Геннадьевич\",\"date_of_birth\":\"1992-03-10\",\"department_id\":2,\"salary\":414.26},{\"id\":3,\"full_name\":\"Савосин Виталий Юрьевич\",\"date_of_birth\":\"1987-09-13\",\"department_id\":1,\"salary\":535.17},{\"id\":4,\"full_name\":\"Пивоварчук Вячеслав Владимирович\",\"date_of_birth\":\"1992-03-14\",\"department_id\":1,\"salary\":454.26},{\"id\":5,\"full_name\":\"Былинский Александр Юрьевич\",\"date_of_birth\":\"1987-05-17\",\"department_id\":0,\"salary\":554.22},{\"id\":6,\"full_name\":\"Дацкевич Олег Александрович\",\"date_of_birth\":\"1987-09-13\",\"department_id\":0,\"salary\":564.27},{\"id\":7,\"full_name\":\"Кондрахин Виталий Юрьевич\",\"date_of_birth\":\"1987-06-24\",\"department_id\":0,\"salary\":524.05},{\"id\":8,\"full_name\":\"Семёнов Кирилл Алексеевич\",\"date_of_birth\":\"1992-10-06\",\"department_id\":0,\"salary\":414.05},{\"id\":9,\"full_name\":\"Петренко Кирилл Александрович\",\"date_of_birth\":\"1989-09-16\",\"department_id\":3,\"salary\":673.45},{\"id\":10,\"full_name\":\"Тюшкевич Александр Григорьевич\",\"date_of_birth\":\"1984-05-12\",\"department_id\":3,\"salary\":523.45},{\"id\":11,\"full_name\":\"Цедрик Сергей Михайлович\",\"date_of_birth\":\"1958-02-10\",\"department_id\":3,\"salary\":523.45},{\"id\":12,\"full_name\":\"Ливак Андрей Вячеславович\",\"date_of_birth\":\"1974-01-17\",\"department_id\":3,\"salary\":423.45},{\"id\":13,\"full_name\":\"Строкач Елена Владимировна\",\"date_of_birth\":\"1962-12-14\",\"department_id\":4,\"salary\":693.45},{\"id\":14,\"full_name\":\"Селивончик Валенина Александровна\",\"date_of_birth\":\"1969-04-16\",\"department_id\":4,\"salary\":723.45},{\"id\":15,\"full_name\":\"Рябова Александра Сергеевна\",\"date_of_birth\":\"1989-11-18\",\"department_id\":5,\"salary\":533.45},{\"id\":16,\"full_name\":\"Новик Светлана Владимировна\",\"date_of_birth\":\"1985-10-21\",\"department_id\":5,\"salary\":535.45},{\"id\":17,\"full_name\":\"Иовлев Владимир Леонидович\",\"date_of_birth\":\"1981-05-25\",\"department_id\":5,\"salary\":575.45}]";
    private static final String RawDataDepartment = "[{\"id\":0,\"name\":\"Системной интеграции\"},{\"id\":1,\"name\":\"Систем видеонаблюдения и контроля доступа\"},{\"id\":2,\"name\":\"Электромонтажных работ\"},{\"id\":3,\"name\":\"ПТО\"},{\"id\":4,\"name\":\"Бухгалтерия\"},{\"id\":5,\"name\":\"Снабжения\"}]";
    Employee employee = new Employee("Test Name",Date.valueOf("1989-05-05"),6,545.58);
    Department department = new Department("Test Department");

    @Autowired
    private WebAppServiceImpl webAppService;
    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void prepare() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void getAverageSalary() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/department"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataDepartmentList, MediaType.APPLICATION_JSON_UTF8));
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/?department=6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataEmployees, MediaType.APPLICATION_JSON_UTF8));
                Assert.assertTrue(webAppService.getAverageSalary().get(6)==537.54);
        mockServer.verify();
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void searchEmployeesByDateOfBirth() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/?department=6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataEmployees, MediaType.APPLICATION_JSON_UTF8));
        Assert.assertTrue(webAppService.searchEmployeesByDateOfBirth(6, "1987-09-13").size()==2);
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void searchEmployeesByIntervalOfBirthdates() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/?department=6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataEmployees, MediaType.APPLICATION_JSON_UTF8));
        Assert.assertTrue(webAppService.searchEmployeesByIntervalOfBirthdates(6,"", "1987-06-24" ).size()==9);
        mockServer.verify();
        mockServer.reset();

        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/?department=6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataEmployees, MediaType.APPLICATION_JSON_UTF8));
        Assert.assertTrue(webAppService.searchEmployeesByIntervalOfBirthdates(6,"1987-06-24", "" ).size()==10);
        mockServer.verify();
        mockServer.reset();

        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/?department=6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataEmployees, MediaType.APPLICATION_JSON_UTF8));
        Assert.assertTrue(webAppService.searchEmployeesByIntervalOfBirthdates(6,"1987-06-24", "1987-06-24" ).size()==1);
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void createDepartment() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/department/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON_UTF8));
        webAppService.createDepartment(department);
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void getDepartmentById() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/department/6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataDepartment6, MediaType.APPLICATION_JSON_UTF8));
        Assert.assertNotNull(webAppService.getDepartmentById(6));
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void getDepartments() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/department"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataDepartment, MediaType.APPLICATION_JSON_UTF8));
        Department[] test =  webAppService.getDepartments();
        for (Department dep:test) {
            Assert.assertNotNull(dep);}
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void updateDepartment() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/department/"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON_UTF8));
        webAppService.updateDepartment(department);
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void deleteDepartmentById() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/department/6"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON_UTF8));
        webAppService.deleteDepartmentById(6);
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void createEmployee() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON_UTF8));
        webAppService.createEmployee(employee);
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void getEmployeeById() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataEmployee6, MediaType.APPLICATION_JSON_UTF8));
        Assert.assertNotNull(webAppService.getEmployeeById(6));
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void getEmployeesByDepartmentId() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/?department=6"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(RawDataEmployees, MediaType.APPLICATION_JSON_UTF8));
        Assert.assertNotNull(webAppService.getEmployeesByDepartmentId(6));
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void updateEmployee() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON_UTF8));
        webAppService.updateEmployee(employee);
        mockServer.verify();
        mockServer.reset();
    }

    @Test
    public void deleteEmployeeById() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/departmentrest/employee/6"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON_UTF8));
        webAppService.deleteEmployeeById(6);
        mockServer.verify();
        mockServer.reset();
    }

}