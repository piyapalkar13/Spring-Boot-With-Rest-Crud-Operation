package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmpRepository;

@RestController
@RequestMapping("api/v1")
public class EmployeeController {

	@Autowired
	private EmpRepository repo;


	//get All Employees data
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return repo.findAll();
	}


	//store Employee data
	@PostMapping("/saveEmp")
	public Employee createEmp(@Valid @RequestBody Employee e)
	{
		return repo.save(e);
	}

	//get Employee record by ID

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmpById(@PathVariable("id") Long eid)throws ResourceNotFoundException
	{
		Employee e=repo.findById(eid).orElseThrow(()->new ResourceNotFoundException("Employee not found this id :"+eid));

		return  ResponseEntity.ok().body(e);
	}

	//update employee
	@RequestMapping("/emp/{id}")
	public ResponseEntity<Employee>	updateEmp(@PathVariable("id") Long eid,@Valid @RequestBody Employee e)throws ResourceNotFoundException
	{
		Employee emp=repo.findById(eid).orElseThrow(()->new ResourceNotFoundException("Employee not found this id :"+eid));

		emp.setEmailId(e.getEmailId());
		emp.setFname(e.getFname());
		emp.setLname(e.getLname());

		final Employee updatedEmp=repo.save(emp);
		
		return ResponseEntity.ok(updatedEmp);
	}

	//delete record by id
	@DeleteMapping("/deleteEmp/{id}")
	public Map<String, Boolean> deleteEmp(@PathVariable("id") Long eid)throws ResourceNotFoundException
	{
	
		Employee emp=repo.findById(eid).orElseThrow(()->new ResourceNotFoundException("Employee not found this id :"+eid));
		
		repo.delete(emp);
		
		Map<String, Boolean> response=new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		return response;
		
	}
	
}
