package dao;

import java.sql.*;
import java.util.*;

import model.Imovel;

public class ImovelDAO {

    private Connection conn;

    public ImovelDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Imovel imovel) throws SQLException {
        String sql = "INSERT INTO imovel (endereco_imovel, tipo_imovel, area_imovel, valor_imovel, comodos_imovel) VALUES (?, ?, ?, ?, ?)"; // trocara para imvovel

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, imovel.getEndereco());
        stmt.setString(2, imovel.getTipoPropriedade());
        stmt.setInt(3, imovel.getArea());
        stmt.setInt(4, imovel.getValor());
        stmt.setInt(5, imovel.getComodos());

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
        	imovel.setEndereco(rs.getString("endereco_imovel"));
        	imovel.setTipoPropriedade(rs.getString("tipo_imovel"));
        	imovel.setArea(rs.getInt("area_imovel"));
        	imovel.setValor(rs.getInt("valor_imovel"));
        	imovel.setComodos(rs.getInt("comodos_imovel"));

            lista.add(imovel);
        }

        return lista;
    }
}

