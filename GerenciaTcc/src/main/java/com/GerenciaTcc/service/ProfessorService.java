package com.GerenciaTcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Professor;
import com.GerenciaTcc.repository.ProfessorRepository;
import com.GerenciaTcc.utils.PerfilAcesso;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository repositoryProfessor;
	
	public List<Professor> listarProfessores(){
		
		return repositoryProfessor.findAll();
	}
	
	public Professor consultarProfessor(Long id){
		
		Optional<Professor> professor = repositoryProfessor.findById(id);
		
		return professor.orElseThrow(() -> new BadRequestException("Professor não encontrado. Id: "+id));
	}
	
	public Professor consultarProfessorPorUsuario(String usuario){
		
		Optional<Professor> professor = repositoryProfessor.findByUsuario(usuario);
		
		return professor.orElseThrow(() -> new BadRequestException("Professor não encontrado. Usuário: "+usuario));
	}
	
	public Professor consultarProfessorPorProntuario(String prontuario){
		
		Optional<Professor> professor = repositoryProfessor.findByProntuario(prontuario);
		
		return professor.orElseThrow(() -> new BadRequestException("Professor não encontrado. Prontuário: "+prontuario));
	}
	
	public Professor inserirProfessor(Professor professor) {
				
		Optional<Professor> professorCadastrado = repositoryProfessor.findByProntuario(professor.getProntuario());
		Optional<Professor> emailCadastrado = repositoryProfessor.findByEmail(professor.getEmail());
		Optional<Professor> usuarioCadastrado = repositoryProfessor.findByUsuario(professor.getUsuario());
		
		if(professorCadastrado.isPresent()) {
			throw new BadRequestException("Prontuário já cadastrado!");
		}
		
		if(emailCadastrado.isPresent()) {
			throw new BadRequestException("E-mail já cadastrado!");
		}
		
		if(usuarioCadastrado.isPresent()) {
			throw new BadRequestException("Usuário já cadastrado!");
		}
				
		professor.setCargo(PerfilAcesso.PROFESSOR);
		
		return repositoryProfessor.save(professor);
	}
	
	public Professor atualizarProfessor(Long id, Professor professor) {
		
		Professor professorAtualizado = consultarProfessor(id);
		
		if(professor.getNome() != null) professorAtualizado.setNome(professor.getNome());
		if(professor.getCelular() != null) professorAtualizado.setCelular(professor.getCelular());
		if(professor.getEmail() != null) professorAtualizado.setEmail(professor.getEmail());
		if(professor.getProntuario() != null) professorAtualizado.setProntuario(professor.getProntuario());
		if(professor.getAreaAtuacao() != null) professorAtualizado.setAreaAtuacao(professor.getAreaAtuacao());
		if(professor.getAreasInteresse() != null) professorAtualizado.setAreasInteresse(professor.getAreasInteresse());
		if(professor.getDisciplinasMinistradas() != null) professorAtualizado.setDisciplinasMinistradas(professor.getDisciplinasMinistradas());
		if(professor.getSenha() != null) professorAtualizado.setSenha(professor.getSenha());
		
		if(professor.getCargo() != null) professorAtualizado.setCargo(professor.getCargo());

		return repositoryProfessor.save(professorAtualizado);
	}
	
	public void removerProfessor(Long id){
		consultarProfessor(id);
		
		repositoryProfessor.deleteById(id);
	}
}
