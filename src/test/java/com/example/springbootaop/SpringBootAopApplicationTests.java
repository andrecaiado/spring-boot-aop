package com.example.springbootaop;

import com.example.springbootaop.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringBootAopApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmployeeService employeeService;

	@Test
	void givenInitialDb_whenGetAllEmployees_shouldReturn20() {
		assertEquals(20, employeeService.getAllEmployees().size());
	}

}
