package com.nttdata.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nttdata.repository.entity.Empleado;

public interface EmpleadoRepoJPA extends JpaRepository<Empleado, Integer>, EmpleadoRepo {

	// https://www.baeldung.com/spring-data-jpa-query
	@Query(value="select * from empleado where nombre=?1",
			nativeQuery=true)
	public List<Empleado> listarCuyoNombreEs( String nombre);
	
	List<Empleado> findByIdGreaterThanAndNombreLike (Integer pId, String contiene);
	
	List<Empleado> findByIdGreaterThan (Integer pId);
	
}
