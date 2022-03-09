package com.nttdata.service;

import java.util.List;

import com.nttdata.repository.entity.Empleado;

public interface EmpleadoService {
	public void registrar (String name);
	public List<Empleado> listar();
	public List<Empleado> listarFiltroNombre(String cad);
	public List<Empleado> listarFiltroNombreEs(String cad);
	List<Empleado> listarConJPA (Integer pID, String contiene);
	public Empleado inserta(Empleado empleado) throws Exception;
	public void modificar(Empleado empleado);
	public void eliminar(Integer id);
	public Empleado getById(Integer id);
}
 