package dao;

import java.sql.SQLException;
import java.util.List;

public interface ICrudDAO<T> {
    void inserir(T obj) throws SQLException;
    List<T> listar() throws SQLException;
    T buscarPorId(int id) throws SQLException;
    void atualizar(T obj) throws SQLException;
    void deletar(int id) throws SQLException;
}