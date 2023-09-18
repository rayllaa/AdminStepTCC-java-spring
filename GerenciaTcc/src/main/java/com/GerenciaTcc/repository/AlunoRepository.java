package com.GerenciaTcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GerenciaTcc.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	Optional<Aluno> findById(Long id);
	
	Optional<Aluno> findByProntuario(String prontuario);
	
	Optional<Aluno> findByUsuario(String usuario);
	
	Optional<Aluno> findByEmail(String email);
}
