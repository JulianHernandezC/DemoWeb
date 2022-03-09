package com.nttdata.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.repository.entity.Empleado;
import com.nttdata.service.EmpleadoService;

@RestController
@RequestMapping ("/api/empleados")
public class EmpleadoRestController {

	@Autowired
	EmpleadoService empleadoService;
	
	@GetMapping
	@Cacheable(value="cacheEmpleado")
	public List<Empleado> listarEmpleado() {
		/*try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {}*/
		return empleadoService.listar();
	}
	
	@GetMapping (value="/{id}")
	public ResponseEntity<Empleado> devuelveEmpleado(@PathVariable("id") Integer id) {		
		Empleado emp = empleadoService.getById(id);
		if (emp==null)
			return new ResponseEntity<> (null, HttpStatus.NOT_FOUND);
		else 
			return new ResponseEntity<>(emp, HttpStatus.OK);		
	}
	
	
	@CacheEvict(value="cacheEmpleado", allEntries=true)
	//@PostMapping 
	public void insertarEmpleado (@RequestBody Empleado empleado) {
		empleado.setId(null);
		//empleadoService.inserta(empleado);
	}
	
	
	@CacheEvict(value="empleados", allEntries=true)
	//@PostMapping
	public ResponseEntity<List<Empleado>> insertarEmpleado_v2 (@RequestBody Empleado empleado) throws Exception {
		try {
			empleado.setId(null);
			empleadoService.inserta(empleado);
			return new ResponseEntity<> (empleadoService.listar(), HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CacheEvict(value="empleados", allEntries=true)
	@PostMapping
	public ResponseEntity<Empleado> insertarEmpleado_v3 (@RequestBody Empleado empleado) {
		try {
			HttpHeaders headers = new HttpHeaders();
			if (empleado.getId()!=null) {
				headers.set("Message", "Para dar de alta un nuevo empleado, el ID debe llegar vacío");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}
			else if (empleado.getNombre()==null || empleado.getNombre().equals("")
				|| empleado.getApellidos()==null || empleado.getApellidos().equals("")) {
				headers.set("Message", "Ni NOMBRE ni APELLIDOS pueden ser nulos");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
			}
			
			Empleado emp = empleadoService.inserta(empleado);
			URI newPath = new URI("/api/empleados/"+emp.getId());
			headers.setLocation(newPath);
			headers.set("Message", "Empleado insertado correctamente con id: "+emp.getId());
			
			return new ResponseEntity<> (emp, headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	@CacheEvict(value="cacheEmpleado", allEntries=true)
	@PutMapping
	public void modificarEmpleado (@RequestBody Empleado empleado) {
		//if (empleado.getId() == null) {}
		empleadoService.modificar(empleado);
	}
	
	@CacheEvict(value="cacheEmpleado", allEntries=true)
	@DeleteMapping (value="/{id}")
	public void borrarEmpleado (@PathVariable("id") Integer id) {
		empleadoService.eliminar(id);
	}
	
	
	
}
