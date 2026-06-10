package model;

public class PessoaFisica extends Cliente {
	private String nome;
	private String cpf;

	public PessoaFisica(String nome, String cpf) {
		super();
		this.nome = nome;
		this.cpf = cpf;
	}

	public PessoaFisica() {
		super();
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


}