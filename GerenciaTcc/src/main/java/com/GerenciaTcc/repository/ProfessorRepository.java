package com.GerenciaTcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GerenciaTcc.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{

	Optional<Professor> findById(Long id);
	
	Optional<Professor> findByProntuario(String prontuario);
	
	Optional<Professor> findByUsuario(String usuario);
	
	Optional<Professor> findByEmail(String email);

}
