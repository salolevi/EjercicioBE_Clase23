package com.example.Integrador_SalomonLevi.daos;

import com.example.Integrador_SalomonLevi.entidades.Odontologo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements IDao<Odontologo> {
    private static final Logger logger = LogManager.getLogger(OdontologoDAOH2.class);

    private final static String DB_URL = "jdbc:h2:~/parcial;INIT=RUNSCRIPT FROM 'create.sql'";
    private final static String INSERT = "INSERT INTO Odontologos (nombre, apellido, matricula) VALUES (?, ?, ?);";
    private final static String SELECT_ALL = "SELECT * FROM Odontologos;";
    private final static String SELECT = "SELECT * FROM Odontologos WHERE id = ?;";
    //    La matricula no permito cambiarla
    private final static String UPDATE = "UPDATE Odontologos SET nombre = ?, apellido = ? WHERE id = ?;";
    private final static String DELETE = "DELETE FROM Odontologos WHERE id = ?;";
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    @Override
    public void agregar(Odontologo odontologo) throws SQLException {
        Connection c = null;
        try {
            c = getConnection();
            var st = c.prepareStatement(INSERT);
            st.setString(1, odontologo.nombre());
            st.setString(2, odontologo.apellido());
            st.setString(3, odontologo.matricula());
            st.execute();
        } catch (SQLException e) {
            logger.error("Metodo agregar: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        logger.info("Se agrego correctamente al odontologo " + odontologo.apellido());
    }

    public Odontologo buscarPorId(int id) {
        Connection c = null;

        try {
            c = getConnection();
            var st = c.prepareStatement(SELECT);
            st.setInt(1, id);
            var rs = st.executeQuery();

            if (rs.next())
                return new Odontologo(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4));

        }catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Odontologo> listar() {
        Connection c = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            c = getConnection();

            var st = c.createStatement();
            var rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                odontologos.add(new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            logger.error("Metodo listar: " + e.getMessage());
        }

        if (odontologos.size() == 0) {
            logger.debug("La lista esta vacia");
        }

        return odontologos;
    }

    @Override
    public void modificar(Odontologo odontologo) {
        Connection c = null;

        try {
            c = getConnection();
            var st = c.prepareStatement(UPDATE);
            st.setString(1, odontologo.nombre());
            st.setString(2, odontologo.apellido());
            st.setInt(3, odontologo.id());
            st.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        Connection c = null;
        try {
            c = getConnection();
            var st = c.prepareStatement(DELETE);
            st.setInt(1, id);
            st.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}