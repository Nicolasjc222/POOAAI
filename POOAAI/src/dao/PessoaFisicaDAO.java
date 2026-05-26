package dao;

import java.sql.*;
import java.util.*;

import model.PessoaFisica;

public class PessoaFisicaDAO {

    private Connection conn;

    public PessoaFisicaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(PessoaFisica pessoaFisica) throws SQLException {
        String sql = "INSERT INTO pf (cpf_cliente, nome, id_cliente) VALUES (?, ?, ?)"; // trocara para pf
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pessoaFisica.getCpf());
        stmt.setString(2, pessoaFisica.getNome());
        stmt.setInt(3, pessoaFisica.getIdCliente());

        stmt.executeUpdate();
        stmt.close();
    }

    public List<PessoaFisica> listar() throws SQLException {
        List<PessoaFisica> lista = new ArrayList<>();
        String sql = "SELECT c.*, pf.* FROM pf JOIN cliente c ON pf.id_cliente = c.id_cliente";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
        	PessoaFisica pessoaFisica = new PessoaFisica();
        	
        	pessoaFisica.setCpf(rs.getString("cpf_cliente"));
        	pessoaFisica.setNome(rs.getString("nome"));
        	
        	pessoaFisica.setIdCliente(rs.getInt("id_cliente"));
        	pessoaFisica.setTelefone(rs.getInt("telefone_cliente"));
        	pessoaFisica.setEmail(rs.getString("email_cliente"));
            lista.add(pessoaFisica);
        }

        return lista;
    }
    
}

