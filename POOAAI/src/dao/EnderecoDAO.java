package dao;

import java.sql.*;
import java.util.*;

import model.Endereco;

public class EnderecoDAO implements ICrudDAO<Endereco> {

	private Connection conn;

	public EnderecoDAO(Connection conn) {
		this.conn = conn;
	}

	public void inserir(Endereco endereco) throws SQLException {
		String sql = "INSERT INTO endereco (rua_endereco, bairro_endereco, cidade_endereco, UF_endereco, CEP_endereco, numero_endereco, complemento_endereco) VALUES (?, ?, ?, ?, ?, ?, ?)";// trocara para endereco
		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, endereco.getRua());
		stmt.setString(2, endereco.getBairro());
		stmt.setString(3, endereco.getCidade());
		stmt.setString(4, endereco.getUf());
		stmt.setString(5, endereco.getCep());
		stmt.setInt(6, endereco.getNumero());
		stmt.setString(7, endereco.getComplemento());

		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next()) {
			endereco.setIdEndereco(rs.getInt(1));
		}
		rs.close();
		stmt.close();
	}

	public List<Endereco> listar() throws SQLException {
		List<Endereco> lista = new ArrayList<>();
		String sql = "SELECT * FROM endereco";

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Endereco endereco = new Endereco();
			endereco.setIdEndereco(rs.getInt("id_endereco"));
			endereco.setRua(rs.getString("rua_endereco"));
			endereco.setBairro(rs.getString("bairro_endereco"));
			endereco.setCidade(rs.getString("cidade_endereco"));
			endereco.setUf(rs.getString("UF_endereco"));
			endereco.setCep(rs.getString("CEP_endereco"));
			endereco.setNumero(rs.getInt("numero_endereco"));
			endereco.setComplemento(rs.getString("complemento_endereco"));

			lista.add(endereco);
		}

		return lista;
	}

	public Endereco buscarPorId(int id) throws SQLException {
		String sql = "SELECT * FROM endereco WHERE id_endereco = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			Endereco endereco = new Endereco();
			endereco.setIdEndereco(rs.getInt("id_endereco"));
			endereco.setRua(rs.getString("rua_endereco"));
			endereco.setBairro(rs.getString("bairro_endereco"));
			endereco.setCidade(rs.getString("cidade_endereco"));
			endereco.setUf(rs.getString("UF_endereco"));
			endereco.setCep(rs.getString("CEP_endereco"));
			endereco.setNumero(rs.getInt("numero_endereco"));
			endereco.setComplemento(rs.getString("complemento_endereco"));
			return endereco;
		}

		return null;
	}

	public void atualizar(Endereco endereco) throws SQLException {
		String sql = "UPDATE endereco SET rua_endereco = ?, bairro_endereco = ?, cidade_endereco = ?, UF_endereco = ?, CEP_endereco = ?, numero_endereco = ?, complemento_endereco = ? WHERE id_endereco = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, endereco.getRua());
		stmt.setString(2, endereco.getBairro());
		stmt.setString(3, endereco.getCidade());
		stmt.setString(4, endereco.getUf());
		stmt.setString(5, endereco.getCep());
		stmt.setInt(6, endereco.getNumero());
		stmt.setString(7, endereco.getComplemento());
		stmt.setInt(8, endereco.getIdEndereco());
		stmt.executeUpdate();
		stmt.close();
	}

	public void deletar(int id) throws SQLException {
		String sql = "DELETE FROM endereco WHERE id_endereco = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		stmt.close();
	}
}