package com.nttdata.service;

import java.util.List;

import com.nttdata.repository.entity.Usuario;

public interface UsuarioService {
	List<Usuario> listar();
	Usuario buscarPorUsername(String username);
}
