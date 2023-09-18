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
import org.springframework.web.bind.annotation.RestController;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Area;
import com.GerenciaTcc.service.AreaService;

@RestController
@RequestMapping("/areas")
@CrossOrigin(origins="*")
public class AreaController {

    @Autowired
	private AreaService serviceArea;
	
	@GetMapping
	public ResponseEntity<List<Area>> listarAreas(){
		
		List<Area> areas = serviceArea.listarAreas();
		
		return ResponseEntity.ok().body(areas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Area> consultarArea(@PathVariable("id") Long id){
		
		Area area = serviceArea.consultarArea(id);
		
		return ResponseEntity.ok().body(area);
	}
	
	@PostMapping
	public ResponseEntity<Area> inserirArea(@RequestBody @Valid Area area, BindingResult result){
		
		validarErros(result);
		
		Area areaNovo = serviceArea.inserirArea(area);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(areaNovo);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Area> atualizarArea(@PathVariable(name = ("id")) Long id, 
												@RequestBody  @Valid Area area, BindingResult result){
		
		validarErros(result);
		
		Area areaAtualizado = serviceArea.atualizarArea(id, area);
		
		return ResponseEntity.ok().body(areaAtualizado);
	}
	
//	@DeleteMapping(value = "/{id}")
//    public ResponseEntity<Area> removerArea(@PathVariable(name = ("id")) Long id){
//
//	 	serviceArea.consultarArea(id);
//        serviceArea.removerArea(id);
//
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
	
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
