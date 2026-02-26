package com.springbootREST.springbootREST.repositories;

import com.springbootREST.springbootREST.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// changing the default name of endpoint of repository to custom name
//@RepositoryRestResource(path="someemployees")
public interface  EmployeeRepository extends JpaRepository<Employee, Integer> {

}
