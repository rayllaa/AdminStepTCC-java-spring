package com.GerenciaTcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Curso;
import com.GerenciaTcc.repository.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository repositoryCurso;
	
	public List<Curso> listarCursos(){
				
		return repositoryCurso.findAll();
	}
	
	public Curso consultarCurso(Long id){
		
		Optional<Curso> curso = repositoryCurso.findById(id);
		
		return curso.orElseThrow(() -> new BadRequestException("Curso não encontrado. Id: "+id));
	}
	
	public Curso inserirCurso(Curso curso) {
		
		Optional<Curso> cursoCadastrado = repositoryCurso.findByNome(curso.getNome());//ignorar maiuscula e acentuação
				
		if(cursoCadastrado.isPresent()) {
			throw new BadRequestException("Curso já cadastrado! Curso: "+curso.getNome());
		}
				
		return repositoryCurso.save(curso);
	}
	
	public Curso atualizarCurso(Long id, Curso curso) {
		
		Curso cursoAtualizado = consultarCurso(id);
		
		if(curso.getNome() != null) cursoAtualizado.setNome(curso.getNome());
		if(curso.getInstituicao() != null) cursoAtualizado.setInstituicao(curso.getInstituicao());
		if(curso.getLogoPath() != null) cursoAtualizado.setLogoPath(curso.getLogoPath());
		
		return repositoryCurso.save(cursoAtualizado);
	}
	
	public void removerCurso(Long id){
		consultarCurso(id);
		
		repositoryCurso.deleteById(id);
	}
}
