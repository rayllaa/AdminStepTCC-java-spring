package com.GerenciaTcc.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Proposta;
import com.GerenciaTcc.service.PropostaService;

@RestController
@RequestMapping("/propostas")
@CrossOrigin(origins = "*")
public class PropostaController {

	@Autowired
	private PropostaService serviceProposta;

	@GetMapping
	public ResponseEntity<List<Proposta>> listarPropostas() {

		List<Proposta> propostas = serviceProposta.listarPropostas();

		return ResponseEntity.ok().body(propostas);
	}

	@GetMapping("/{numero_processo}")
	public ResponseEntity<Proposta> consultarProposta(@PathVariable(name = ("numero_processo")) String numeroProcesso) {

		Proposta proposta = serviceProposta.consultarProposta(numeroProcesso);

		return ResponseEntity.ok().body(proposta);
	}

	@GetMapping("/defesas")
	public List<Proposta> obterPropostasAPartirDaDataAtual(@RequestParam("data_atual") String dataAtual) {
		return serviceProposta.obterPropostasAPartirDaDataAtual(dataAtual);
	}
	
	@GetMapping("consultar")
	public ResponseEntity<Proposta> consultarPropostaPorIdAluno(@RequestParam("id_aluno") Long idAluno) {

		Proposta proposta = serviceProposta.consultarPropostaPorIdAluno(idAluno);

		return ResponseEntity.ok().body(proposta);
	}

	@GetMapping("consultar/{id_professor}")
	public ResponseEntity<List<Proposta>> consultarPropostaPorIdProfessor(@PathVariable("id_professor") Long idProfessor) {

		List<Proposta> propostas = serviceProposta.consultarPropostaPorIdProfessor(idProfessor);

		return ResponseEntity.ok().body(propostas);
	}

	@PostMapping
	public ResponseEntity<Proposta> inserirProposta(@RequestBody @Valid Proposta proposta, BindingResult result) {
		
		validarErros(result);

		Proposta propostaNova = serviceProposta.inserirProposta(proposta);

		return ResponseEntity.status(HttpStatus.CREATED).body(propostaNova);
	}
	
	@PatchMapping(value = "/{numero_processo}")
	public ResponseEntity<Proposta> atualizarProposta(@PathVariable(name = ("numero_processo")) String numeroProcesso, @RequestBody  @Valid Proposta proposta, BindingResult result) {

		validarErros(result);
		
		Proposta propostaAtualizada = serviceProposta.atualizarProposta(numeroProcesso, proposta);

		return ResponseEntity.ok().body(propostaAtualizada);
	}
	
//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<Proposta> removerPropostaPorId(@PathVariable(name = ("id")) Long id, BindingResult result) {
//
//		serviceProposta.removerPropostaPorId(id);
//
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}
//
	private void validarErros(BindingResult result) {
		
		if (result.hasErrors()) {
	        String errorMessage = result.getAllErrors()
	                .stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .findFirst()
	                .orElse("Erro desconhecido");
	        throw new BadRequestException(errorMessage);
	    }
	}
}
