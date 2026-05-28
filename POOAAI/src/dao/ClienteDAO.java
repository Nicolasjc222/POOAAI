package dao;

import java.sql.*;
import java.util.*;

import model.Cliente;
import model.Endereco;
import model.PessoaFisica;
import model.PessoaJuridica;

public class ClienteDAO implements ICrudDAO<Cliente> {

    private Connection conn;
    private EnderecoDAO enderecoDAO;

    public ClienteDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (telefone_cliente, email_cliente, id_endereco, tipo_cliente) VALUES (?, ?, ?, ?)"; // trocara para cliente
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        stmt.setString(1, cliente.getTelefone());
        stmt.setString(2, cliente.getEmail());
        stmt.setInt(3, cliente.getEndereco().getIdEndereco());
        stmt.setString(4, cliente.getTipoCliente());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()) {
        	cliente.setIdCliente(rs.getInt(1));
        }
        rs.close();
        stmt.close();
    }
    
    public List<Cliente> listar() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
        	String tipo = rs.getString("tipo_cliente");
        	Cliente cliente;
        	if(tipo.equals("PF")) {
        		cliente = new PessoaFisica();
        	} else {
        		cliente = new PessoaJuridica();
        	}
        	cliente.setIdCliente(rs.getInt("id_cliente"));
        	cliente.setTelefone(rs.getString("telefone_cliente"));
        	cliente.setEmail(rs.getString("email_cliente"));
        	cliente.setTipoCliente(rs.getString("tipo_cliente"));
        	enderecoDAO = new EnderecoDAO(conn);
        	Endereco endereco = enderecoDAO.buscarPorId(rs.getInt("id_endereco"));
        	cliente.setEndereco(endereco);
            lista.add(cliente);
        }

        return lista;
    }
    
    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
        	String tipo = rs.getString("tipo_cliente");
        	Cliente cliente;
        	if(tipo.equals("PF")) {
        		cliente = new PessoaFisica();
        	} else {
        		cliente = new PessoaJuridica();
        	}
        	cliente.setIdCliente(rs.getInt("id_cliente"));
        	cliente.setTelefone(rs.getString("telefone_cliente"));
        	cliente.setEmail(rs.getString("email_cliente"));
        	cliente.setTipoCliente(rs.getString("tipo_cliente"));
        	enderecoDAO = new EnderecoDAO(conn);
        	Endereco endereco = enderecoDAO.buscarPorId(rs.getInt("id_endereco"));
        	cliente.setEndereco(endereco);
            return cliente;
        }

        return null;
    }
    
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET telefone_cliente = ?, email_cliente = ?, id_endereco = ? WHERE id_cliente = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cliente.getTelefone());
        stmt.setString(2, cliente.getEmail());
        stmt.setInt(3, cliente.getEndereco().getIdEndereco());
        stmt.setInt(4, cliente.getIdCliente());
        stmt.executeUpdate();
        stmt.close();
    }
    
    public void deletar(int id) throws SQLException {
    	String sql = "DELETE FROM cliente WHERE id_cliente = ?";
    	PreparedStatement stmt = conn.prepareStatement(sql);
    	stmt.setInt(1, id);
    	stmt.executeUpdate();
    	stmt.close();
    }
    
}

