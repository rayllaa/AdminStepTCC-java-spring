package com.GerenciaTcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	@NotEmpty(message = "Campo nome curso vazio!")
	private String nome;

	//@Column(unique=true)
	@NotEmpty(message = "Campo instituicao vazio!")
	private String instituicao;

	@Column
	private String logoPath;

	@OneToMany(mappedBy = "curso")
	@JsonIgnore
	private List<Aluno> alunos;
}
