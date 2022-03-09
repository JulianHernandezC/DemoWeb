package com.nttdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.repository.EmpleadoRepoJPA;
import com.nttdata.repository.entity.Empleado;
import com.nttdata.service.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
	
	@Autowired
	EmpleadoRepoJPA empleadoRepoJPA;
	
	@Override
	public void registrar(String name) {
		empleadoRepoJPA.registrar(name);
	}

	@Override
	public List<Empleado> listar() {
		//return empleadoRepoJPA.findByIdGreaterThan( 2 );
		return empleadoRepoJPA.findAll();
	}

	@Override
	public List<Empleado> listarFiltroNombre(String cad) {
		return empleadoRepoJPA.listarCuyoNombreContiene(cad);
	}
	
	@Override
	public List<Empleado> listarFiltroNombreEs(String cad) {
		return empleadoRepoJPA.listarCuyoNombreEs(cad);
	}

	@Override
	public List<Empleado> listarConJPA (Integer pID, String contiene) {
		return empleadoRepoJPA.findByIdGreaterThanAndNombreLike(pID, contiene);
	}

	@Override
	public Empleado inserta(Empleado empleado) throws Exception {
		return empleadoRepoJPA.save(empleado);	
	}

	@Override
	public void modificar(Empleado empleado) {
		empleadoRepoJPA.save(empleado);		
	}

	@Override
	public void eliminar(Integer id) {
		empleadoRepoJPA.deleteById(id);		
	}

	@Override
	public Empleado getById(Integer id) {
		return empleadoRepoJPA.findById(id).orElse(null);
											// .get()-> si empleado existe;
											// otro-> si no existe
	}	
}
