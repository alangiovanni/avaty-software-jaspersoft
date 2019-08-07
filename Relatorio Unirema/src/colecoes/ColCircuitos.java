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

public class ColCircuitos {
	//Atributos
	private ArrayList<Circuito> colCircuitos = new ArrayList<Circuito>();
	
	//Constructor
	public ColCircuitos(){
		
	}
	
	//M�todos
	/**
	 * Mock Factory of entity circuit
	**/
	public ArrayList<Circuito> retornaLista(){
		return colCircuitos;
	}
	
	public void adicionaUsuarioNaLista(Circuito novoCircuito){
		colCircuitos.add(novoCircuito);
	}
	
	public void removeUsuarioDaLista(Circuito novoCircuito){
		colCircuitos.remove(novoCircuito);
	}
	
	public int retornaTamanhoLista() {
		return colCircuitos.size();
	}
	
	//N�O USADO
	public void começarDoZero() {
		System.out.println("LATÊNCIA");
		System.out.println("Tamanho ANTES da execução do Protocolo: "+ colCircuitos.size());
		colCircuitos.removeAll(colCircuitos);		
		System.out.println("Tamanho APÓS execução do protocolo: " + colCircuitos.size());
	}
	
	public Circuito retornaCircuito(String circuito){
		for(Circuito circuitoDaVez: colCircuitos) {
			if(circuitoDaVez.getCircuito().equals(circuito)) {
				return circuitoDaVez;
			}
		}
		return null;
	}
	
	public void pegarLatenciaDaPlanilha(File planilha) {
		//Variavel para salvar os circuitos n�o encontrados na cole�ao do software
		String circuitos_nao_encontrados = null;
		
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
					
					//Inicializando as vari�veis antes de realizar a busca na linha
					int contador = 0;
					int latencia = 0;
					String nome_circuito = null;
					boolean circuito_encontrado=false;
					
					
					//Varre todas as celulas da linha
					while(cellIterator.hasNext()) {
						//criamos uma celula
						Cell cell = cellIterator.next();
						
						switch (contador) {
						case 0:
							nome_circuito = cell.getStringCellValue();
							break;
							
						case 4:
							latencia = (int) cell.getNumericCellValue();
							break;
						}			
						contador ++;
					}
					
					//Faz uma varredura e verifica se j� encontra-se cadastrado o circuito
					//Se sim, atualiza
					//Se n�o encontrar, notifica para o usuario
					
					for(Circuito circuito: colCircuitos){
						if(circuito.getCircuito().equalsIgnoreCase(nome_circuito)) {
							//Circuito j� existe na cole��o, atualizando-o.
							circuito.setLatencia(latencia);
							//Informo aqui que encontrei o circuito na planilha.
							circuito_encontrado=true;
							
							if (circuito.getTecnologia().equalsIgnoreCase("mpls")) {
								//Calculo do desconto
								circuito.setDesconto(((latencia-130.00)/10)*0.0025*circuito.getValor());
							}
						}
					}
					// Circuitos n�o encontrados na cole��o
					if(circuito_encontrado == false) {
						if(circuitos_nao_encontrados == null) {
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
		}
		else {
			JOptionPane.showMessageDialog(null, "ERRO: Circuitos não encontrados no SOFTWARE: " + circuitos_nao_encontrados);
		}
			
	}
	
	public void lerDaPlanilha(File planilha) {
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
					
					int contador = 0;
					Circuito novoCircuito = new Circuito();
					
					//Varre todas as celulas da linha
					while(cellIterator.hasNext()) {
						//criamos uma celula
						Cell cell = cellIterator.next();
						
						switch (contador) {
						case 0:
							try	{
								novoCircuito.setCircuito(cell.getStringCellValue());	
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o CIRCUITO. Está na Primeira coluna (A)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return;
							}
							break;
						case 1:
							try {
								novoCircuito.setLocalizacao(cell.getStringCellValue());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar a LOCALIZAÇÃO. Está na Segunda coluna (B)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return;
							}
							break;
						case 2:
							try {
								novoCircuito.setTecnologia(cell.getStringCellValue());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar a TECNOLOGIA. Est� na Terceira coluna (C)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return;
							}
							break;
						case 3:
							try {
								novoCircuito.setGrupo(cell.getStringCellValue());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o GRUPO. Est� na Quarta coluna (D)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return;
							}
							break;
						case 4:
							try {
								novoCircuito.setLocal(cell.getStringCellValue());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o LOCAL. Est� na Quinta coluna (E)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return;
							}
							break;
						case 5:
							try {
								novoCircuito.setBandaContratada(cell.getStringCellValue());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar a VELOCIDADE DO LINK. Está na Sexta coluna (F)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return;
							}
							break;
						case 6:
							try {
								novoCircuito.setValor(cell.getNumericCellValue());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o VALOR DO LINK. Está na Sétima coluna (G)? Esta formatado para NÚMERO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return;
							}
							break;
						}			
						contador ++;
					}
					
					//Faz uma varredura e verifica se j� encontra-se cadastrado o circuito
					//Se sim, atualiza
					//Se n�o encontrar, adiciona novo.
					boolean circuito_encontrado=false;
					for(Circuito circuito: colCircuitos){
						if(circuito.getCircuito().equals(novoCircuito.getCircuito())) {
							//Circuito j� existe na cole��o, atualizando-o.
							circuito.setLocalizacao(novoCircuito.getLocalizacao());
							circuito.setBandaContratada(novoCircuito.getBandaContratada());
							circuito.setGrupo(novoCircuito.getGrupo());
							circuito.setLocal(novoCircuito.getLocal());
							circuito.setTecnologia(novoCircuito.getTecnologia());
							circuito.setValor(novoCircuito.getValor());
							
							//Informo aqui que encontrei o circuito na planilha.
							circuito_encontrado=true;
						}
					}
					if(circuito_encontrado == false) {
						colCircuitos.add(novoCircuito);
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
		
		JOptionPane.showMessageDialog(null, "Cadastro/Atualização Concluída com SUCESSO! ");
	}
	
	//Este m�todo calcula o total do desconto somando os descontos de todos os circuitos. Em seguida seto o total e cada circuito.
	public void calcularESetarTotal() {
		double total=0.0;
		for(Circuito circuito: colCircuitos){
			total = total + circuito.getDesconto();
		}
		
		for(Circuito circuito: colCircuitos){
			circuito.setTotal(total);
		}
	}
	
	public ColCircuitos retornaListaComLatenciaAlta(){
		ColCircuitos circuitosComLatenciaAlta = new ColCircuitos();
		for(Circuito circuito: colCircuitos){
			if(circuito.getLatencia() > 0) {
				circuitosComLatenciaAlta.adicionaUsuarioNaLista(circuito);
			}
		}
		return circuitosComLatenciaAlta;
	}

	@Override
	public String toString() {
		return "ColCircuitos [colCircuitos=" + colCircuitos + "]";
	}
}
