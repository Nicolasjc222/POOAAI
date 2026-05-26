package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Conexao;
import model.PessoaFisica;
import dao.PessoaFisicaDAO;

public class PessoaFisicaController {
    private PessoaFisicaDAO dao;
	private PessoaFisica pessoaFisica;
	
	public PessoaFisicaController(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}
	
    @FXML
    public void salvarPessoaFisica() {
        try {
        	Conexao.conectar(); // sua classe de conexão
            dao = new PessoaFisicaDAO(Conexao.conexao);
            dao.inserir(pessoaFisica);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pessoa física salva com sucesso!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
}

