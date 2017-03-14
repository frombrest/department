package com.company.dao;

import com.company.model.Department;
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
 * with the HSQLDB base of departments (table "TW"."Department")
 *
 * class uses the Spring JDBC API and
 * and log4j2 library for debugging
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

@Repository(value = "departmentDAO")
public class DepartmentJdbcDAO implements DepartmentDAO {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(DepartmentJdbcDAO.class);

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
     * Method searches the department entity with the same id in table "TW"."Department"
     * @param id of the target department
     * @return department entity
     */
    public Department getById(int id) {
        Department department = null;
        try {
            department = jdbcTemplate.queryForObject(
                    "SELECT * FROM \"TW\".\"Department\" WHERE \"id\" = ?", new Object[]{id},
                    new RowMapper<Department>() {
                        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
                            Department department = new Department();
                            department.setId(resultSet.getInt("id"));
                            department.setName(resultSet.getString("name"));
                            return department;
                        }
                    }
            );
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Department with id=" + id + " not found. Result == null");
        }
        return department;
    }

    /**
     * Method returns a list of all departments from the table "TW"."Department"
     * @return list of all entity departments
     */
    public List<Department> getAll() {
        List<Department> listDepartment = null;
        try {
            listDepartment = jdbcTemplate.query(
                    "SELECT * FROM \"TW\".\"Department\";", new RowMapper<Department>() {
                        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
                            Department department = new Department();
                            department.setId(resultSet.getInt("id"));
                            department.setName(resultSet.getString("name"));
                            return department;
                        }
                    }
            );
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Departments not found. Result == null");
        }
        return listDepartment;
    }

    /**
     * Method remove department with the same id from the table "TW"."Department"
     * @param id of the deletable department
     */
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM \"TW\".\"Department\" WHERE \"id\"= ?;", id);
        logger.debug("Delete department with id=" + id);
    }

    /**
     * Method creates a department in the table "TW"."Department"
     * @param department entity of the created department
     */
    public void create(Department department) {
        jdbcTemplate.update("INSERT INTO \"TW\".\"Department\" (\"name\") VALUES (?);",
                department.getName());
        logger.debug("Create department: " + department.getName());
    }

    /**
     * Method makes changes to department in the table "TW"."Department"
     * @param department entity of the modified department
     */
    public void update(Department department) {
        jdbcTemplate.update("UPDATE \"TW\".\"Department\" SET \"name\"=? WHERE \"id\"=?;",
                department.getName(), department.getId());
        logger.debug("Update department: " + department.getName());
    }
}
