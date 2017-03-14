package com.company.dao;

import com.company.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * This class implements methods for working
 * with the HSQLDB base of employees (table "TW"."Employee")
 *
 * class uses the Spring JDBC API and
 * and log4j2 library for debugging
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

@Repository(value = "employeeDAO")
public class EmployeeJdbcDAO implements EmployeeDAO {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(EmployeeJdbcDAO.class);

    /**
     * Field for injection Spring Jdbc Template bean
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * Method is intended for injection Spring Jdbc Template bean
     * @param jdbcTemplate target bean
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method searches the employee entity with the same id in table "TW"."Employee"
     * @param id of the target employee
     * @return employee entity
     */
    public Employee getById(int id) {
        Employee employee = null;
        try {
            employee = jdbcTemplate.queryForObject(
                    "SELECT * FROM \"TW\".\"Employee\" WHERE \"id\" = ?", new Object[]{id},
                    new RowMapper<Employee>() {
                        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                            Employee employee = new Employee();
                            employee.setId(resultSet.getInt("id"));
                            employee.setFull_name(resultSet.getString("full_name"));
                            employee.setDate_of_birth(resultSet.getDate("date_of_birth"));
                            employee.setDepartment_id(resultSet.getInt("department_id"));
                            employee.setSalary(resultSet.getDouble("salary"));
                            return employee;
                        }
                    }
            );
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Employee with id=" + id + " not found. Result == null");
        }
        return employee;
    }

    /**
     * Method returns a list of all employees from the the target department.
     * Search is in the table "TW"."Employee"
     * @param id of the target department
     * @return list of entity employees
     */
    public List<Employee> getByDepartmentId(int id) {
        List<Employee> listEmployee = null;
        try {
            listEmployee = jdbcTemplate.query(
                    "SELECT * FROM \"TW\".\"Employee\" WHERE \"department_id\" = ?",
                    new Object[]{id}, new RowMapper<Employee>() {
                        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                            Employee employee = new Employee();
                            employee.setId(resultSet.getInt("id"));
                            employee.setFull_name(resultSet.getString("full_name"));
                            employee.setDate_of_birth(resultSet.getDate("date_of_birth"));
                            employee.setDepartment_id(resultSet.getInt("department_id"));
                            employee.setSalary(resultSet.getDouble("salary"));
                            return employee;
                        }
                    }
            );
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Employees not found. Result == null");
        }
        return listEmployee;
    }

    /**
     * Method returns a list of all employees from the table "TW"."Employee"
     * @return list of all entity employees
     */
    public List<Employee> getAll() {
        List<Employee> listEmployee = null;
        try {
            listEmployee = jdbcTemplate.query(
                    "SELECT * FROM \"TW\".\"Employee\"", new RowMapper<Employee>() {
                        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                            Employee employee = new Employee();
                            employee.setId(resultSet.getInt("id"));
                            employee.setFull_name(resultSet.getString("full_name"));
                            employee.setDate_of_birth(resultSet.getDate("date_of_birth"));
                            employee.setDepartment_id(resultSet.getInt("department_id"));
                            employee.setSalary(resultSet.getDouble("salary"));
                            return employee;
                        }
                    }

            );
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Employees not found. Result == null");
        }
        return listEmployee;
    }

    /**
     * Method remove employee with the same id from the table "TW"."Employee"
     * @param id of the deletable employee
     */
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM \"TW\".\"Employee\" WHERE \"id\"= ?;", id);
        logger.debug("Delete employee with id=" + id);
    }

    /**
     * Method creates a employee in the table "TW"."Employee"
     * @param employee entity of the created employee
     */
    public void create(Employee employee) {
        jdbcTemplate.update("INSERT INTO \"TW\".\"Employee\" (\"full_name\", \"date_of_birth\", " +
                        "\"department_id\", \"salary\") VALUES (?, ?, ?, ?);", employee.getFull_name(),
                employee.getDate_of_birth().toString(), employee.getDepartment_id(), employee.getSalary());
        logger.debug("Create employee: " + employee.getFull_name());
    }

    /**
     * Method makes changes to employee in the table "TW"."Employee"
     * @param employee entity of the modified employee
     */
    public void update(Employee employee) {
        jdbcTemplate.update("UPDATE \"TW\".\"Employee\" SET \"full_name\"=?,\"date_of_birth\"=?, " +
                        "\"department_id\"=?, \"salary\"=? WHERE \"id\"=?;", employee.getFull_name(),
                employee.getDate_of_birth().toString(), employee.getDepartment_id(), employee.getSalary(),
                employee.getId());
        logger.debug("Update employee: " + employee.getFull_name());
    }

}
