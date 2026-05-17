package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Conexao;
import model.Endereco;
import dao.EnderecoDAO;

public class EnderecoController {
    private EnderecoDAO dao;
	private Endereco endereco;
	
	public EnderecoController(Endereco endereco) {
		this.endereco = endereco;
	}
	
    @FXML
    public void salvarEndereco() {
        try {
        	Conexao.conectar(); // sua classe de conexão
            dao = new EnderecoDAO(Conexao.conexao);
            dao.inserir(endereco);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Imovel salvo com sucesso!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
}

