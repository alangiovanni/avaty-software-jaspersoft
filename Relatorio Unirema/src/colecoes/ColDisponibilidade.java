package colecoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import classes.Circuito;
import classes.DisponibilidadeLink;

public class ColDisponibilidade {
	//Atributos
	private ArrayList<DisponibilidadeLink> colDisponibilidade = new ArrayList<DisponibilidadeLink>();
	private String mesReferencia = "";
	private String anoReferencia = "";

	//Constructor
	public ColDisponibilidade(){
		
	}
	
	//Métodos
	
	public ArrayList<DisponibilidadeLink> retornaLista(){
		return colDisponibilidade;
	}
	
	public String getMesReferencia() {
		return mesReferencia;
	}
	
	public String getAnoReferencia() {
		return anoReferencia;
	}
	
	public void adicionaDisponibilidade(DisponibilidadeLink novaDisponibilidade){
		colDisponibilidade.add(novaDisponibilidade);
	}
	
	public void começarDoZero() {
		System.out.println("LATÊNCIA");
		System.out.println("Tamanho ANTES da execução do Protocolo: "+ colDisponibilidade.size());
		this.mesReferencia = "";
		this.anoReferencia ="";
		colDisponibilidade.removeAll(colDisponibilidade);
		System.out.println("Tamanho APÓS execução do protocolo: " + colDisponibilidade.size());
	}
	
	public int retornaTamanhoDaLista() {
		return colDisponibilidade.size();
	}
	public void pegaDisponibilidadeDaPlanilha(File planilha, ColCircuitos circuitos) {
		// Variavel para salvar os circuitos não encontrados na coleçao do software
		String circuitos_nao_encontrados = null;

		try {
			FileInputStream entradaPlanilha = new FileInputStream(planilha);
			try {
				// Cria um WorkBook com todas as abas
				XSSFWorkbook workbook = new XSSFWorkbook(entradaPlanilha);

				// Recupera apenas a planilha 1
				XSSFSheet sheet = workbook.getSheetAt(0);

				// Retorna todas as linhas da planilha acima
				Iterator<Row> rowIterator = sheet.iterator();

				// varre todas as linhas da planilha
				while (rowIterator.hasNext()) {
					// Recebe cada linha da Planilha
					Row row = rowIterator.next();

					// Recebe todas as celulas da linha
					Iterator<Cell> cellIterator = row.iterator();

					// Inicializando as variáveis antes de realizar a busca na linha
					int contador = 0;
					String nome_circuito = null;
					int totalColetas = 0;
					int coletasAceitaveis= 0;
					int disponibilidade = 0;

					// Varre todas as celulas da linha
					while (cellIterator.hasNext()) {
						// criamos uma celula
						Cell cell = cellIterator.next();

						switch (contador) {
						case 0:
							nome_circuito = cell.getStringCellValue();
							break;
						case 1:
							totalColetas = (int) cell.getNumericCellValue();
							break;
						case 2:
							coletasAceitaveis = (int) cell.getNumericCellValue();
							break;
						case 3:
							disponibilidade = (int) cell.getNumericCellValue();
							break;
						}
						contador++;
					}

					// Faz uma varredura e verifica se já encontra-se cadastrado o circuito
					// Se sim, atualiza
					// Se não encontrar, notifica para o usuario
					boolean circuito_encontrado = false;
					
					for (Circuito circuito : circuitos.retornaLista()) {
						if (circuito.getCircuito().equalsIgnoreCase(nome_circuito)) {
							// Informo aqui que encontrei o circuito na planilha.
							circuito_encontrado = true;
							
							//Setando a disponibilidade
							DisponibilidadeLink novaDisponibilidade = new DisponibilidadeLink();
							
							novaDisponibilidade.setCircuito(circuito.getCircuito());
							novaDisponibilidade.setBandaContratada(circuito.getBandaContratada());
							novaDisponibilidade.setLocalizacao(circuito.getLocalizacao());
							novaDisponibilidade.setGrupo(circuito.getGrupo());
							novaDisponibilidade.setLocal(circuito.getLocal());
							novaDisponibilidade.setTecnologia(circuito.getTecnologia());
							novaDisponibilidade.setValor(circuito.getValor());
							//Infor de disponibilidades
							novaDisponibilidade.setDisponibilidade(disponibilidade);
							novaDisponibilidade.setColetasAceitaveis(coletasAceitaveis);
							novaDisponibilidade.setTotalColetas(totalColetas);
							
							//DEFINIR O VALOR A PAGAR DO CIRCUITO AQUI BASEADO NA DISPONIBILIDADE
							// Iniciando a variável para tratar nos IF's Abaixo
							double valorDescontado;
							double valorDesconto;
							
							if (disponibilidade >= 98) {
								valorDescontado = 0.0;
								valorDesconto = 0.0;
								
								//pagar 100%
								novaDisponibilidade.setValorPagar(circuito.getValor());
								novaDisponibilidade.setValorDesconto(valorDesconto);
								
							} else if ((disponibilidade >= 96) && (disponibilidade < 98)) {
								//pagar 98%
								valorDescontado = circuito.getValor()*0.98;
								valorDesconto = circuito.getValor()-valorDescontado;
								
								//Valores
								novaDisponibilidade.setValorPagar(valorDescontado);
								novaDisponibilidade.setValorDesconto(valorDesconto);
								
								
							} else if ((disponibilidade >= 94) && (disponibilidade < 96)) {
								//pagar 96%
								valorDescontado = circuito.getValor()*0.96;
								valorDesconto = circuito.getValor()-valorDescontado;								
								
								//Valores
								novaDisponibilidade.setValorPagar(valorDescontado);
								novaDisponibilidade.setValorDesconto(valorDesconto);
							}
							else {
								//pagar 90%
								valorDescontado = circuito.getValor()*0.90;
								valorDesconto = circuito.getValor()-valorDescontado;
								
								//Valores
								novaDisponibilidade.setValorPagar(valorDescontado);
								novaDisponibilidade.setValorDesconto(valorDesconto);
							}
							
							colDisponibilidade.add(novaDisponibilidade);
						}
					}
					// Circuitos não encontrados na coleção
					if (circuito_encontrado == false) {
						if (circuitos_nao_encontrados == null) {
							circuitos_nao_encontrados = nome_circuito;
						} else
							circuitos_nao_encontrados.concat(", " + nome_circuito);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (circuitos_nao_encontrados == null) {
			JOptionPane.showMessageDialog(null, "Todos os circuitos foram localizados.");
		} else {
			JOptionPane.showMessageDialog(null,
					"ERRO: Circuitos não encontrados na Base de Dados: " + circuitos_nao_encontrados);
		}
	}
	
	public void setarTotalValorPagar() {
		double totalValorPagar = 0.0;
		double totalDescontoPagar = 0.0;
		
		for ( DisponibilidadeLink circuito : colDisponibilidade) {
			totalValorPagar = totalValorPagar + circuito.getValorPagar();
			totalDescontoPagar = totalDescontoPagar + circuito.getValorDesconto();
		}

		for (DisponibilidadeLink circuito : colDisponibilidade) {
			circuito.setTotalValorPagar(totalValorPagar);
			circuito.setTotalValorDesconto(totalDescontoPagar);
		}
	}
	
	public void setMesEAnoRelatorio(String mes, String ano) {
		this.anoReferencia = ano;
		this.mesReferencia = mes;
		for ( DisponibilidadeLink circuito : colDisponibilidade) {
			circuito.setMesReferencia(mes);
			circuito.setAnoReferencia(ano);
		}
	}
}
