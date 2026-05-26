package dao;

import java.sql.*;
import java.util.*;

import model.Imovel;
import model.Endereco;

public class ImovelDAO {

    private Connection conn;
    private EnderecoDAO enderecoDAO;

    public ImovelDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Imovel imovel) throws SQLException {
        String sql = "INSERT INTO imovel (tipo_imovel, area_imovel, valor_imovel, comodos_imovel, id_endereco) VALUES (?, ?, ?, ?, ?)"; // trocara para imvovel
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, imovel.getTipoPropriedade());
        stmt.setInt(2, imovel.getArea());
        stmt.setInt(3, imovel.getValor());
        stmt.setInt(4, imovel.getComodos());
        stmt.setInt(5, imovel.getEndereco().getIdEndereco());

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
        	Endereco endereco = enderecoDAO.getEndereco(rs.getInt("id_endereco"));
        	imovel.setEndereco(endereco);

            lista.add(imovel);
        }

        return lista;
    }
}

