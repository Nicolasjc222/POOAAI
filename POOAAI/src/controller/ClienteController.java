package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Conexao;
import model.Cliente;
import dao.ClienteDAO;

public class ClienteController {
    private ClienteDAO dao;
	private Cliente cliente;
	
	public ClienteController(Cliente cliente) {
		this.cliente = cliente;
	}
	
    @FXML
    public void salvarCliente() {
        try {
        	Conexao.conectar(); // sua classe de conexão
            dao = new ClienteDAO(Conexao.conexao);
            dao.inserir(cliente);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cliente salvo com sucesso!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
}

