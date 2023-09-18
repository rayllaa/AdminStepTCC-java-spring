package com.GerenciaTcc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.GerenciaTcc.model.Aluno;

public interface RelatorioRepository extends JpaRepository<Aluno, Long> {
	
	List<Aluno> findByCurso(String curso);
}
