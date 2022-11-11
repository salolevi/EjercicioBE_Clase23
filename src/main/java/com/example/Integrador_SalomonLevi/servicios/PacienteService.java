package com.example.Integrador_SalomonLevi.servicios;

import com.example.Integrador_SalomonLevi.daos.PacienteDAOH2;
import com.example.Integrador_SalomonLevi.entidades.Paciente;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@AllArgsConstructor
public class PacienteService {

    private static final Logger logger = LogManager.getLogger(PacienteService.class);
    PacienteDAOH2 pacienteDAOH2;

    public List<Paciente> listarPacientes() {
        for (Paciente p: pacienteDAOH2.listar()) {
            logger.info(p);
        }

        return pacienteDAOH2.listar();
    }

    public void agregarPaciente(Paciente paciente) {
        pacienteDAOH2.agregar(paciente);
    }

    public void modificarPaciente(Paciente paciente) {
        pacienteDAOH2.modificar(paciente);
    }

    public void eliminarPaciente(int id) {
        pacienteDAOH2.eliminar(id);
    }

    public Paciente buscarPorId(int id) { return pacienteDAOH2.buscarPorId(id); }

    public Paciente buscarPorDni(String dni) { return pacienteDAOH2.buscarPorDni(dni); }

}