package com.example.Integrador_SalomonLevi.controllers;

import com.example.Integrador_SalomonLevi.daos.PacienteDAOH2;
import com.example.Integrador_SalomonLevi.entidades.Paciente;
import com.example.Integrador_SalomonLevi.servicios.PacienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PacienteController {
    PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

    @GetMapping("/index")
    public String listaPacientes(Model model) {
        var pacientes = pacienteService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "index";
    }

    @PostMapping("/index")
    @ResponseStatus(HttpStatus.CREATED)
    public String agregarPAciente(@RequestBody Paciente p, Model model) {
        if (p != null)
            pacienteService.agregarPaciente(p);
        return "index";
    }

    @GetMapping("/paciente")
    public String buscarPaciente(String dni, Model model) {
        model.addAttribute("paciente", pacienteService.buscarPorDni(dni));
        return "paciente";
    }
}
