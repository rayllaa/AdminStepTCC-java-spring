package com.GerenciaTcc.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GerenciaTcc.model.Proposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
	
	Optional<Proposta> findById(Long id);

	Optional<Proposta> findByNumeroProcesso(String numeroProcesso);
	
	Optional<Proposta> findByAlunoId(Long idAluno);
	
	List<Proposta> findByProfessorId(Long idProfessor);

	@Query(value = "SELECT * FROM proposta p WHERE p.data_qualificacao >= :dataAtual OR p.data_defesa >= :dataAtual", nativeQuery = true)
	List<Proposta> findByDataDefesaOrDataQualificacaoGreaterThanEqual(@Param("dataAtual") String dataAtual);
	
}
