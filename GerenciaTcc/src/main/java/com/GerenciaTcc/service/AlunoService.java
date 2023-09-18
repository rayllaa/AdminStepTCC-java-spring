package com.GerenciaTcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Aluno;
import com.GerenciaTcc.repository.AlunoRepository;
import com.GerenciaTcc.utils.PerfilAcesso;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository repositoryAluno;
	
	public List<Aluno> listarAlunos(){
				
		return repositoryAluno.findAll();
	}
	
	public Aluno consultarAluno(Long id){
		
		Optional<Aluno> aluno = repositoryAluno.findById(id);
		
		return aluno.orElseThrow(() -> new BadRequestException("Aluno não encontrado. Id: "+id));
	}
	
	public Aluno consultarAlunoPorUsuario(String usuario){
		
		Optional<Aluno> aluno = repositoryAluno.findByUsuario(usuario);
		
		return aluno.orElseThrow(() -> new BadRequestException("Aluno não encontrado. Usuário: "+usuario));
	}
	
	public Aluno consultarAlunoPorProntuario(String prontuario){
		
		Optional<Aluno> aluno = repositoryAluno.findByProntuario(prontuario);
		
		return aluno.orElseThrow(() -> new BadRequestException("Aluno não encontrado. Prontuário: "+prontuario));
	}
	
	public Aluno inserirAluno(Aluno aluno) {
		
		Optional<Aluno> alunoCadastrado = repositoryAluno.findByProntuario(aluno.getProntuario());
		Optional<Aluno> emailCadastrado = repositoryAluno.findByEmail(aluno.getEmail());
		Optional<Aluno> usuarioCadastrado = repositoryAluno.findByUsuario(aluno.getUsuario());
				
		if(alunoCadastrado.isPresent()) {
			throw new BadRequestException("Prontuário já cadastrado!");
		}
		
		if(emailCadastrado.isPresent()) {
			throw new BadRequestException("E-mail já cadastrado!");
		}
		
		if(usuarioCadastrado.isPresent()) {
			throw new BadRequestException("Usuário já cadastrado!");
		}
		
		aluno.setCargo(PerfilAcesso.ALUNO);
		
		return repositoryAluno.save(aluno);
	}
	
	public Aluno atualizarAluno(Long id, Aluno aluno) {
		
		Aluno alunoAtualizado = consultarAluno(id);
		
		if(aluno.getNome() != null) alunoAtualizado.setNome(aluno.getNome());
		if(aluno.getCelular() != null) alunoAtualizado.setCelular(aluno.getCelular());
		if(aluno.getEmail() != null) alunoAtualizado.setEmail(aluno.getEmail());
		if(aluno.getProntuario() != null) alunoAtualizado.setProntuario(aluno.getProntuario());
		if(aluno.getCurso() != null) alunoAtualizado.setCurso(aluno.getCurso());
		if(aluno.getAnoIngresso() != null) alunoAtualizado.setAnoIngresso(aluno.getAnoIngresso());
		if(aluno.getSemestreAtual() != null) alunoAtualizado.setSemestreAtual(aluno.getSemestreAtual());
		if(aluno.getAreasInteresse() != null) alunoAtualizado.setAreasInteresse(aluno.getAreasInteresse());
		if(aluno.getSenha() != null) alunoAtualizado.setSenha(aluno.getSenha());
		
		return repositoryAluno.save(alunoAtualizado);
	}
	
	public void removerAluno(Long id){
		consultarAluno(id);
		
		repositoryAluno.deleteById(id);
	}
}
