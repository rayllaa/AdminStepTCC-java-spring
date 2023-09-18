package com.GerenciaTcc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Curso;
import com.GerenciaTcc.service.CursoService;
import com.GerenciaTcc.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins="*")
public class CursoController {

	@Autowired
	private CursoService serviceCurso;

	@Autowired
	private FileStorageService fileStorageService;
	
	@GetMapping
	public ResponseEntity<List<Curso>> listarCursos(){
		
		List<Curso> cursos = serviceCurso.listarCursos();
		
		return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Curso> consultarCurso(@PathVariable("id") Long id){
		
		Curso curso = serviceCurso.consultarCurso(id);
		
		return ResponseEntity.ok().body(curso);
	}
	
	@PostMapping
	public ResponseEntity<Curso> inserirCurso(@RequestBody @Valid Curso curso, BindingResult result){
		
		validarErros(result);
		
		Curso cursoNovo = serviceCurso.inserirCurso(curso);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoNovo);
	}

	@PostMapping("/upload-logo")
	public ResponseEntity<String> uploadLogo(@RequestPart("file") MultipartFile file,
											 @RequestParam("id") Long idCurso) {
		Curso curso = serviceCurso.consultarCurso(idCurso);

		if (curso == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
		}

		String fileName = fileStorageService.storeFile(file);

		curso.setLogoPath(fileName);
		serviceCurso.atualizarCurso(idCurso, curso);

		return ResponseEntity.ok().body("{\"message\": \"Logo salvo com sucesso.\"}");
	}

	@GetMapping("/logo/{id}")
	public ResponseEntity<byte[]> getLogo(@PathVariable Long id) {
		Curso curso = serviceCurso.consultarCurso(id);

		if (curso == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		try {
			Resource recurso = fileStorageService.getFile(curso.getLogoPath());
			InputStream inputStream = recurso.getInputStream();
			byte[] imageBytes = IOUtils.toByteArray(inputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG); // Ou o tipo de mídia apropriado.

			return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
		} catch (IOException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Curso> atualizarCurso(@PathVariable(name = ("id")) Long id, 
												@RequestBody  @Valid Curso curso, BindingResult result){
		
		validarErros(result);
		
		Curso cursoAtualizado = serviceCurso.atualizarCurso(id, curso);
		
		return ResponseEntity.ok().body(cursoAtualizado);
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
