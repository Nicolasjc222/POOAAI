package controller;

import model.Conexao;
import model.Endereco;

import java.util.*;

import dao.EnderecoDAO;

public class EnderecoController {
	private EnderecoDAO dao;
	private Endereco endereco;

	public EnderecoController(Endereco endereco) {
		this.endereco = endereco;
	}

	public void salvarEndereco() {
		try {
			Conexao.conectar();
			dao = new EnderecoDAO(Conexao.conexao);
			dao.inserir(endereco);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}

	public void deletarEndereco() {
		try {
			Conexao.conectar();
			dao = new EnderecoDAO(Conexao.conexao);
			dao.deletar(endereco.getIdEndereco());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}

	public void atualizarEndereco() {
		try {
			Conexao.conectar();
			dao = new EnderecoDAO(Conexao.conexao);
			dao.atualizar(endereco);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
	public Endereco procurarEndereco() {
        try {
            Conexao.conectar();
            dao = new EnderecoDAO(Conexao.conexao);
            return dao.getEndereco(endereco.getIdEndereco());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }

    public List<Endereco> listarEnderecos() {
        try {
            Conexao.conectar();
            dao = new EnderecoDAO(Conexao.conexao);
            return dao.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }
}

