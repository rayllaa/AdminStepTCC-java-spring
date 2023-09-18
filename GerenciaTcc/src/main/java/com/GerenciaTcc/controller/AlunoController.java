package com.GerenciaTcc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Aluno;
import com.GerenciaTcc.service.AlunoService;

@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins="*")
public class AlunoController {

	@Autowired
	private AlunoService serviceAluno;
	
	@GetMapping
	public ResponseEntity<List<Aluno>> listarAlunos(){
		
		List<Aluno> alunos = serviceAluno.listarAlunos();
		
		return ResponseEntity.ok().body(alunos);
	}
	
	@GetMapping("consultar")
	public ResponseEntity<Aluno> consultarAlunoPorUsuario(@RequestParam("usuario") String usuario){
		
		Aluno aluno = serviceAluno.consultarAlunoPorUsuario(usuario);
		
		return ResponseEntity.ok().body(aluno);
	}
	
	@PostMapping
	public ResponseEntity<Aluno> inserirAluno(@RequestBody @Valid Aluno aluno, BindingResult result){
		validarErros(result);
		Aluno alunoNovo = serviceAluno.inserirAluno(aluno);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(alunoNovo);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Aluno> atualizarAluno(@PathVariable(name = ("id")) Long id, 
												@RequestBody Aluno aluno, BindingResult result){
		validarErros(result);
		Aluno alunoAtualizado = serviceAluno.atualizarAluno(id, aluno);
		
		return ResponseEntity.ok().body(alunoAtualizado);
	}
	
	private void validarErros(BindingResult result) {
		
		if (result.hasErrors()) {
	        String errorMessage = result.getAllErrors()
	                .stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .findFirst()
	                .orElse("Erro ao cadastrar aluno");
	        throw new BadRequestException(errorMessage);
	    }
	}
}
