package classes;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TempoDeReparo extends Circuito{
	Calendar datas = new GregorianCalendar();
	
	String protocolos;
	String problema;
	String duracaoReparoEmHoras=null; //Manipulado após pegar a duração do Reparo
	String expediente;
	
	int qtdeReparos;
	int duracaoReparo;
	int totalMinDoMes; //Esta variável é manipulada na hora de pegar os reparos da planilha;
	int disponibilidadeReparoMin;
	int slaHora;
	int slaMin;
	
	double disponibilidadePorcentagem;
	double descontoReparo;
	double totalDescontoReparo;
	
	//Variaveis para a referencia do relatório
	private String mesReferencia;
	private String anoReferencia;

	
	//Getters and Setters
	public String getProtocolos() {
		return protocolos;
	}
	public void setProtocolos(String protocolos) {
		this.protocolos = protocolos;
	}
	public void concatProtocolo(String protocolo) {
		StringBuilder concatenaProcotolos = new StringBuilder();
		concatenaProcotolos.append(this.protocolos).append(" - ").append(protocolo);
		
		//setando os procotolos concatenados
		this.protocolos=concatenaProcotolos.toString();
	}
	public int getQtdeReparos() {
		return qtdeReparos;
	}
	public void setQtdeReparos(int qtdeReparos) {
		this.qtdeReparos = qtdeReparos;
	}
	public String getProblema() {
		return problema;
	}
	public void setProblema(String problema) {
		this.problema = problema;
	}
	public int getDuracaoReparo() {
		return duracaoReparo;
	}
	public void setDuracaoReparo(int duracaoReparo) {
		this.duracaoReparo = duracaoReparo;
		this.disponibilidadeReparoMin = this.totalMinDoMes - this.duracaoReparo;
		
		calculaReparoEmHoras(duracaoReparo); //Manipulo aqui a variável duracaoReparoEmHoras
		
		//Necessário a conversão de cada variável Inteira em Double para que o valor desse corretamente
		this.disponibilidadePorcentagem = (new Double (this.disponibilidadeReparoMin*100).doubleValue())/new Double (this.totalMinDoMes).doubleValue();
	}
	
	public int getTotalMinDoMes() {
		return totalMinDoMes;
	}
	public int getDisponibilidadeReparoMin() {
		return disponibilidadeReparoMin;
	}
	public double getDisponibilidadePorcentagem() {
		return disponibilidadePorcentagem;
	}
	public double getDescontoReparo() {
		return descontoReparo;
	}
	public void setDescontoReparo(double descontoReparo) {
		this.descontoReparo = descontoReparo;
	}
	public double getTotalDescontoReparo() {
		return totalDescontoReparo;
	}
	public void setTotalDescontoReparo(double totalDescontoReparo) {
		this.totalDescontoReparo = totalDescontoReparo;
	}
	public int getSlaHora() {
		return slaHora;
	}
	public void setSlaHora(int slaHora) {
		this.slaHora = slaHora;
	}
	public int getSlaMin() {
		return slaMin;
	}
	public void setSlaMin(int slaMin) {
		this.slaMin = slaMin;
	}
	public String getDuracaoReparoEmHoras() {
		return duracaoReparoEmHoras;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public void setDuracaoReparoEmHoras(String duracaoReparoEmHoras) {
		this.duracaoReparoEmHoras = duracaoReparoEmHoras;
	}
	public void setTotalMinDoMes(int totalMinDoMes) {
		this.totalMinDoMes = totalMinDoMes;
	}
	public void setDisponibilidadeReparoMin(int disponibilidadeReparoMin) {
		this.disponibilidadeReparoMin = disponibilidadeReparoMin;
	}
	public void setDisponibilidadePorcentagem(double disponibilidadePorcentagem) {
		this.disponibilidadePorcentagem = disponibilidadePorcentagem;
	}
	private void calculaReparoEmHoras(int duracaoReparo) {
		int dias = 0;
		int hora=duracaoReparo/60;
		int minuto=duracaoReparo-(hora*60);
		int duracaoReparoDias=hora/24;
		StringBuilder reparoEmHoras = new StringBuilder();
		
		if (duracaoReparoDias > 0) {
			hora=hora-(duracaoReparoDias*24);
		}
		
		//ACRESCENTANDO ZERO'S onde for menor que 10
		if (dias < 10)
			reparoEmHoras.append("0" + duracaoReparoDias+" Dias. ");
		else
			reparoEmHoras.append(duracaoReparoDias+" Dias. ");
		if (hora < 10)
			reparoEmHoras.append("0" + hora +":");
		else
			reparoEmHoras.append(hora +":");
		if (minuto < 10)
			reparoEmHoras.append("0" + minuto + ":00");
		else
			reparoEmHoras.append(minuto + ":00");
		
		this.duracaoReparoEmHoras = reparoEmHoras.toString();
	}
	
	public String getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	public String getAnoReferencia() {
		return anoReferencia;
	}
	public void setAnoReferencia(String anoReferencia) {
		this.anoReferencia = anoReferencia;
	}
}
