package controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Conexao;
import model.Imovel;
import dao.ImovelDAO;

public class ImovelController {
    private ImovelDAO dao;
	private Imovel imovel;
	
	public ImovelController(Imovel imovel) {
		this.imovel = imovel;
	}
	
    @FXML
    public void salvarImovel() {
        try {
        	Conexao.conectar(); // sua classe de conexão
            dao = new ImovelDAO(Conexao.conexao);
            dao.inserir(imovel);

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

