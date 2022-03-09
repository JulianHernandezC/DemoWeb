package com.nttdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.repository.entity.Usuario;

public interface UsuarioRepoJPA extends JpaRepository<Usuario, String> {

}
