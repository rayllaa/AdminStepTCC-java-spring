package com.GerenciaTcc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.GerenciaTcc.utils.PerfilAcesso;

import lombok.Data;

@Data
@MappedSuperclass
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	@NotEmpty(message = "Campo prontuario vazio!")
	private String prontuario;
	
	@NotEmpty(message = "Campo nome vazio!")
	private String nome;
	
	@NotEmpty(message = "Campo celular vazio!")
	//@Pattern(regexp="\\(\\d{2}\\)\\d{4,5}-\\d{4}", message = "Numero inválido")
	private String celular;
	
	@Column(unique=true)
	@NotEmpty(message = "Campo email vazio!")
	@Email
	private String email;
	
	@Column(unique=true)
	@NotEmpty(message = "Campo usuario vazio!")
	private String usuario;
	
	@NotEmpty(message = "Campo senha vazio!")
	private String senha;
	
	private PerfilAcesso cargo;
}

