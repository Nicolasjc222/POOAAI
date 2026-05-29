package controller;

import model.Conexao;
import model.PessoaFisica;

import java.util.List;

import dao.PessoaFisicaDAO;

public class PessoaFisicaController {
    private PessoaFisicaDAO dao;
	private PessoaFisica pessoaFisica;
	
	public PessoaFisicaController(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}
	public PessoaFisicaController() {
	}
	
    public void salvarPessoaFisica() {
        try {
        	Conexao.conectar();
            dao = new PessoaFisicaDAO(Conexao.conexao);
            dao.inserir(pessoaFisica);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
    public void atualizarPessoaFisica() {
        try {
        	Conexao.conectar();
            dao = new PessoaFisicaDAO(Conexao.conexao);
            dao.atualizar(pessoaFisica);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
    public PessoaFisica procurarPessoaFisica() {
        try {
            Conexao.conectar();
            dao = new PessoaFisicaDAO(Conexao.conexao);
            return dao.buscarPorId(pessoaFisica.getIdCliente());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }

    public List<PessoaFisica> listarPessoaFisica() {
        try {
            Conexao.conectar();
            dao = new PessoaFisicaDAO(Conexao.conexao);
            return dao.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }
}

