package com.example.Integrador_SalomonLevi.daos;

import com.example.Integrador_SalomonLevi.entidades.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements IDao<Paciente> {

    private static final Logger logger = LogManager.getLogger(PacienteDAOH2.class);

    private final static String DB_URL = "jdbc:h2:~/parcial;INIT=RUNSCRIPT FROM 'create.sql'";
    private final static String INSERT = "INSERT INTO Pacientes (nombre, apellido, domicilio, dni) VALUES (?, ?, ?, ?);";
    private final static String SELECT_ALL = "SELECT * FROM Pacientes;";
    private final static String SELECT = "SELECT * FROM Pacientes WHERE id = ?;";
    private final static String SELECT_DNI = "SELECT * FROM Pacientes WHERE dni = ?;";
    //    La matricula no permito cambiarla
    private final static String UPDATE = "UPDATE Pacientes SET nombre = ?, apellido = ?, domicilio = ? WHERE id = ?;";
    private final static String DELETE = "DELETE FROM Pacientes WHERE id = ?;";
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    @Override
    public void agregar(Paciente paciente) {
        Connection c = null;
        try {
            c = getConnection();
            var st = c.prepareStatement(INSERT);
            st.setString(1, paciente.nombre());
            st.setString(2, paciente.apellido());
            st.setString(3, paciente.domicilio());
            st.setString(4, paciente.dni());
            st.execute();

        } catch (SQLException e) {
            logger.error("Metodo agregar: " + e.getMessage());
        }
    }

    @Override
    public List<Paciente> listar() {
        Connection c = null;
        List<Paciente> pacientes = new ArrayList<>();
        try {
            c = getConnection();
            var st = c.createStatement();
            var rs = st.executeQuery(SELECT_ALL);
            while (rs.next()){
                pacientes.add(new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (pacientes.size() == 0)
            logger.debug("La lista esta vacia");
        return pacientes;
    }

    @Override
    public void modificar(Paciente paciente) {
        Connection c = null;
        try {
            c = getConnection();
            var st = c.prepareStatement(UPDATE);
            st.setString(1, paciente.nombre());
            st.setString(2, paciente.apellido());
            st.setString(3, paciente.domicilio());
            st.setInt(4, paciente.id());
            st.executeUpdate();
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

    @Override
    public Paciente buscarPorId(int id) {
        Connection c = null;
        try {
            c = getConnection();
            var st = c.prepareStatement(SELECT);
            st.setInt(1, id);
            var rs = st.executeQuery();
            if (rs.next())
                return new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public Paciente buscarPorDni(String dni) {
        Connection c = null;
        try {
            c = getConnection();
            var st = c.prepareStatement(SELECT_DNI);
            st.setString(1, dni);
            var rs = st.executeQuery();
            if (rs.next())
                return new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}