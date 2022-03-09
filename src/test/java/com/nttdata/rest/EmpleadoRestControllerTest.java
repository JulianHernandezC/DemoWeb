package com.nttdata.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.repository.EmpleadoRepoJPA;
import com.nttdata.repository.entity.Empleado;
import com.nttdata.service.EmpleadoService;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class EmpleadoRestControllerTest {
	private Empleado e1, e2;
	
	@Autowired
	EmpleadoRepoJPA repo;
	
	@Autowired
	EmpleadoRestController controller;
	
	@Autowired
	EmpleadoService service;
	
	@Mock //-> 'simular'
	EmpleadoService serviceMock;
	
	@InjectMocks
	EmpleadoRestController controllerMock;
	
	

	@BeforeEach
	void setUp() throws Exception {
		repo.deleteAll();
		
		e1 = new Empleado();
		e1.setNombre("Manuel");
		e1.setApellidos("Muñoz Martínez");
		e1=repo.save(e1);
	
		e2 = new Empleado();
		e2.setNombre("Ana");
		e2.setApellidos("Alexa Armani");
		e2=repo.save(e2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testListarEmpleado() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			//assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
		
		//WHEN:
			List<Empleado> le = controller.listarEmpleado();
		
		//THEN:
			assertEquals(2, le.size(), "Hay dos empleados en BBDD");
		}

	@Test
	void testDevuelveEmpleado() {
		//GIVEN:
			//Hay dos empleados en la BBDD: 
			assertEquals(2, repo.findAll().size(), "Hay dos empleados en BBDD");
			
		//WHEN:
			ResponseEntity<Empleado> re = controller.devuelveEmpleado (e1.getId());

		//THEN:
			assertEquals(re.getBody().getId(), e1.getId(), "recuperado empleado");
			assertThat (re.getStatusCodeValue()).isEqualTo(200) ;
	}
	
	@Test
	void testDevuelveEmpleadoNoEncuentra() {
		//GIVEN:
			//Hay dos empleados en la BBDD: 
			assertEquals(2, repo.findAll().size(), "Hay dos empleados en BBDD");
			
		//WHEN:
			ResponseEntity<Empleado> re = controller.devuelveEmpleado (-1);
			
		//THEN:
			assertNull(re.getBody(), "Empleado no encontarado");
			assertThat (re.getStatusCodeValue()).isEqualTo(404) ;
	}


	@Test
	void testInsertarEmpleado() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, repo.findAll().size(), "Hay dos empleados en BBDD");
		
		//WHEN:
			Empleado e3 = new Empleado();
			e3.setNombre("N3");
			e3.setApellidos("AP3");
			controller.insertarEmpleado (e3);		
			
		//THEN:
			List<Empleado> le = repo.findAll();
			assertEquals(2, le.size(), "ÑAPA: El insertar no hace nada");	
	}
	

	@Test
	void testInsertarEmpleado_v2() throws Exception {
		//GIVEN:
		//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, repo.findAll().size(), "Hay dos empleados en BBDD");
		
		//WHEN:
			Empleado e3 = new Empleado();
			e3.setNombre("N3");
			e3.setApellidos("AP3");
			ResponseEntity<List<Empleado>> re = controller.insertarEmpleado_v2 (e3);		
			
		//THEN:
			List<Empleado> le = repo.findAll();
			assertEquals(3, le.size(), "Hay 3 empleados");	
			assertEquals (201, re.getStatusCodeValue(), "Código error ok");
	}
	
	@Test
	void testInsertarEmpleado_v2Excepcion() throws Exception {
		//GIVEN:
			when ( serviceMock.inserta(e1) ).thenThrow (new Exception());
			
		//WHEN:
			ResponseEntity<List<Empleado>> re = controllerMock.insertarEmpleado_v2(e1);
			
		//THEN:
			assertEquals(500, re.getStatusCodeValue(), "Excepción");
	
	}
	
	@Test
	void testInsertarEmpleado_v3_idIsNull() throws Exception{
		//GIVEN:
		//Hay dos usuarios en la BBDD: 
		assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");

	//WHEN:
		Empleado e3= new Empleado();
		e3.setId(222);
		e3.setNombre("N3");
		e3.setApellidos("AP3");
		ResponseEntity<Empleado> re=  controller.insertarEmpleado_v3(e3);
	
	//THEN:
		assertEquals(406, re.getStatusCodeValue(), "Validación ok del id");
	}

	

	@Test
	void testInsertarEmpleado_v3() throws Exception {
		//GIVEN:
		when ( serviceMock.inserta(e1) ).thenThrow (new Exception());
		
	//WHEN:
		ResponseEntity<Empleado> re = controller.insertarEmpleado_v3(e1);
		
	//THEN:
		assertEquals(406, re.getStatusCodeValue(), "Excepción");
	}

	@Test
	void testModificarEmpleado() {
		//GIVEN:
		//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
		assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");

	//WHEN:
		String nuevoNombre = "Fulanito";
		e2.setNombre(nuevoNombre);
		controller.modificarEmpleado(e2);
		
	//THEN:
		assertEquals (nuevoNombre, service.getById( e2.getId() ).getNombre(), "Modificado nombre e2" );
		assertEquals(2, service.listar().size(), "Sigue habiendo dos empleados en BBDD");			

	}

	@Test
	void testBorrarEmpleado() {
		//GIVEN:
			//Hay dos usuarios en bbdd ('Manuel' y 'Ana')
			assertEquals(2, service.listar().size(), "Hay dos empleados en BBDD");
	
		//WHEN:
			controller.borrarEmpleado( e2.getId() );
			
		//THEN:
			assertEquals(1, service.listar().size(), "Solo queda 1 empleado");

	}

}
