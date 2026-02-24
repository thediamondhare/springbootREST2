package com.springbootREST.springbootREST.rest;

import com.springbootREST.springbootREST.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.springbootREST.springbootREST.service.EmployeeService;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private EmployeeService employeeService;

    private JsonMapper jsonMapper;

    // constructor injection
    @Autowired
    public EmployeeRestController (EmployeeService theEmployeeService, JsonMapper theJsonMapper) {
        employeeService = theEmployeeService;
        jsonMapper = theJsonMapper;
    }


    // mapping GET to read a single employee with given id
    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee == null) {
            throw new RuntimeException("Employee with id: " + employeeId + " not found");
        }

        return theEmployee;
    }

    //  mapping POST to add a new employee
    @PostMapping("")
    public Employee addEmployee(@RequestBody Employee theEmployee) {
        // just in case they pass in JSON an id set it to 0, this is to force a save of a new employee instead updating old one
        theEmployee.setId(0);

        Employee dbEmployee = employeeService.save(theEmployee);

        return dbEmployee;
    }

    //  mapping PUT to update an employee data
    @PutMapping("")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    //  mapping PATCH to patch an employee data
    @PatchMapping("/{employeeId}")
    public Employee patchEmployeeById(@PathVariable int employeeId, @RequestBody Map<String, Object> patchPayload) {
        Employee tempEmployee = employeeService.findById(employeeId);

        // throw error if employee tno found in db
        if ( tempEmployee == null ) {
            throw new RuntimeException("Employee with id: " + employeeId + " not found");
        }

        // throw error if sb want patch the id
        if ( patchPayload.containsKey("employeeId") ) {
            throw new RuntimeException("Employee id " + employeeId + " is not allowed in request body");
        }

        // patching employee
        Employee patchedEmployee = jsonMapper.updateValue(tempEmployee, patchPayload);
        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    // mapping DELETE  to delete an employee with given id
    @DeleteMapping("{employeeId}")
    public String removeEmployeeById(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);

        if ( tempEmployee == null ) {
            throw new RuntimeException("Employee with id: " + employeeId + " not found");
        }

        employeeService.deleteById(employeeId);

        return "Deleted employee with id " + employeeId;
    }

    // mapping to expose  /employees and return a list of employees
    @GetMapping("/all")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }


}
