package controller;

import java.util.*;

import model.Conexao;
import model.Imovel;
import dao.ImovelDAO;

public class ImovelController {
    private ImovelDAO dao;
	private Imovel imovel;
	
	public ImovelController(Imovel imovel) {
		this.imovel = imovel;
	}
	
    public void salvarImovel() {
        try {
        	Conexao.conectar();
            dao = new ImovelDAO(Conexao.conexao);
            dao.inserir(imovel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
    public void deletarImovel() {
    	 try {
         	Conexao.conectar();
             dao = new ImovelDAO(Conexao.conexao);
             dao.deletar(imovel.getIdImovel());

         } catch (Exception e) {
             e.printStackTrace();
         } finally {
         	Conexao.desconectar();
         }
    }
    public void atualizarImovel() {
    	try {
         	Conexao.conectar();
             dao = new ImovelDAO(Conexao.conexao);
             dao.atualizar(imovel);

         } catch (Exception e) {
             e.printStackTrace();
         } finally {
         	Conexao.desconectar();
         }
    }
    
    public Imovel procurarImovel() {
        try {
            Conexao.conectar();
            dao = new ImovelDAO(Conexao.conexao);
            return dao.buscarPorId(imovel.getIdImovel());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }

    public List<Imovel> listarImoveis() {
        try {
            Conexao.conectar();
            dao = new ImovelDAO(Conexao.conexao);
            return dao.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }
}

