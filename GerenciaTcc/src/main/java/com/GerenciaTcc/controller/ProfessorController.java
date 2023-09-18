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
import com.GerenciaTcc.model.Professor;
import com.GerenciaTcc.service.ProfessorService;

@RestController
@RequestMapping("/professores")
@CrossOrigin(origins="*")
public class ProfessorController {

	@Autowired
	private ProfessorService serviceProfessor;
	
	@GetMapping
	public ResponseEntity<List<Professor>> listarProfessores(){
		
		List<Professor> professores = serviceProfessor.listarProfessores();
		
		return ResponseEntity.ok().body(professores);
	}
	
	@GetMapping("consultar")
	public ResponseEntity<Professor> consultarProfessorPorUsuario(
			@RequestParam(name="usuario") String usuario){
		
		Professor professor = serviceProfessor.consultarProfessorPorUsuario(usuario);
		
		return ResponseEntity.ok().body(professor);
	}
	
	@PostMapping
	public ResponseEntity<Professor> inserirProfessor(@RequestBody @Valid Professor professor, BindingResult result){
		validarErros(result);
		Professor professorNovo = serviceProfessor.inserirProfessor(professor);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(professorNovo);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Professor> atualizarProfessor(@PathVariable(name = ("id")) Long id, 
														@RequestBody @Valid Professor professor, BindingResult result){
		
		validarErros(result);
		Professor professorAtualizado = serviceProfessor.atualizarProfessor(id, professor);
		
		return ResponseEntity.ok().body(professorAtualizado);
	}

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
