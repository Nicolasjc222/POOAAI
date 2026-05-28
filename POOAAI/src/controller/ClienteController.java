package controller;

import java.util.*;
import model.Conexao;
import model.Cliente;
import dao.ClienteDAO;

public class ClienteController {
    private ClienteDAO dao;
    private Cliente cliente;

    public ClienteController(Cliente cliente) {
        this.cliente = cliente;
    }

    public void salvarCliente() {
        try {
            Conexao.conectar();
            dao = new ClienteDAO(Conexao.conexao);
            dao.inserir(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexao.desconectar();
        }
    }

    public void deletarCliente() {
        try {
            Conexao.conectar();
            dao = new ClienteDAO(Conexao.conexao);
            dao.deletar(cliente.getIdCliente());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexao.desconectar();
        }
    }

    public void atualizarCliente() {
        try {
            Conexao.conectar();
            dao = new ClienteDAO(Conexao.conexao);
            dao.atualizar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexao.desconectar();
        }
    }

    public Cliente procurarCliente() {
        try {
            Conexao.conectar();
            dao = new ClienteDAO(Conexao.conexao);
            return dao.buscarPorId(cliente.getIdCliente());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }

    public List<Cliente> listarClientes() {
        try {
            Conexao.conectar();
            dao = new ClienteDAO(Conexao.conexao);
            return dao.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar();
        }
    }
}