package colecoes;

import java.util.ArrayList;

import classes.Global;
import classes.TempoDeReparo;

public class ColGlobal {
	//Atributos
	private ArrayList<Global> colGlobal = new ArrayList<Global>();
	
	//Constructor
	public ColGlobal(){
		
	}
		
	//Métodos
	public ArrayList<Global> retornaLista(){
		return colGlobal;
	}
	public void adicionaAoGlobal(Global circuito){
		colGlobal.add(circuito);
	}
	
	public void removeDoGlobal(Global circuito){
		colGlobal.remove(circuito);
	}
	
	public int retornaTamanhoDaLista() {
		return colGlobal.size();
	}
	
	public void começarDoZero() {
		System.out.println("DISPONIBILIDADE GLOBAL");
		System.out.println("Tamanho ANTES da execução do Protocolo: "+ colGlobal.size());
		colGlobal.removeAll(colGlobal);
		System.out.println("Tamanho APÓS execução do protocolo: " + colGlobal.size());
	}
	
	public void populaGlobal(ColTempoReparo reparos) {
		for (TempoDeReparo reparo : reparos.retornaLista()) {
			Global novoCircuitoGlobal = new Global();
			novoCircuitoGlobal.setCircuito(reparo.getCircuito());
			novoCircuitoGlobal.setLocalizacao(reparo.getLocalizacao());
			novoCircuitoGlobal.setTecnologia(reparo.getTecnologia());
			novoCircuitoGlobal.setGrupo(reparo.getGrupo());
			novoCircuitoGlobal.setLocal(reparo.getLocal());
			novoCircuitoGlobal.setBandaContratada(reparo.getBandaContratada());
			novoCircuitoGlobal.setValor(reparo.getValor());
			novoCircuitoGlobal.setTotalIndisponibilidade(reparo.getDuracaoReparoEmHoras());
			novoCircuitoGlobal.setQtdeReparos(reparo.getQtdeReparos());
			novoCircuitoGlobal.setProtocolo(reparo.getProtocolos());
			novoCircuitoGlobal.setTotalIndisponibilidadeMinutos(reparo.getDuracaoReparo());
			novoCircuitoGlobal.setTotalMinDoMes(reparo.getTotalMinDoMes());
			novoCircuitoGlobal.setDisponibilidadePercentual(reparo.getDisponibilidadePorcentagem());
			novoCircuitoGlobal.setExpediente(reparo.getExpediente());
			novoCircuitoGlobal.setMesReferencia(reparo.getMesReferencia());
			novoCircuitoGlobal.setAnoReferencia(reparo.getAnoReferencia());
			
			//SETAR A PORCENTAGEM A PAGAR
			novoCircuitoGlobal.setValorPagarPercentual(returnPorcentagemPagar(novoCircuitoGlobal));
			//SETAR O DESCONTO
			novoCircuitoGlobal.setValorDesconto(returnValorDesconto(novoCircuitoGlobal));
			
			//Adicionando o novo Registro Global do circuito a coleção
			colGlobal.add(novoCircuitoGlobal);
		}
	}
	
	public void calcularESetarTotalDesconto() {
		double totalDesconto = 0.0;
		for (Global registro : colGlobal) {
			totalDesconto = totalDesconto + registro.getValorDesconto();
		}

		for (Global circuito : colGlobal) {
			circuito.setTotalDesconto(totalDesconto);
		}
	}
	
	private int returnPorcentagemPagar(Global global) {
		double disponibidadeMesPercentual=global.getDisponibilidadePercentual();
		String tecnologia=global.getTecnologia();
		int porcentagemPagar=0;
		//Se Não conter "SAT" na tecnologia então é terrestre
		if (!(tecnologia.contains("SAT"))) {
			//Disponibilidade Entre 99.5 e 100, retorna 100
			if (disponibidadeMesPercentual >= 99.50)
				porcentagemPagar=100;
			//Disponibilidade entre 99.4 e 98, retorna 98
			else if ((disponibidadeMesPercentual < 99.5) && (disponibidadeMesPercentual >= 98.00))
				porcentagemPagar=98;
			//Disponibilidade entre 97.9 e 96, retorna 96
			else if ((disponibidadeMesPercentual < 98.00) && (disponibidadeMesPercentual >= 96.00))
				porcentagemPagar=96;
			//Disponibilidade abaixo de 95.9, retorna 90
			else if ((disponibidadeMesPercentual <= 95.90))
				porcentagemPagar=90;
			//SE Conter "SAT" na tecnologia então é link SATÉLITE
		} else {
			//Disponibilidade Entre 97 e 100, retorna 100
			if (disponibidadeMesPercentual >= 97.00)
				porcentagemPagar=100;
			//Disponibilidade entre 96.9 e 95, retorna 98
			else if ((disponibidadeMesPercentual < 97.00) && (disponibidadeMesPercentual >= 95.00))
				porcentagemPagar=98;
			//Disponibilidade entre 94.9 e 93, retorna 96
			else if ((disponibidadeMesPercentual < 95.00) && (disponibidadeMesPercentual >= 93.00))
				porcentagemPagar=96;
			//Disponibilidade abaixo de 92.9, retorna 90
			else if ((disponibidadeMesPercentual < 93.00))
				porcentagemPagar=90;
		}
	//Se retornar 0, então houve erro. Verifique a String tecnologia ou as condições nos IF's
		return porcentagemPagar;
	}
	
	private double returnValorDesconto(Global global) {
		int valorPercentualPagar=global.getValorPagarPercentual();
		double valorLink=global.getValor();
		
		//Esse calculo retornará o desconto.
		return (valorLink-((valorLink*valorPercentualPagar)/100));
	}
}
