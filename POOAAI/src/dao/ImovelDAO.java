package dao;

import java.sql.*;
import java.util.*;

import model.Imovel;
import model.Endereco;

public class ImovelDAO implements ICrudDAO<Imovel>{

	private Connection conn;
	private EnderecoDAO enderecoDAO;

	public ImovelDAO(Connection conn) {
		this.conn = conn;
	}

	public void inserir(Imovel imovel) throws SQLException {
		String sql = "INSERT INTO imovel (tipo_imovel, area_imovel, valor_imovel, comodos_imovel, finalidade, id_endereco, id_cliente) VALUES (?, ?, ?, ?, ?, ?, ?)"; // trocara para imvovel
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, imovel.getTipoPropriedade());
		stmt.setInt(2, imovel.getArea());
		stmt.setInt(3, imovel.getValor());
		stmt.setInt(4, imovel.getComodos());
		stmt.setString(5,imovel.getFinalidade());
		stmt.setInt(6, imovel.getEndereco().getIdEndereco());
		stmt.setInt(7, imovel.getIdCliente());

		stmt.executeUpdate();
		stmt.close();
	}

	public List<Imovel> listar() throws SQLException {
		List<Imovel> lista = new ArrayList<>();
		String sql = "SELECT * FROM imovel";

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Imovel imovel = new Imovel();
			imovel.setIdImovel(rs.getInt("id_imovel"));
			imovel.setTipoPropriedade(rs.getString("tipo_imovel"));
			imovel.setArea(rs.getInt("area_imovel"));
			imovel.setValor(rs.getInt("valor_imovel"));
			imovel.setComodos(rs.getInt("comodos_imovel"));
			imovel.setFinalidade(rs.getString("finalidade"));
			imovel.setIdCliente(rs.getInt("id_cliente"));
			enderecoDAO = new EnderecoDAO(conn);
			Endereco endereco = enderecoDAO.buscarPorId(rs.getInt("id_endereco"));
			imovel.setEndereco(endereco);

			lista.add(imovel);
		}

		return lista;
	}

	public Imovel buscarPorId(int id) throws SQLException {
		String sql = "SELECT * FROM imovel WHERE id_imovel = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			Imovel imovel = new Imovel();
			imovel.setIdImovel(rs.getInt("id_imovel"));
			imovel.setTipoPropriedade(rs.getString("tipo_imovel"));
			imovel.setArea(rs.getInt("area_imovel"));
			imovel.setValor(rs.getInt("valor_imovel"));
			imovel.setFinalidade(rs.getString("finalidade"));
			imovel.setComodos(rs.getInt("comodos_imovel"));
			imovel.setIdCliente(rs.getInt("id_cliente"));
			enderecoDAO = new EnderecoDAO(conn);
			Endereco endereco = enderecoDAO.buscarPorId(rs.getInt("id_endereco"));
			imovel.setEndereco(endereco);
			return imovel;
		}

		return null;
	}

	public void atualizar(Imovel imovel) throws SQLException {
		String sql = "UPDATE imovel SET tipo_imovel = ?, area_imovel = ?, valor_imovel = ?, comodos_imovel = ?,finalidade = ?, id_endereco = ?, id_cliente = ? WHERE id_imovel = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, imovel.getTipoPropriedade());
		stmt.setInt(2, imovel.getArea());
		stmt.setInt(3, imovel.getValor());
		stmt.setInt(4, imovel.getComodos());
		stmt.setString(5,imovel.getFinalidade());
		stmt.setInt(6, imovel.getEndereco().getIdEndereco());
		stmt.setInt(7, imovel.getIdCliente());
		stmt.setInt(8, imovel.getIdImovel());
		stmt.executeUpdate();
		stmt.close();
	}

	public void deletar(int id) throws SQLException {
		String sql = "DELETE FROM imovel WHERE id_imovel = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		stmt.close();
	}
}

