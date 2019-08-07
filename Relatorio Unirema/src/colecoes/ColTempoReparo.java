package colecoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import classes.Circuito;
import classes.TempoDeReparo;
import telas.ViewMesReferencia;

public class ColTempoReparo {
	
	//Atributos
	private ArrayList<TempoDeReparo> colTempoReparos = new ArrayList<TempoDeReparo>();
	private String mesReferencia = "";
	private String anoReferencia = "";
	
	//Constructor
	public ColTempoReparo(){
		
	}
	
	//M�todos
	public String getMesReferencia() {
		return mesReferencia;
	}
	
	public String getAnoReferencia() {
		return anoReferencia;
	}
	public ArrayList<TempoDeReparo> retornaLista(){
		return colTempoReparos;
	}
	
	public void adicionaReparo(TempoDeReparo novoTempoDeReparo){
		colTempoReparos.add(novoTempoDeReparo);
	}
	
	public void removeReparo(TempoDeReparo novoTempoDeReparo){
		colTempoReparos.remove(novoTempoDeReparo);
	}
	
	public void começarDoZero() {
		System.out.println("TEMPO DE REPARO");
		System.out.println("Tamanho ANTES da execução do Protocolo: "+ colTempoReparos.size());
		colTempoReparos.removeAll(colTempoReparos);
		this.mesReferencia = "";
		this.anoReferencia ="";
		System.out.println("Tamanho APÓS execução do protocolo: " + colTempoReparos.size());
	}
	
	public int retornaTamanhoDaLista() {
		return colTempoReparos.size();
	}
	
	public int pegaReparoDaPlanilha(File planilha, ColCircuitos circuitos) {
		int QtdeDiaMes;
		int erro;
		//Seta o m�s e o ano na cole��o para referencia do relatorio. Aqui o usu�rio seleciona o m�s e o ano.
		erro=pegaMesAnoReferencia();
		
		//Se erro igual a 1 o usu�rio cancelou.
		if (erro == 1)
			return 1;
		
		//Obtendo a quantidade de dias no m�s
		QtdeDiaMes=returnQtdeDiaMes();
		
		// Variavel para salvar os circuitos n�o encontrados na cole�ao do software
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

					// Inicializando as vari�veis antes de realizar a busca na linha
					int contador = 0;
					String nome_circuito = null;
					String protocolo = null;
					String problema = null;
					String expediente = null;
					int deltaDuracaoReparo = 0;
					
					// Varre todas as celulas da linha
					while (cellIterator.hasNext()) {
						// criamos uma celula
						Cell cell = cellIterator.next();
						
						switch (contador) {
						//ESTOU IGNORANDO A PRIMERA COLUNA, QUE � A DO TICKET, Por enquanto.
						case 1:
							try {
								nome_circuito = cell.getStringCellValue().trim();	
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o CIRCUITO. Está na Segunda coluna (B)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return 2;
							}
							break;
						case 2:
							//AQUI LEIO APENAS APARTIR DO PROBLEMA, IGNORO AS NOMENCLATURAS DE PRO001...
							try {
								problema = cell.getStringCellValue().substring(8).trim();	
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o PROBLEMA. Está na Terceira coluna (C)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return 2;
							}
							break;
						case 3:
							try {
								expediente = cell.getStringCellValue().trim();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o EXPEDIENTE. Está na Quarta coluna (D)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return 2;
							}
							break;
						case 4:
							//Delta da Dura��o do Reparo j� em MINUTOS.
							try {
								deltaDuracaoReparo = (int) cell.getNumericCellValue();	
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o DELTA. Está na Quinta coluna (E)? Está formatado para N�MERO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return 2;
							}
							break;
						case 5:
							try {
								protocolo = cell.getStringCellValue().trim();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro ao coletar o PROTOCOLO. Está na Sexta coluna (F)? Está formatado para TEXTO?", "ERRO NO TIPO DE DADOS", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
								return 2;
							}
							break;
						}
						contador++;
					}
					
					// Faz uma varredura e verifica se j� encontra-se cadastrado o circuito
					// Se sim, atualiza
					// Se n�o encontrar, notifica para o usuario
					boolean circuito_encontrado = false;
					boolean circuito_encontrado_reparo = false;
					
					for (Circuito circuito : circuitos.retornaLista()) {
						if (circuito.getCircuito().equalsIgnoreCase(nome_circuito)) {
							// Informo aqui que encontrei o circuito na planilha.
							circuito_encontrado = true;
							
							// Se for da SEFAZ, n�o fa�a nada, pule para pr�xima linha
							if (circuito.getGrupo().equalsIgnoreCase("SEFAZ"))
								break;
							
							//AQUI PROCURA POR CIRCUITOS COM MAIS DE UM REPARO ABERTO
							for	(TempoDeReparo reparo: colTempoReparos) {
								if (reparo.getCircuito().equalsIgnoreCase(nome_circuito)) { //SE TIVER MAIS DE UM REPARO...
									circuito_encontrado_reparo=true;
									//Somo as dura��es dos REPAROs para o mesmo circuito
									int novaDuracaoReparo = reparo.getDuracaoReparo() + formTemRepMin(reparo, deltaDuracaoReparo);
									reparo.setDuracaoReparo(novaDuracaoReparo);
									
									//Contabilizo mais um na quantidade de reparos
									reparo.setQtdeReparos(reparo.getQtdeReparos()+1);
									
									//Concatena procotolos
									reparo.concatProtocolo(protocolo);
									//reparo.concat_tkt_prot_problem(ticket, protocolo, problema); // N�o existe ainda
									
									// CALCULO DO DESCONTO
									calcularDesconto(reparo);
								}
							}
							
							//S� entrar� aqui se o for um novo reparo
							if (!circuito_encontrado_reparo) {
								//Abrindo o reparo
								TempoDeReparo novoReparo = new TempoDeReparo();
								
								novoReparo.setCircuito(circuito.getCircuito());
								novoReparo.setBandaContratada(circuito.getBandaContratada());
								novoReparo.setLocalizacao(circuito.getLocalizacao());
								novoReparo.setGrupo(circuito.getGrupo());
								novoReparo.setLocal(circuito.getLocal());
								novoReparo.setTecnologia(circuito.getTecnologia());
								novoReparo.setProtocolos(protocolo);
								novoReparo.setProblema(problema);
								novoReparo.setValor(circuito.getValor());
								novoReparo.setExpediente(expediente);
								novoReparo.setAnoReferencia(this.anoReferencia);
								novoReparo.setMesReferencia(this.mesReferencia);
								
								//Primeiro reparo
								novoReparo.setQtdeReparos(1);

								// SETANDO O SLA DOS CIRCUITOS COM BASE EM SUA LOCALIZA��O E TECNOLOGIA
								/* if (circuito.getTecnologia().contains("SAT")) {
									if (circuito.getLocalizacao().equalsIgnoreCase("capital")) {
										novoReparo.setSlaHora(8);
										novoReparo.setSlaMin(480);

									} else {
										novoReparo.setSlaHora(24);
										novoReparo.setSlaMin(1440);
									}
								} else { */
									if (circuito.getLocalizacao().equalsIgnoreCase("capital")) {
										novoReparo.setSlaHora(3);
										novoReparo.setSlaMin(180);
									} else {
										novoReparo.setSlaHora(6);
										novoReparo.setSlaMin(360);
									}
								//}
								
								// Setando o total de minutos do M�s e a dura��o do reparo, necess�rio ser nesta ordem
								novoReparo.setTotalMinDoMes(QtdeDiaMes*24*60);
								novoReparo.setDuracaoReparo(formTemRepMin(novoReparo, deltaDuracaoReparo));
								// CALCULO DO DESCONTO
								calcularDesconto(novoReparo);
								
								colTempoReparos.add(novoReparo);
							}
						}
					}
					// Circuitos n�o encontrados na cole��o
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
					"ERRO: Circuitos n�o encontrados na Base de Dados: " + circuitos_nao_encontrados);
		}
		
		return 0;
	}
	
	public void calcularESetarTotalReparo() {
		double total = 0.0;
		for (TempoDeReparo circuito : colTempoReparos) {
			total = total + circuito.getDescontoReparo();
		}

		for (TempoDeReparo circuito : colTempoReparos) {
			circuito.setTotalDescontoReparo(total);
		}
	}
	//N�O UTILIZADO MAIS
	//AQUI RECEBO A DATA NO SEGUINTE PADR�O: 00 Dias; hh:mm:ss
	//Essa � a dura��o do reparo
	@SuppressWarnings("unused")
	private String somarHoras(String horaReparoAtual, String horaReparoContabilizar){
		//Pegando os dias das dura��es de reparos
		int diaAntes = Integer.parseInt(horaReparoAtual.substring(0, 2));
		int diaNovo = Integer.parseInt(horaReparoContabilizar.substring(0, 2));
		
		//Pegando as Horas das dura��es de reparos
		int horaAntes = Integer.parseInt(horaReparoAtual.substring(9, 11));
		int horaNova = Integer.parseInt(horaReparoContabilizar.substring(9, 11));
		
		//Pegando os minutos das dura��es de reparo
		int minutoAntes = Integer.parseInt(horaReparoAtual.substring(12, 14));
		int minutoNovo = Integer.parseInt(horaReparoContabilizar.substring(12, 14));
		
		//Pegando os segundos das dura��es de reparo
		int segundoAntes = Integer.parseInt(horaReparoAtual.substring(15, 17));
		int segundoNovo = Integer.parseInt(horaReparoContabilizar.substring(15, 17));

		//CONTABILIZANDO
		int diaContabilizado = diaAntes+diaNovo;
		int horaContabilizada = horaAntes+horaNova;
		int minutoContabilizado = minutoAntes+minutoNovo;
		int segundoContabilizado = segundoAntes+segundoNovo;
		
		if (segundoContabilizado > 59) {
			segundoContabilizado = segundoContabilizado-60;
			minutoContabilizado ++;
		}
		
		if (minutoContabilizado > 59) {
			minutoContabilizado = minutoContabilizado-60;
			horaContabilizada++;
		}
		
		//AQUI CONVERTO O INT PARA STRING USANDO O CONSTRUTOR DE STRING E COLOCANDO UM 0 ONDE OS N�MEROS FOR MENOR QUE 10 PARA QUE FIQUE NO SEGUINTE PADR�O: hh:mm:ss
		StringBuilder stringBuilder = new StringBuilder();
		
		if (diaContabilizado < 10)
			stringBuilder.append("0"+diaContabilizado + " Dias. ");
		else
			stringBuilder.append(diaContabilizado + " Dias. ");
		
		if (horaContabilizada < 10) 
			stringBuilder.append("0"+horaContabilizada);
		else 	
			stringBuilder.append(horaContabilizada);
		
		stringBuilder.append(":");
		
		if (minutoContabilizado < 10)
			stringBuilder.append("0"+minutoContabilizado);
		else
			stringBuilder.append(minutoContabilizado);
		
		stringBuilder.append(":");
		
		if (segundoContabilizado < 10)
			stringBuilder.append("0"+segundoContabilizado);
		else
			stringBuilder.append(segundoContabilizado);
		
		return stringBuilder.toString();
	}
	private void calcularDesconto(TempoDeReparo reparo) {
		// CALCULO DO DESCONTO
		double descontoReparo=0.0;
		if(reparo.getSlaHora() == 3) {
			if (reparo.getDuracaoReparo() > reparo.getSlaMin()) {
				if (reparo.getDuracaoReparo() <= 300) {
					//PAGA 95% DO LINK - ENTRE 3h e 5h
					descontoReparo=reparo.getValor()-(reparo.getValor()*0.95);
					reparo.setDescontoReparo(descontoReparo);
				} else if (reparo.getDuracaoReparo() > 300 && reparo.getDuracaoReparo() <= 480) {
					//PAGA 90% DO LINK - ENTRE 5h e 8h
					descontoReparo=reparo.getValor()-(reparo.getValor()*0.90);
					reparo.setDescontoReparo(descontoReparo);
				} else {
					//PAGA 85% DO LINK - Maior que 8h
					descontoReparo=reparo.getValor()-(reparo.getValor()*0.85);
					reparo.setDescontoReparo(descontoReparo);
				}
					
			} else 
				//PAGA 100% DO LINK - MENOR QUE 3h
				reparo.setDescontoReparo(descontoReparo);
			
		} else if (reparo.getSlaHora() == 6) {
			if (reparo.getDuracaoReparo() > reparo.getSlaMin()) {
				if (reparo.getDuracaoReparo() <= 480) {
					//PAGA 95% DO LINK - ENTRE 6h e 8h
					descontoReparo=reparo.getValor()-(reparo.getValor()*0.95);
					reparo.setDescontoReparo(descontoReparo);
				} else if (reparo.getDuracaoReparo() > 480 && reparo.getDuracaoReparo() <= 600) {
					//PAGA 90% DO LINK - ENTRE 8h e 10h
					descontoReparo=reparo.getValor()-(reparo.getValor()*0.90);
					reparo.setDescontoReparo(descontoReparo);
				} else {
					//PAGA 85% DO LINK - Maior que 10h
					descontoReparo=reparo.getValor()-(reparo.getValor()*0.85);
					reparo.setDescontoReparo(descontoReparo);
				}
					
			} else 
				//PAGA 100% DO LINK - MENOR QUE 6h
				reparo.setDescontoReparo(descontoReparo);
		}
	}
	
	/*
	 * API JODA TIME
	 */
	//N�O UTILIZADO MAIS - DEIXADO COMO APRENDIZADO.
	public String retornaDuracaoReparo(String dataInicioReparo, String dataFimReparo) {
		//String dataInicioReparo = "2019-06-04 12:05:00";
		//String dataFimReparo = "2019-06-08 16:15:10";
		
		//FORMATO DA DATA LIDA - OTRS
		SimpleDateFormat formatEua = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//DATAS INFORMADAS ACIMA PARA O TRY/CATCH
		Date date1 = null;
		Date date2 = null;
		
		try {
			date1 = formatEua.parse(dataInicioReparo);
			date2 = formatEua.parse(dataFimReparo);
			
			DateTime dt1 = new DateTime(date1);
			DateTime dt2 = new DateTime(date2);
			
			StringBuilder stringDuracaoReparo = new StringBuilder();
			
			int diaInt=Days.daysBetween(dt1, dt2).getDays();
			int horaInt=Hours.hoursBetween(dt1, dt2).getHours() % 24;
			int minutoInt=Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
			int segundoInt=Seconds.secondsBetween(dt1, dt2).getSeconds() % 60;
			
	
			//OS PR�XIMOS IF'S � PARA COLOCAR O HOR�RIO NO PADR�O: hh:mm:ss
			if (diaInt < 10)
				stringDuracaoReparo.append("0"+diaInt+" Dias. ");
			else
				stringDuracaoReparo.append(horaInt+" Dias. ");
			
			if (horaInt < 10)
				stringDuracaoReparo.append("0"+horaInt+":");
			else
				stringDuracaoReparo.append(horaInt+":");
			
			if (minutoInt < 10)
				stringDuracaoReparo.append("0"+minutoInt+":");
			else
				stringDuracaoReparo.append(minutoInt+":");
			
			if (segundoInt < 10)
				stringDuracaoReparo.append("0"+segundoInt);
			else
				stringDuracaoReparo.append(segundoInt);

			return stringDuracaoReparo.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ERRO SE RETORNAR NULL, N�O ENTROU NO TRY
		return null;	
	}
	
	// FORMULA PARA CALCULAR A DURA��O DO REPARO LEVANDO EM CONSIDERA��O O SLA E O DELTA COLETADO NA PLANILHA.
	private int formTemRepMin(TempoDeReparo reparo, int delta) {
		return reparo.getSlaMin()+((-1)*delta);
	}
	
	private int returnQtdeDiaMes() {
		Calendar calendario = Calendar.getInstance();
		
		//Modificando a data de acordo com o informado na ViewMesReferencia.
		calendario.set(Calendar.YEAR, Integer.parseInt(this.anoReferencia));
		try {
			calendario.set(Calendar.MONTH, converteMesEmInt(this.mesReferencia));
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, "Erro ao setar o Mês em ColTempoReparo, Função: returnQtdeDiaMes", "Set Mês ColTempoReparo", JOptionPane.INFORMATION_MESSAGE);
		    e.printStackTrace(); //Printa o erro.
		    return 1;
		}
		
		// Retorna a quantidade de dias no m�s informado
		return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	private int converteMesEmInt(String mes) {
		switch (mes.toUpperCase()){
		case "JANEIRO":
			return 0;
		case "FEVEREIRO":
			return 1;
		case "MAR�O":
			return 2;
		case "ABRIL":
			return 3;
		case "MAIO":
			return 4;
		case "JUNHO":
			return 5;
		case "JULHO":
			return 6;
		case "AGOSTO":
			return 7;
		case "SETEMBRO":
			return 8;
		case "OUTUBRO":
			return 9;
		case "NOVEMBRO":
			return 10;
		case "DEZEMBRO":
			return 11;
		}
		
		//O retorno 12 significa que o m�s n�o � nenhuma das strings testadas acima. � um erro.
		return 12;
	}
	
	private int pegaMesAnoReferencia(){
		if (this.mesReferencia.isEmpty() || this.anoReferencia.isEmpty()) {
            ViewMesReferencia obj = new ViewMesReferencia();
            obj.setVisible(true);
			obj.setLocationRelativeTo(null);
		
			if (obj.getMesSelecionado() == null || obj.getAnoSelecionado() == null) {
				return 1;
			}
			else {
				this.mesReferencia=obj.getMesSelecionado();
				this.anoReferencia=obj.getAnoSelecionado();
			}
		}
		//Se o usu�rio n�o cancelar retornar� 0
		return 0;
	}
	
	@Override
	public String toString() {
		return "ColTempoReparo [colTempoReparos=" + colTempoReparos + "]";
	}
}
