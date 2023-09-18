package com.GerenciaTcc.service;

import com.GerenciaTcc.model.*;
import com.GerenciaTcc.repository.RelatorioRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RelatorioService {
	
	@Autowired
	private RelatorioRepository repositoryRelatorio;

	public List<Aluno> listarAlunoFiltroCurso(String curso){
		
		List<Aluno> alunos = repositoryRelatorio.findByCurso(curso);
		return alunos;
	}

	public byte[] gerarRelatorioAlunoPDF(List<Aluno> listaAlunos) throws DocumentException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	    Document document = new Document();
	    PdfWriter.getInstance(document, outputStream);
	    document.open();
	
	    Paragraph titulo = new Paragraph("Relatório de Alunos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
	    titulo.setAlignment(Element.ALIGN_CENTER);
	    document.add(titulo);
	    document.add(new Paragraph("\n"));
	    
	    Paragraph quantidade = new Paragraph("Quantidade: "+listaAlunos.size(), FontFactory.getFont(FontFactory.HELVETICA, 10));
	    quantidade.setAlignment(Element.ALIGN_LEFT);
	    document.add(quantidade);
	
	    document.add(new Paragraph("\n"));
	    
	    //Tabela
	    PdfPTable table = new PdfPTable(8);
	    table.setWidthPercentage(100);
	
	    table.addCell(createCenterAlignedHeader("Prontuário"));
	    table.addCell(createCenterAlignedHeader("Nome"));
	    table.addCell(createCenterAlignedHeader("Email"));
	    table.addCell(createCenterAlignedHeader("Ano de Ingresso"));
	    table.addCell(createCenterAlignedHeader("Semestre Atual"));
	    table.addCell(createCenterAlignedHeader("Curso"));
	    table.addCell(createCenterAlignedHeader("Áreas de Interesse"));
	    table.addCell(createCenterAlignedHeader("Status Proposta"));    
	
	    for (Aluno aluno : listaAlunos) {
	    	table.addCell(createCenterAlignedCell(aluno.getProntuario()));
	        table.addCell(createCenterAlignedCell(aluno.getNome()));
	        table.addCell(createCenterAlignedCell(aluno.getEmail()));
	        table.addCell(createCenterAlignedCell(aluno.getAnoIngresso()));
	        table.addCell(createCenterAlignedCell(aluno.getSemestreAtual()));
	        table.addCell(createCenterAlignedCell(aluno.getCurso().getNome()));
	        table.addCell(createCenterAlignedCell(aluno.getAreasInteresse().toString()));
	        table.addCell(createCenterAlignedCell(aluno.getStatusProposta()));
	    }
	    
	    document.add(table);	        
	     
        // Criar o gráfico de barras
        //JFreeChart chart = createBarChart(listaAlunos);
        
//        //Grafico de pizza
//        DefaultPieDataset dataset = criarGraficoPizza(listaAlunos);
//        JFreeChart graficoPizza = ChartFactory.createPieChart("Gráfico de Pizza", dataset);
//
//        Image image, image2;
//		try {
//			//image = Image.getInstance(chart.createBufferedImage(500, 300), null);
//			image2 = Image.getInstance(graficoPizza.createBufferedImage(500, 300), Color.orange);
//		    //document.add(image);
//		    document.add(image2);
//
//		} catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            // Fechar o documento
//           // document.close();
//        }
	   	
	    document.close();
	    
	    return outputStream.toByteArray();
	}
	
	public byte[] gerarRelatorioAlunoPorCursoPDF(List<Aluno> listaAlunos) throws DocumentException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	    Document document = new Document();
	    PdfWriter.getInstance(document, outputStream);
	    document.open();
	    	
	    Paragraph titulo = new Paragraph("Relatório de Alunos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
	    titulo.setAlignment(Element.ALIGN_CENTER);
	    document.add(titulo);
	    document.add(new Paragraph("\n"));
	    
	    Paragraph quantidade = new Paragraph("Quantidade: "+listaAlunos.size(), FontFactory.getFont(FontFactory.HELVETICA, 10));
	    quantidade.setAlignment(Element.ALIGN_LEFT);
	    document.add(quantidade);
	
	    document.add(new Paragraph("\n"));
	    	    
	    Map<String, List<Aluno>> mapaAlunosPorCurso = new HashMap<>();

        // Adiciona cada aluno ao curso correspondente no mapa
        for (Aluno aluno : listaAlunos) {
            Curso curso = aluno.getCurso();
            if (!mapaAlunosPorCurso.containsKey(curso.getNome())) {
                mapaAlunosPorCurso.put(curso.getNome(), new ArrayList<>());
            }
            mapaAlunosPorCurso.get(curso.getNome()).add(aluno);
        }

        // Cria a lista de cursos com seus alunos
        List<CursoAlunos> cursosAlunos = new ArrayList<>();
        for (Map.Entry<String, List<Aluno>> entry : mapaAlunosPorCurso.entrySet()) {
            CursoAlunos cursoAlunos = new CursoAlunos();
            cursoAlunos.setCurso(entry.getKey());
            cursoAlunos.setAlunos(entry.getValue());
            cursosAlunos.add(cursoAlunos);
        }
        
        for(CursoAlunos alunos : cursosAlunos){
        	document.add(new Paragraph("\n"));
        	
        	Paragraph ac = new Paragraph(alunos.getCurso()+ " ("+alunos.getAlunos().size()+")", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
     	    ac.setAlignment(Element.ALIGN_LEFT);
     	    document.add(ac);
     	
     	    document.add(new Paragraph("\n"));

			//Grafico de pizza
			DefaultPieDataset dataset = setGraficoAlunosPorStatus(alunos.getAlunos());

			this.gerarGraficoPizza("Alunos x Etapa TCC", dataset, document);
     	    
     	   PdfPTable tabelaAlunoCurso = criarTabelaAlunosPorCurso();
     	        	    
        	for(Aluno aluno : alunos.getAlunos()) {
        		tabelaAlunoCurso.addCell(createCenterAlignedCell(aluno.getProntuario()));
            	tabelaAlunoCurso.addCell(createCenterAlignedCell(aluno.getNome()));
    	        tabelaAlunoCurso.addCell(createCenterAlignedCell(aluno.getEmail()));
    	        tabelaAlunoCurso.addCell(createCenterAlignedCell(aluno.getAnoIngresso()));
    	        tabelaAlunoCurso.addCell(createCenterAlignedCell(aluno.getSemestreAtual()));
    	        tabelaAlunoCurso.addCell(createCenterAlignedCell(aluno.getAreasInteresse()
																			.stream()
																			.map(Area::getArea)
																			.collect(Collectors.joining(", "))));
    	        tabelaAlunoCurso.addCell(createCenterAlignedCell(aluno.getStatusProposta()));
        	}
        	
        	 document.add(new Paragraph("\n"));
        	 document.add(tabelaAlunoCurso);
        }
		document.add(new Paragraph("\n"));

        //Grafico de pizza
		DefaultPieDataset datasetAlunos = setGraficoAlunosPorStatus(listaAlunos);

		this.gerarGraficoPizza("Alunos de todos os Cursos x Etapa TCC", datasetAlunos, document);

	    document.close();
	    
	    return outputStream.toByteArray();
	}

	private PdfPTable criarTabelaAlunosPorCurso() {
		  PdfPTable tabelaAlunoCurso = new PdfPTable(7);
		    tabelaAlunoCurso.setWidthPercentage(100);
		
		    tabelaAlunoCurso.addCell(createCenterAlignedHeader("Prontuário"));
		    tabelaAlunoCurso.addCell(createCenterAlignedHeader("Nome"));
		    tabelaAlunoCurso.addCell(createCenterAlignedHeader("Email"));
		    tabelaAlunoCurso.addCell(createCenterAlignedHeader("Ano de Ingresso"));
		    tabelaAlunoCurso.addCell(createCenterAlignedHeader("Semestre Atual"));
		    tabelaAlunoCurso.addCell(createCenterAlignedHeader("Áreas de Interesse"));
		    tabelaAlunoCurso.addCell(createCenterAlignedHeader("Status Proposta"));  
		    
		   return tabelaAlunoCurso;
	}
	 
	public byte[] gerarRelatorioProfessorPDF(List<Professor> listaProfessores) throws DocumentException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	    Document document = new Document();
	    PdfWriter.getInstance(document, outputStream);
	    document.open();
	
	    Paragraph titulo = new Paragraph("Relatório de Professores", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
	    titulo.setAlignment(Element.ALIGN_CENTER);
	    document.add(titulo);
	    document.add(new Paragraph("\n"));
	    
	    Paragraph quantidade = new Paragraph("Quantidade: "+listaProfessores.size(), FontFactory.getFont(FontFactory.HELVETICA, 10));
	    quantidade.setAlignment(Element.ALIGN_LEFT);
	    document.add(quantidade);
	
	    document.add(new Paragraph("\n"));
	    
	    PdfPTable table = new PdfPTable(4);
	    table.setWidthPercentage(100);
	
	    table.addCell(createCenterAlignedHeader("Nome"));
	    table.addCell(createCenterAlignedHeader("Email"));
	    table.addCell(createCenterAlignedHeader("Áreas de Interesse"));
	    table.addCell(createCenterAlignedHeader("Área de Atuação"));
	
	    for (Professor professor : listaProfessores) {
	        table.addCell(createCenterAlignedCell(professor.getNome()));
	        table.addCell(createCenterAlignedCell(professor.getEmail()));
	        table.addCell(createCenterAlignedCell(professor.getAreasInteresse()
																.stream()
																.map(Area::getArea)
																.collect(Collectors.joining(", "))));
	        table.addCell(createCenterAlignedCell(professor.getAreaAtuacao()));
	    }
	
	    document.add(table);	        
	
	    document.close();
	
	    return outputStream.toByteArray();
	}
	
	public byte[] gerarRelatorioPropostaPDF(List<Proposta> listaPropostas) throws DocumentException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	    Document document = new Document();
	    PdfWriter.getInstance(document, outputStream);
	    document.open();
	
	    Paragraph titulo = new Paragraph("Relatório de Propostas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
	    titulo.setAlignment(Element.ALIGN_CENTER);
	    document.add(titulo);
	    document.add(new Paragraph("\n"));
	    
	    Paragraph quantidade = new Paragraph("Quantidade: "+listaPropostas.size(), FontFactory.getFont(FontFactory.HELVETICA, 10));
	    quantidade.setAlignment(Element.ALIGN_LEFT);
	    document.add(quantidade);
	
	    document.add(new Paragraph("\n"));
	    
	    PdfPTable table = new PdfPTable(6);
	    table.setWidthPercentage(100);
	
	    table.addCell(createCenterAlignedHeader("Número Processo"));
	    table.addCell(createCenterAlignedHeader("Tema"));
	    table.addCell(createCenterAlignedHeader("Aluno"));
	    table.addCell(createCenterAlignedHeader("Professor"));
	    table.addCell(createCenterAlignedHeader("Curso"));
	    table.addCell(createCenterAlignedHeader("Status"));
	
	    for (Proposta proposta : listaPropostas) {
	        table.addCell(createCenterAlignedCell(proposta.getNumeroProcesso()));
	        table.addCell(createCenterAlignedCell(proposta.getTema()));
	        table.addCell(createCenterAlignedCell(proposta.getAluno().getNome()));
	        table.addCell(createCenterAlignedCell(proposta.getProfessor().getNome()));
	        table.addCell(createCenterAlignedCell(proposta.getAluno().getCurso().getNome()));
	        table.addCell(createCenterAlignedCell(proposta.getStatusProposta()));
	    }
	
	    document.add(table);	        
	
	    document.close();
	
	    return outputStream.toByteArray();
	}

	public byte[] gerarRelatorioPropostaPorCursoPDF(List<Proposta> listaPropostas) throws DocumentException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	    Document document = new Document();
	    PdfWriter.getInstance(document, outputStream);
	    document.open();
	
	    Paragraph titulo = new Paragraph("Relatório de Propostas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
	    titulo.setAlignment(Element.ALIGN_CENTER);
	    document.add(titulo);
	    document.add(new Paragraph("\n"));
	    
	    Paragraph quantidade = new Paragraph("Quantidade: "+listaPropostas.size(), FontFactory.getFont(FontFactory.HELVETICA, 10));
	    quantidade.setAlignment(Element.ALIGN_LEFT);
	    document.add(quantidade);
	
	    document.add(new Paragraph("\n"));
	    
	    Map<String, List<Proposta>> mapaPropostasPorCurso = new HashMap<>();

        // Adiciona cada proposta ao curso correspondente no mapa
        for (Proposta proposta : listaPropostas) {
            String curso = proposta.getAluno().getCurso().getNome();
            if (!mapaPropostasPorCurso.containsKey(curso)) {
            	mapaPropostasPorCurso.put(curso, new ArrayList<>());
            }
            mapaPropostasPorCurso.get(curso).add(proposta);
        }

        // Cria a lista de cursos com propostas
        List<CursoPropostas> cursosPropostas = new ArrayList<>();
        for (Map.Entry<String, List<Proposta>> entry : mapaPropostasPorCurso.entrySet()) {
            CursoPropostas cursoPropostas = new CursoPropostas();
            cursoPropostas.setCurso(entry.getKey());
            cursoPropostas.setPropostas(entry.getValue());
            cursosPropostas.add(cursoPropostas);
            
        }
        	    	    
	    for(CursoPropostas propostas : cursosPropostas){
        	
        	document.add(new Paragraph("\n"));
        	
        	Paragraph ac = new Paragraph(propostas.getCurso()+ " ("+propostas.getPropostas().size()+")", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
     	    ac.setAlignment(Element.ALIGN_LEFT);
     	    document.add(ac);
     	
     	    document.add(new Paragraph("\n"));

			//Grafico de pizza
			DefaultPieDataset dataset = setGraficoPropostasPorStatus(propostas.getPropostas());

			this.gerarGraficoPizza("Propostas x Etapa TCC", dataset, document);
     	    
     	   PdfPTable table = criarTabelaPropostasPorCurso();
     	        	    
        	for(Proposta proposta : propostas.getPropostas()) {
        		table.addCell(createCenterAlignedCell(proposta.getNumeroProcesso()));
     	        table.addCell(createCenterAlignedCell(proposta.getTema()));
     	        table.addCell(createCenterAlignedCell(proposta.getAluno().getNome()));
     	        table.addCell(createCenterAlignedCell(proposta.getProfessor().getNome()));
     	        table.addCell(createCenterAlignedCell(proposta.getAluno().getCurso().getNome()));
     	        table.addCell(createCenterAlignedCell(proposta.getStatusProposta()));
        	}
        	
        	 document.add(new Paragraph("\n"));
        	 document.add(table);	
        }

		document.add(new Paragraph("\n"));

		//Grafico de pizza
		DefaultPieDataset dataset = setGraficoPropostasPorStatus(listaPropostas);

		this.gerarGraficoPizza("Todas Propostas x Etapa TCC", dataset, document);
	    	    	   		   	
	    document.close();
	
	    return outputStream.toByteArray();
	}

	private PdfPTable criarTabelaPropostasPorCurso() {
		PdfPTable table = new PdfPTable(6);
	    table.setWidthPercentage(100);
	
	    table.addCell(createCenterAlignedHeader("Número Processo"));
	    table.addCell(createCenterAlignedHeader("Tema"));
	    table.addCell(createCenterAlignedHeader("Aluno"));
	    table.addCell(createCenterAlignedHeader("Professor"));
	    table.addCell(createCenterAlignedHeader("Curso"));
	    table.addCell(createCenterAlignedHeader("Status"));
		    
		return table;
	}
	
	private PdfPCell createCenterAlignedCell(String content) {
	    PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(FontFactory.HELVETICA, 7)));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    return cell;
	}
	 
	 private PdfPCell createCenterAlignedHeader(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
	  }

	 private DefaultPieDataset setGraficoAlunosPorStatus(List<Aluno> listaAlunos) {
		 DefaultPieDataset grafico = new DefaultPieDataset();

		 // Contagem de alunos por status de proposta
		 Map<String, Integer> contagemPorStatus = new HashMap<>();
		 for (Aluno aluno : listaAlunos) {
			 String status = aluno.getStatusProposta();
			 contagemPorStatus.put(status, contagemPorStatus.getOrDefault(status, 0) + 1);
		 }

		 // Adicionar os valores contados ao dataset
		 for (Map.Entry<String, Integer> entry : contagemPorStatus.entrySet()) {
			 String label = entry.getKey() + " (" + entry.getValue() + ")";
			 grafico.setValue(label, entry.getValue());
		 }

		 return grafico;
	 }

	private DefaultPieDataset setGraficoPropostasPorStatus(List<Proposta> listaPropostas) {
		DefaultPieDataset grafico = new DefaultPieDataset();

		// Contagem de propostas por status
		Map<String, Integer> contagemPorStatus = new HashMap<>();
		for (Proposta proposta : listaPropostas) {
			String status = proposta.getStatusProposta();
			contagemPorStatus.put(status, contagemPorStatus.getOrDefault(status, 0) + 1);
		}

		// Adicionar os valores contados ao dataset
		for (Map.Entry<String, Integer> entry : contagemPorStatus.entrySet()) {
			String label = entry.getKey() + " (" + entry.getValue() + ")";
			grafico.setValue(label, entry.getValue());
		}

		return grafico;
	}

	private void gerarGraficoPizza(String label, DefaultPieDataset dataset, Document document) {
		JFreeChart graficoPizza = ChartFactory.createPieChart(label, dataset);
		graficoPizza.setBackgroundPaint(new Color(0, 0, 0, 0)); // Cor transparente (R, G, B, Alpha)
		graficoPizza.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0)); // Cor transparente para a área do gráfico
		graficoPizza.removeLegend();

		PiePlot plot = (PiePlot) graficoPizza.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setLabelBackgroundPaint(null);
		plot.setLabelShadowPaint(null);
		plot.setLabelOutlinePaint(null);
		plot.setOutlineVisible(false); //remover borda area
		plot.setShadowPaint(null); // Remover a sombra do plot
		plot.setInsets(new RectangleInsets(0, 0, 0, 0));

		// Configurar tamanho menor da fonte para etiquetas e rótulos
		Font fonteEtiquetas = new Font("Dialog", Font.PLAIN, 10);
		plot.setLabelFont(fonteEtiquetas);

		Font fonteTitulo = new Font("SansSerif", Font.BOLD, 14); // Exemplo: tamanho 16, negrito
		graficoPizza.getTitle().setFont(fonteTitulo);

		try {
			BufferedImage bufferedImage = graficoPizza.createBufferedImage(400, 200);

			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);

			PdfPCell cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.addElement(Image.getInstance(bufferedImage, null));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);

			document.add(table);

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
}
