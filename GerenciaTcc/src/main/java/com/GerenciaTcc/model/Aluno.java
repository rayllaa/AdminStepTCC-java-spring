package com.GerenciaTcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aluno extends Usuario {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Campo anoIngresso vazio!")
	private String anoIngresso;
	
	@NotEmpty(message = "Campo semestreAtual vazio!")
	private String semestreAtual;

	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;

	@ManyToMany
	@JoinTable(name = "aluno_area",
			joinColumns = @JoinColumn(name = "id_aluno"),
			inverseJoinColumns = @JoinColumn(name = "id_area"))
	private Set<Area> areasInteresse = new HashSet<>();
	
	@OneToOne(mappedBy = "aluno")
    @JsonIgnore
    private Proposta proposta;
	
	private String statusProposta;// = "NÃO SUBMETIDA";
}
