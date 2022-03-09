package com.nttdata.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.repository.entity.Empleado;
import com.nttdata.service.EmpleadoService;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class DemoControllerTest {
	
	@Autowired
	EmpleadoService service;
	
	@Autowired
	DemoController webController;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@WithMockUser (username="user1", roles={"ADMIN"})
	void testListarEmp() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			//assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
		
		//WHEN:
			//webController.listarEmp(new Object());
			List<Empleado> le = service.listar();
		
		//THEN:
			assertEquals(0, le.size(), "Hay dos empleados en BBDD");
	}

}
