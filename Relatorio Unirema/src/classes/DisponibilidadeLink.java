package classes;

public class DisponibilidadeLink extends Circuito{
	private int disponibilidade;
	private int totalColetas;
	private int coletasAceitaveis;
	private double valorPagar;
	private double totalValorPagar;
	private double valorDesconto;
	private double totalValorDesconto;
	
	//Variaveis para a referencia do relatório
	private String mesReferencia;
	private String anoReferencia;
	
	//GETTERS AND SETTERS
	public int getDisponibilidade() {
		return disponibilidade;
	}
	public void setDisponibilidade(int disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	public double getValorPagar() {
		return valorPagar;
	}
	public void setValorPagar(double valorPagar) {
		this.valorPagar = valorPagar;
	}
	public double getTotalValorPagar() {
		return totalValorPagar;
	}
	public void setTotalValorPagar(double totalValorPagar) {
		this.totalValorPagar = totalValorPagar;
	}
	public int getTotalColetas() {
		return totalColetas;
	}
	public void setTotalColetas(int totalColetas) {
		this.totalColetas = totalColetas;
	}
	public int getColetasAceitaveis() {
		return coletasAceitaveis;
	}
	public void setColetasAceitaveis(int coletasAceitaveis) {
		this.coletasAceitaveis = coletasAceitaveis;
	}
	public double getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public double getTotalValorDesconto() {
		return totalValorDesconto;
	}
	public void setTotalValorDesconto(double totalValorDesconto) {
		this.totalValorDesconto = totalValorDesconto;
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
