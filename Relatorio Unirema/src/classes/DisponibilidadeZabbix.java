package classes;

public class DisponibilidadeZabbix extends Circuito{
	private String tipoIncidente;
	private double indisponibilidade;
	private double disponibilidade;
	private double mediaIndisponibilidade;
	private double mediaDisponibilidade;
	
	//Variaveis para a referencia do relatório
	private String mesReferencia;
	private String anoReferencia;
	
	public String getTipoIncidente() {
		return tipoIncidente;
	}
	public void setTipoIncidente(String tipoIncidente) {
		this.tipoIncidente = tipoIncidente;
	}
	public double getIndisponibilidade() {
		return indisponibilidade;
	}
	public void setIndisponibilidade(double indisponibilidade) {
		/*
		 * Os números da planilha vem em decimais. Ex: 0.04
		 * 0.04*100 = 4
		 * 4%
		 */
		this.indisponibilidade = (indisponibilidade*100);
	}
	public double getDisponibilidade() {
		return disponibilidade;
	}
	public void setDisponibilidade(double disponibilidade) {
		/*
		 * Os números da planilha vem em decimais. Ex: 0.04
		 * 0.04*100 = 4
		 * 4%
		 */
		this.disponibilidade = (disponibilidade*100);
	}
	public double getMediaIndisponibilidade() {
		return mediaIndisponibilidade;
	}
	public void setMediaIndisponibilidade(double mediaIndisponibilidade) {
		this.mediaIndisponibilidade = mediaIndisponibilidade;
	}
	public double getMediaDisponibilidade() {
		return mediaDisponibilidade;
	}
	public void setMediaDisponibilidade(double mediaDisponibilidade) {
		this.mediaDisponibilidade = mediaDisponibilidade;
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
