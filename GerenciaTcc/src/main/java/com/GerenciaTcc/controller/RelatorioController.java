package com.GerenciaTcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GerenciaTcc.model.Aluno;
import com.GerenciaTcc.model.Professor;
import com.GerenciaTcc.model.Proposta;
import com.GerenciaTcc.service.RelatorioService;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
	
	@Autowired
	private RelatorioService relatorioService;
    
    @PostMapping(value = "/gerar/alunos", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> gerarRelatorioAluno(@RequestBody List<Aluno> listaAlunos) throws DocumentException {
        byte[] relatorioBytes = relatorioService.gerarRelatorioAlunoPorCursoPDF(listaAlunos);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio-alunos.pdf");

        return ResponseEntity.ok().headers(headers).body(relatorioBytes);
    }
    
    @PostMapping(value = "/gerar/professores", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> gerarRelatorioProfessor(@RequestBody List<Professor> listaProfessores) throws DocumentException {
        byte[] relatorioBytes = relatorioService.gerarRelatorioProfessorPDF(listaProfessores);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio-professores.pdf");

        return ResponseEntity.ok().headers(headers).body(relatorioBytes);
    }
    
    @PostMapping(value = "/gerar/propostas", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> gerarRelatorioProposta(@RequestBody List<Proposta> listaPropostas) throws DocumentException {
        byte[] relatorioBytes = relatorioService.gerarRelatorioPropostaPorCursoPDF(listaPropostas);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio-propostas.pdf");

        return ResponseEntity.ok().headers(headers).body(relatorioBytes);
    }
}
