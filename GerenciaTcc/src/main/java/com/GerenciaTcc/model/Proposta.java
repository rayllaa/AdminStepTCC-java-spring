package com.GerenciaTcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Proposta  {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	//Envio Colegiado
	@Column(unique=true)
	@NotEmpty(message = "Campo numero processo vazio!")
	private String numeroProcesso;
	
	@NotNull(message = "Campo Orientando vazio!")
	@OneToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;
    
	@NotNull(message = "Campo Orientador vazio!")
    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;
    
    @NotEmpty(message = "Campo data de envio ao colegiado vazio!")
	private String dataEnvioColegiado;
	
    @NotEmpty(message = "Campo data de avaliação do colegiado vazio!")
	private String dataAvaliacaoColegiado;
	
	@NotEmpty(message = "Campo status de parecer do Colegiado vazio!")
	private String statusParecerColegiado;
	
	@NotEmpty(message = "Campo tema da proposta vazio!")
	private String tema;
	
	private String linkAta;
	
	private String observacaoColegiado;
	
	private Boolean statusEtapaEnvioProposta = false;
	
	//TCC em andamento
	private String dataInicioDesenvolvimento;
	
	private String dataFinalDesenvolvimento;
	
	private Boolean statusEtapaDesenvolvimento = false;

	//Qualificação	
	private String dataQualificacao;
	
	private String horarioQualificacao;
	
	private String localQualificacao;
	
//	@ManyToMany(mappedBy = "proposta", cascade = CascadeType.ALL)
//	private List<Professor> integrantesBancaQualificacao;
	
	private String integrantesBancaQualificacao;
		
	private String modalidadeQualificacao;
	 
	private String statusParecerQualificacao;
	
	private Boolean statusEtapaQualificacao = false;
	
	//Defesa
	private String dataDefesa;
	
	private String horarioDefesa;
	
	private String localDefesa;
	
	private String integrantesBancaDefesa;
	
	private String modalidadeDefesa;
	 
	private String statusParecerDefesa;
	
	private Boolean statusEtapaDefesa = false;
	
	//Finalização
	
	private String dataEntregaDocumentosFinais;
	
	private Boolean statusEtapaFinalizacao = false;
	
	private String statusProposta;
	
}
