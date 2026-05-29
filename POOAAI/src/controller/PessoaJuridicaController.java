package controller;

import model.Conexao;
import model.PessoaJuridica;

import java.util.List;

import dao.PessoaJuridicaDAO;

public class PessoaJuridicaController {
    private PessoaJuridicaDAO dao;
	private PessoaJuridica pessoaJuridica;
	
	public PessoaJuridicaController(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	public PessoaJuridicaController() {
	}
	
    public void salvarPessoaJuridica() {
        try {
        	Conexao.conectar();
            dao = new PessoaJuridicaDAO(Conexao.conexao);
            dao.inserir(pessoaJuridica);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
    public void atualizarPessoaJuridica() {
        try {
        	Conexao.conectar();
            dao = new PessoaJuridicaDAO(Conexao.conexao);
            dao.atualizar(pessoaJuridica);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
    public PessoaJuridica procurarPessoaJuridica() {
        try {
            Conexao.conectar();
            dao = new PessoaJuridicaDAO(Conexao.conexao);
            return dao.buscarPorId(pessoaJuridica.getIdCliente());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }

    public List<PessoaJuridica> listarPessoaJuridica() {
        try {
            Conexao.conectar();
            dao = new PessoaJuridicaDAO(Conexao.conexao);
            return dao.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }
}

