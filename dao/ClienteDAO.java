package dao;

import java.sql.*;
import java.util.*;

import model.Cliente;

public class ClienteDAO {
//
//    private Connection conn;
//
//    public ClienteDAO(Connection conn) {
//        this.conn = conn;
//    }
//
//    public void inserir(ClienteDAO cliente) throws SQLException {
//        String sql = "INSERT INTO cliente (titulo_livro, autor_livro, ano_publicacao, "
//        		+ "num_copias_disponiveis, num_copias_emprestadas) VALUES (?, ?, ?, ?, ?)"; // trocara para cliente
//
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setString(1, cliente.getTitulo());
//        stmt.setString(2, cliente.getAutores());
//        stmt.setInt(3, cliente.getAno());
//        stmt.setInt(4, cliente.getCopiasDispon());
//        stmt.setInt(5, cliente.getCopiasEmprest());
//
//        stmt.executeUpdate();
//        stmt.close();
//    }
//
//    public List<Cliente> listar() throws SQLException {
//        List<Cliente> lista = new ArrayList<>();
//        String sql = "SELECT * FROM cliente";
//
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//
//        while (rs.next()) {
//        	Cliente cliente = new Cliente();
//        	cliente.setIdLivro(rs.getInt("id_livro"));
//        	cliente.setTitulo(rs.getString("titulo_livro"));
//        	cliente.setAutores(rs.getString("autor_livro"));
//        	cliente.setAno(rs.getInt("ano_publicacao"));
//            cliente.setCopiasDispon(rs.getInt("num_copias_disponiveis"));
//            cliente.setCopiasEmprest(rs.getInt("num_copias_emprestadas"));
//
//            lista.add(cliente);
//        }
//
//        return lista;
//    }
}

