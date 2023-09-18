package com.GerenciaTcc.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Area {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	@NotEmpty(message = "Campo area vazio!")
	private String area;

	@ManyToMany(mappedBy = "areasInteresse")
	@JsonIgnore
	@Transient
	private Set<Aluno> alunos = new HashSet<>();

	@ManyToMany(mappedBy = "areasInteresse")
	@JsonIgnore
	@Transient
	private Set<Professor> professores = new HashSet<>();
}
