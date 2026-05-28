package model;

public abstract class Cliente {
	private int idCliente; // necessario porque é foreign key
	private String telefone;
	private	String email;
	private	Endereco endereco = new Endereco();
	private String tipoCliente;
	
	public Cliente(int idCliente, String telefone, String email, Endereco endereco, String tipoCliente) {
		super();
		this.idCliente = idCliente;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.tipoCliente = tipoCliente;
	}
	
	public Cliente() {
		super();
	}
	
	public String getTipoCliente() {
		return tipoCliente;
	}
	
	public void setTipoCliente(String tipoCliente){
		this.tipoCliente = tipoCliente;
	}
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}