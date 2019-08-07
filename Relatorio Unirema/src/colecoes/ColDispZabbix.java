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
import classes.DisponibilidadeZabbix;

public class ColDispZabbix {
	//Atributos
	private ArrayList<DisponibilidadeZabbix> colDispZabbix = new ArrayList<DisponibilidadeZabbix>();
	private String mesReferencia = "";
	private String anoReferencia = "";
		
	//Construtor
	public ColDispZabbix(){
		
	}
	//Métodos
	public String getMesReferencia() {
		return mesReferencia;
	}
	
	public String getAnoReferencia() {
		return anoReferencia;
	}
	
	public ArrayList<DisponibilidadeZabbix> retornaLista(){
		return colDispZabbix;
	}
	public void adicionaDisponibilidadeZabbix(DisponibilidadeZabbix novoDispZabbix){
		colDispZabbix.add(novoDispZabbix);
	}
	public void começarDoZero() {
		System.out.println("DISPONIBILIDADE DO ZABBIX");
		System.out.println("Tamanho ANTES da execução do Protocolo: "+ colDispZabbix.size());
		this.mesReferencia = "";
		this.anoReferencia ="";
		colDispZabbix.removeAll(colDispZabbix);
		System.out.println("Tamanho APÓS execução do protocolo: " + colDispZabbix.size());
	}
	
	public int retornaTamanhoDaLista() {
		return colDispZabbix.size();
	}
	
	public void pegarDisponibilidadeDaPlanilha(File planilha, ColCircuitos circuitos) {
		//Variavel para salvar as secretarias não encontradas na coleçao do software
		StringBuilder circuitos_nao_encontrados = new StringBuilder();
		
		try {
			FileInputStream entradaPlanilha = new FileInputStream(planilha);
			try {
				//Cria um WorkBook com todas as abas
				XSSFWorkbook workbook = new XSSFWorkbook(entradaPlanilha);
				
				//Recupera apenas a planilha 1
				XSSFSheet sheet = workbook.getSheetAt(0);
				
				//Retorna todas as linhas da planilha acima
				Iterator<Row> rowIterator = sheet.iterator();
				
				//varre todas as linhas da planilha
				while(rowIterator.hasNext()) {
					//Recebe cada linha da Planilha
					Row row = rowIterator.next();
					
					//Recebe todas as celulas da linha
					Iterator<Cell> cellIterator = row.iterator();
					
					//Inicializando as variáveis antes de realizar a busca na linha
					int contador = 0;
					String nome_circuito = null;
					String tipoIncidente = null;
					double indisponibilidade = 0.0;
					double disponibilidade = 0.0;
					
					//Varre todas as celulas da linha
					while(cellIterator.hasNext()) {
						//criamos uma celula
						Cell cell = cellIterator.next();
						
						switch (contador) {
						case 0:
							nome_circuito = cell.getStringCellValue();
							System.out.println(nome_circuito);
							break;
							
						case 1:
							tipoIncidente = cell.getStringCellValue();
							break;
						
						case 2:
							indisponibilidade = (double) cell.getNumericCellValue();
							break;
							
						case 3:
							disponibilidade = (double) cell.getNumericCellValue();
							break;
						}			
						contador ++;
					}
					
					//CONTINUAR DAQUI, TRATAR ERROS.
					Circuito circuito = circuitos.retornaCircuito(nome_circuito);
					
					if(circuito == null) {
						//Se Não encontrar o circuito da base do Zabbix na Base do software faça isso...
						if (circuitos_nao_encontrados.length() == 0)
							circuitos_nao_encontrados.append(nome_circuito);
						else 
							circuitos_nao_encontrados.append("; " + nome_circuito);
					} else {
						//ADD A COLEÇÃO...
						DisponibilidadeZabbix novoDispZabbix = new DisponibilidadeZabbix();
						novoDispZabbix.setCircuito(nome_circuito);
						novoDispZabbix.setGrupo(circuito.getGrupo());
						novoDispZabbix.setLocal(circuito.getLocal());
						novoDispZabbix.setTipoIncidente(tipoIncidente);
						novoDispZabbix.setIndisponibilidade(indisponibilidade);
						novoDispZabbix.setDisponibilidade(disponibilidade);
						colDispZabbix.add(novoDispZabbix);
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
		if (circuitos_nao_encontrados.length() == 0) {
			JOptionPane.showMessageDialog(null, "Todos os Circuitos foram localizados.");
		}
		else {
			//Para mostrar no console
			System.out.println("ERRO: Circuitos não encontrados na Base de Dados do SOFTWARE: " + circuitos_nao_encontrados);
			JOptionPane.showMessageDialog(null, "Erro Exibido também no Console: Circuitos não encontrados na Base de Dados do SOFTWARE: " + circuitos_nao_encontrados);
		}
	}
	
	public void calculaMedia() {
		double totalDisponibilidade = 0.0;
		double totalIndisponibilidade = 0.0;
		
		for (DisponibilidadeZabbix circuito : colDispZabbix) {
			totalDisponibilidade = totalDisponibilidade + circuito.getDisponibilidade();
			totalIndisponibilidade = totalIndisponibilidade + circuito.getIndisponibilidade();
		}
		
		//APÓS A SOMA TIRANDO A MÉDIA GERAL
		double mediaDisponibilidade =totalDisponibilidade/colDispZabbix.size();
		double mediaIndisponibilidade =totalIndisponibilidade/colDispZabbix.size();
		
		for (DisponibilidadeZabbix circuito : colDispZabbix) {
			circuito.setMediaDisponibilidade(mediaDisponibilidade);
			circuito.setMediaIndisponibilidade(mediaIndisponibilidade);
		}
	}
	
	public void setMesEAnoRelatorio(String mes, String ano) {
		this.anoReferencia = ano;
		this.mesReferencia = mes;
		for ( DisponibilidadeZabbix circuito : colDispZabbix) {
			circuito.setMesReferencia(mes);
			circuito.setAnoReferencia(ano);
		}
	}
	
	@Override
	public String toString() {
		return "ColDispZabbix [colDispZabbix=" + colDispZabbix + "]";
	}
}
