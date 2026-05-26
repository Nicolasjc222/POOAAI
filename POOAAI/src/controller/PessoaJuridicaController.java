package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Conexao;
import model.PessoaJuridica;
import dao.PessoaJuridicaDAO;

public class PessoaJuridicaController {
    private PessoaJuridicaDAO dao;
	private PessoaJuridica pessoaJuridica;
	
	public PessoaJuridicaController(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	
    @FXML
    public void salvarPessoaJuridica() {
        try {
        	Conexao.conectar(); // sua classe de conexão
            dao = new PessoaJuridicaDAO(Conexao.conexao);
            dao.inserir(pessoaJuridica);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pessoa jurídica salva com sucesso!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
}

