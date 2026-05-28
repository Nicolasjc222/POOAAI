package dao;

import java.sql.*;
import java.util.*;

import model.PessoaJuridica;

public class PessoaJuridicaDAO implements ICrudDAO<PessoaJuridica> {

    private Connection conn;

    public PessoaJuridicaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(PessoaJuridica pessoaJuridica) throws SQLException {
        String sql = "INSERT INTO pj (cnpj_cliente, razao_social, id_cliente) VALUES (?, ?, ?)"; // trocara para pj
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pessoaJuridica.getCnpj());
        stmt.setString(2, pessoaJuridica.getRazaoSocial());
        stmt.setInt(3, pessoaJuridica.getIdCliente());

        stmt.executeUpdate();
        stmt.close();
    }

    public List<PessoaJuridica> listar() throws SQLException {
        List<PessoaJuridica> lista = new ArrayList<>();
        String sql = "SELECT c.*, pj.* FROM pj JOIN cliente c ON pj.id_cliente = c.id_cliente";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
        	PessoaJuridica pessoaJuridica = new PessoaJuridica();
        	
        	pessoaJuridica.setCnpj(rs.getString("cnpj_cliente"));
        	pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
        	
        	pessoaJuridica.setIdCliente(rs.getInt("id_cliente"));
        	pessoaJuridica.setTelefone(rs.getString("telefone_cliente"));
        	pessoaJuridica.setEmail(rs.getString("email_cliente"));
            lista.add(pessoaJuridica);
        }

        return lista;
    }
    
    public PessoaJuridica buscarPorId(int id) throws SQLException {
        String sql = "SELECT c.*, pj.* FROM pj JOIN cliente c ON pj.id_cliente = c.id_cliente WHERE pj.id_cliente = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
        	PessoaJuridica pessoaJuridica = new PessoaJuridica();
        	
        	pessoaJuridica.setCnpj(rs.getString("cnpj_cliente"));
        	pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
        	
        	pessoaJuridica.setIdCliente(rs.getInt("id_cliente"));
        	pessoaJuridica.setTelefone(rs.getString("telefone_cliente"));
        	pessoaJuridica.setEmail(rs.getString("email_cliente"));
            return pessoaJuridica;
        }

        return null;
    }
    
    public void atualizar(PessoaJuridica pessoaJuridica) throws SQLException {
        String sql = "UPDATE pj SET cnpj_cliente = ?, razao_social = ? WHERE id_cliente = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pessoaJuridica.getCnpj());
        stmt.setString(2, pessoaJuridica.getRazaoSocial());
        stmt.setInt(3, pessoaJuridica.getIdCliente());
        stmt.executeUpdate();
        stmt.close();
    }

	public void deletar(int id) throws SQLException {
		// not used
	}
    
    
    
}

