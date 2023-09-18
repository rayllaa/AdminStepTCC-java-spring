package com.GerenciaTcc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Proposta;
import com.GerenciaTcc.repository.PropostaRepository;

@Service
public class PropostaService {
	
	@Autowired
	private PropostaRepository repositoryProposta;
	
	public List<Proposta> listarPropostas(){
				
		return repositoryProposta.findAll();
	}
	
	public Proposta consultarProposta(String numeroProcesso){
		
		Optional<Proposta> proposta = repositoryProposta.findByNumeroProcesso(numeroProcesso);
		
		return proposta.orElseThrow(() -> new BadRequestException("Proposta não encontrada. Numero Processo: "+numeroProcesso));
	}
	
	public Proposta consultarPropostaPorId(Long id){
		
		Optional<Proposta> proposta = repositoryProposta.findById(id);
		
		return proposta.orElseThrow(() -> new BadRequestException("Proposta não encontrada. Id: "+id));
	}
	
	public Proposta consultarPropostaPorIdAluno(Long idAluno){
		
		Optional<Proposta> proposta = repositoryProposta.findByAlunoId(idAluno);
		
		return proposta.orElseThrow(() -> new BadRequestException("Proposta não encontrada. Id aluno: "+idAluno));
	}
	
	public List<Proposta> consultarPropostaPorIdProfessor(Long idProfessor){
		
		List<Proposta> propostas = repositoryProposta.findByProfessorId(idProfessor);
		
		return propostas;
	}
	
	public Proposta inserirProposta(Proposta proposta) {
		
		Optional<Proposta> propostaCadastrada = repositoryProposta.findByNumeroProcesso(proposta.getNumeroProcesso());
		Optional<Proposta> alunoPropostaCadastrada = repositoryProposta.findByAlunoId(proposta.getAluno().getId());

						
		if(propostaCadastrada.isPresent()) {
			throw new BadRequestException("Número de processo já cadastrado em proposta!");
		}

	    if (alunoPropostaCadastrada.isPresent()) {
	        throw new BadRequestException("Aluno já possui proposta cadastrada!");
	    }
	    
		return repositoryProposta.save(proposta);
	}

	public Proposta atualizarProposta(String numeroProcesso, Proposta proposta) {
		
		Proposta propostaAtualizada = consultarProposta(numeroProcesso);
		
		if(proposta.getAluno() != null) propostaAtualizada.setAluno(proposta.getAluno());
		if(proposta.getProfessor() != null) propostaAtualizada.setProfessor(proposta.getProfessor());
		if(proposta.getDataEnvioColegiado() != null) propostaAtualizada.setDataEnvioColegiado(proposta.getDataEnvioColegiado());
		if(proposta.getDataAvaliacaoColegiado() != null) propostaAtualizada.setDataAvaliacaoColegiado(proposta.getDataAvaliacaoColegiado());
		if(proposta.getStatusParecerColegiado() != null) propostaAtualizada.setStatusParecerColegiado(proposta.getStatusParecerColegiado());
		if(proposta.getObservacaoColegiado() != null) propostaAtualizada.setObservacaoColegiado(proposta.getObservacaoColegiado());
		if(proposta.getTema() != null) propostaAtualizada.setTema(proposta.getTema());
		if(proposta.getLinkAta() != null) propostaAtualizada.setLinkAta(proposta.getLinkAta());
		if(proposta.getStatusEtapaEnvioProposta() != null && proposta.getStatusEtapaEnvioProposta() != false) propostaAtualizada.setStatusEtapaEnvioProposta(proposta.getStatusEtapaEnvioProposta());
		
		if(proposta.getDataInicioDesenvolvimento() != null) propostaAtualizada.setDataInicioDesenvolvimento(proposta.getDataInicioDesenvolvimento());
		if(proposta.getDataFinalDesenvolvimento() != null) propostaAtualizada.setDataFinalDesenvolvimento(proposta.getDataFinalDesenvolvimento());
		if(proposta.getStatusEtapaDesenvolvimento() != null && proposta.getStatusEtapaDesenvolvimento() != false) propostaAtualizada.setStatusEtapaDesenvolvimento(proposta.getStatusEtapaDesenvolvimento());
		
		if(proposta.getDataQualificacao() != null) propostaAtualizada.setDataQualificacao(proposta.getDataQualificacao());
		if(proposta.getHorarioQualificacao() != null) propostaAtualizada.setHorarioQualificacao(proposta.getHorarioQualificacao());
		if(proposta.getLocalQualificacao() != null) propostaAtualizada.setLocalQualificacao(proposta.getLocalQualificacao());
		if(proposta.getIntegrantesBancaQualificacao() != null) propostaAtualizada.setIntegrantesBancaQualificacao(proposta.getIntegrantesBancaQualificacao());
		if(proposta.getModalidadeQualificacao() != null)propostaAtualizada.setModalidadeQualificacao(proposta.getModalidadeQualificacao());
		if(proposta.getStatusParecerQualificacao() != null) propostaAtualizada.setStatusParecerQualificacao(proposta.getStatusParecerQualificacao());
		if(proposta.getStatusEtapaQualificacao() != null && proposta.getStatusEtapaQualificacao() != false) propostaAtualizada.setStatusEtapaQualificacao(proposta.getStatusEtapaQualificacao());

		if(proposta.getDataDefesa() != null) propostaAtualizada.setDataDefesa(proposta.getDataDefesa());
		if(proposta.getHorarioDefesa() != null) propostaAtualizada.setHorarioDefesa(proposta.getHorarioDefesa());
		if(proposta.getLocalDefesa() != null) propostaAtualizada.setLocalDefesa(proposta.getLocalDefesa());
		if(proposta.getIntegrantesBancaDefesa() != null) propostaAtualizada.setIntegrantesBancaDefesa(proposta.getIntegrantesBancaDefesa());
		if(proposta.getModalidadeDefesa() != null) propostaAtualizada.setModalidadeDefesa(proposta.getModalidadeDefesa());
		if(proposta.getStatusParecerDefesa() != null) propostaAtualizada.setStatusParecerDefesa(proposta.getStatusParecerDefesa());
		if(proposta.getStatusEtapaDefesa() != null && proposta.getStatusEtapaDefesa() != false) propostaAtualizada.setStatusEtapaDefesa(proposta.getStatusEtapaDefesa());
		
		if(proposta.getDataEntregaDocumentosFinais() != null) propostaAtualizada.setDataEntregaDocumentosFinais(proposta.getDataEntregaDocumentosFinais());
		if(proposta.getStatusEtapaFinalizacao() != null && proposta.getStatusEtapaFinalizacao() != false) propostaAtualizada.setStatusEtapaFinalizacao(proposta.getStatusEtapaFinalizacao());

		if(proposta.getStatusProposta() != null) propostaAtualizada.setStatusProposta(proposta.getStatusProposta());
		
		return repositoryProposta.save(propostaAtualizada);
	}

	public List<Proposta> obterPropostasAPartirDaDataAtual(String dataAtual) {
		return repositoryProposta.findByDataDefesaOrDataQualificacaoGreaterThanEqual(dataAtual);
	}
	
	public void removerPropostaPorId(Long id){
		//consultarPropostaPorId(id);
		
		repositoryProposta.deleteById(id);
	}
	
//	public void removerProposta(String numeroProcesso){
//		//consultarProposta(numeroProcesso);
//
//		repositoryProposta.deleteById(numeroProcesso);
//	}
}

















