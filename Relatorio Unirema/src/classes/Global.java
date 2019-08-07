package classes;

public class Global extends Circuito{
	String totalIndisponibilidade;
	String protocolo;
	String expediente;
	
	int qtdeReparos = 0;
	int totalIndisponibilidadeMinutos = 0;
	int totalMinDoMes = 0; //ESTÁ RECEBENDO DO REPARO EM ColGlobal.
	int valorPagarPercentual = 0;
	
	double disponibilidadePercentual = 0.0;
	double valorDesconto = 0.0;
	
	//Informação pertinente ao somatório dos descontos
	double totalDesconto = 0.0;
	
	//Variaveis para a referencia do relatório
	private String mesReferencia;
	private String anoReferencia;
	
	public String getTotalIndisponibilidade() {
		return totalIndisponibilidade;
	}
	public void setTotalIndisponibilidade(String totalIndisponibilidade) {
		this.totalIndisponibilidade = totalIndisponibilidade;
	}
	public int getQtdeReparos() {
		return qtdeReparos;
	}
	public void setQtdeReparos(int qtdeReparos) {
		this.qtdeReparos = qtdeReparos;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public int getTotalIndisponibilidadeMinutos() {
		return totalIndisponibilidadeMinutos;
	}
	public void setTotalIndisponibilidadeMinutos(int totalIndisponibilidadeMinutos) {
		this.totalIndisponibilidadeMinutos = totalIndisponibilidadeMinutos;
	}
	public int getTotalMinDoMes() {
		return totalMinDoMes;
	}
	public void setTotalMinDoMes(int totalMinDoMes) {
		this.totalMinDoMes = totalMinDoMes;
	}
	public double getDisponibilidadePercentual() {
		return disponibilidadePercentual;
	}
	public void setDisponibilidadePercentual(double disponibilidadePercentual) {
		this.disponibilidadePercentual = disponibilidadePercentual;
	}
	public int getValorPagarPercentual() {
		return valorPagarPercentual;
	}
	public void setValorPagarPercentual(int valorPagarPercentual) {
		this.valorPagarPercentual = valorPagarPercentual;
	}
	public double getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public double getTotalDesconto() {
		return totalDesconto;
	}
	public void setTotalDesconto(double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
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
