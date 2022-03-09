package com.nttdata.repository.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmpleadoTest {

	@Test
	void test() {
		Empleado e1 = new Empleado();
		
		e1.setId(1);
		assertEquals(1, e1.getId(), "Mismo id");
		
		String nombre="Nombre Prueba";
		e1.setNombre(nombre);
		assertEquals (nombre, e1.getNombre(), "Mismo nombre");
		
		String apellidos="Apellidos Prueba";
		e1.setApellidos(apellidos);
		assertEquals(apellidos, e1.getApellidos(), "Mismos apellidos");
		
		Empleado e2 = new Empleado();
		e2.setId(1);
		e2.setNombre(nombre);
		e2.setApellidos(apellidos);
		assertEquals(e1, e2, "Mismo empleado");
		assertEquals(e1, e1, "Mismo empleado y objeto");
		
		assertEquals(e1.hashCode(), e2.hashCode(), "Mismo código hash");		
		
	}


}
