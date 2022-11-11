package com.example.Integrador_SalomonLevi.servicios;

import com.example.Integrador_SalomonLevi.daos.OdontologoDAOH2;
import com.example.Integrador_SalomonLevi.entidades.Odontologo;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class OdontologoService {
    private static final Logger logger = LogManager.getLogger(OdontologoService.class);

    OdontologoDAOH2 odontologoDAOH2;

    public List<Odontologo> listarOdontologos() {

        for (Odontologo p: odontologoDAOH2.listar()) {
            logger.info(p);
        }

        return odontologoDAOH2.listar();
    }

    public void agregarOdontologo(Odontologo odontologo) {
        try {
            odontologoDAOH2.agregar(odontologo);
        }catch (SQLException e) {

        }
    }

    public void modificarOdontologo(Odontologo odontologo) {
        odontologoDAOH2.modificar(odontologo);
    }

    public void eliminarOdontologo(int id) {
        odontologoDAOH2.eliminar(id);
    }

    public Odontologo buscarPorId(int id) {
        return odontologoDAOH2.buscarPorId(id);
    }
}