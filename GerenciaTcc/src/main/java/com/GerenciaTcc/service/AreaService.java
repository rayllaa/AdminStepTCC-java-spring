package com.GerenciaTcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GerenciaTcc.exception.BadRequestException;
import com.GerenciaTcc.model.Area;
import com.GerenciaTcc.repository.AreaRepository;

@Service
public class AreaService {

	@Autowired
	private AreaRepository repositoryArea;
	
	public List<Area> listarAreas(){
				
		return repositoryArea.findAll();
	}
	
	public Area consultarArea(Long id){
		
		Optional<Area> area = repositoryArea.findById(id);
		
		return area.orElseThrow(() -> new BadRequestException("Area não encontrada. Id: "+id));
	}
	
	public Area inserirArea(Area area) {
		
		Optional<Area> areaCadastrado = repositoryArea.findByArea(area.getArea());
				
		if(areaCadastrado.isPresent()) {
			throw new BadRequestException("Area já cadastrada! Área: "+area.getArea());
		}
				
		return repositoryArea.save(area);
	}
	
	public Area atualizarArea(Long id, Area area) {
		
		Area areaAtualizado = consultarArea(id);
		
		if(area.getArea() != null) areaAtualizado.setArea(area.getArea());
		
		return repositoryArea.save(areaAtualizado);
	}
	
	public void removerArea(Long id){
		consultarArea(id);
		
		repositoryArea.deleteById(id);
	}
}
