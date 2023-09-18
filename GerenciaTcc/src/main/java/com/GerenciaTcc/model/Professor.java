package com.GerenciaTcc.model;

import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Professor extends Usuario{
	
	@NotEmpty(message = "Campo areaAtuacao vazio!")
	private String areaAtuacao;
	
	@NotEmpty(message = "Campo disciplinasMinistradas vazio!")
	private String disciplinasMinistradas;

	@ManyToMany
	@JoinTable(name = "professor_area",
			joinColumns = @JoinColumn(name = "id_professor"),
			inverseJoinColumns = @JoinColumn(name = "id_area"))
	private Set<Area> areasInteresse = new HashSet<>();

}
