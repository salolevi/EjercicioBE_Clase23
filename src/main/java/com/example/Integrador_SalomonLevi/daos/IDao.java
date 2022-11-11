package com.example.Integrador_SalomonLevi.daos;
import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {

    public void agregar(T t) throws SQLException;

    public List<T> listar();

    public void modificar(T t);

    public void eliminar(int id);

    public T buscarPorId(int id);
}
