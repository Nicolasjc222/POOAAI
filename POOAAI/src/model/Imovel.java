package model;

public class Imovel {
	//Atributos
	private int idImovel;
	private Endereco endereco = new Endereco();
	private String tipoPropriedade; // mudar depois
	private int area;
	private int valor;
	private int comodos;
	private String finalidade;


	//Construtor
	public Imovel(Endereco endereco, String tipoPropriedade, int area, int valor, int comodos) {
		super();
		this.endereco = endereco;
		this.tipoPropriedade = tipoPropriedade;
		this.area = area;
		this.valor = valor;
		this.comodos = comodos;
	}

	public Imovel() {
		super();
	}

	//Getters e Setters

	public int getIdImovel() {
		return idImovel;
	}

	public void setIdImovel(int idImovel) {
		this.idImovel = idImovel;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTipoPropriedade() {
		return tipoPropriedade;
	}

	public void setTipoPropriedade(String tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getComodos() {
		return comodos;
	}

	public void setComodos(int comodos) {
		this.comodos = comodos;
	}

	// Adicione lá embaixo nos getters e setters:
	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

}
