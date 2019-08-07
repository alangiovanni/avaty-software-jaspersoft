package classes;

public class Circuito {
	//Atributos
	private String circuito;
	private String localizacao;
	private String tecnologia;
	private String grupo;
	private String local;
	private String bandaContratada;
	private int latencia;
	private double valor;
	private double desconto;
	private double total;
	
	//Métodos - Getters and Setters
	public String getCircuito() {
		return circuito;
	}
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public String getTecnologia() {
		return tecnologia;
	}
	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getBandaContratada() {
		return bandaContratada;
	}
	public void setBandaContratada(String bandaContratada) {
		this.bandaContratada = bandaContratada;
	}
	public int getLatencia() {
		return latencia;
	}
	public void setLatencia(int latencia) {
		this.latencia = latencia;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
