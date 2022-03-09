package com.nttdata.repository.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.repository.EmpleadoRepoJPA;
import com.nttdata.repository.entity.Empleado;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class EmpleadoRepoImplTest {

	@Autowired
	EmpleadoRepoJPA repo;
	
	
	@BeforeEach
	void setUp() throws Exception {
		repo.deleteAll();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRegistrar() {
		repo.registrar("Mensaje prueba");
		assertTrue(true);
	}

	@Test
	void testListarCuyoNombreContiene() {
		//GIVEN:
			Empleado e1 = new Empleado();
			e1.setNombre("Lucas");
			e1.setApellidos("Ape1 Ape2");
			e1=repo.save(e1);
		
			Empleado e2 = new Empleado();
			e2.setNombre("Ana");
			e2.setApellidos("Ape1 Ape2");
			e2=repo.save(e2);
		
		//WHEN:
			List<Empleado> l1 = repo.listarCuyoNombreContiene("u");
			List<Empleado> l2 = repo.listarCuyoNombreContiene("2");
		
		//THEN:
			assertAll (
				() -> {	assertEquals(1, l1.size(), "Sólo 1 empleado contiene la 'u' en el nombre"); },
				() -> assertEquals(0, l2.size(), "No hay ningún empleado con '2' en el nombre")
			);
			
		//CLEAN
			repo.delete(e1);
			repo.delete(e2);			
	}

}
