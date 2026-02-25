package com.victor.consultas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.victor.consultas.model.Consulta;
import com.victor.consultas.repository.ConsultaRepository;
import com.victor.consultas.repository.PacienteRepository;

@Controller
public class DashboardController {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public DashboardController(PacienteRepository pacienteRepository,
                               ConsultaRepository consultaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        long totalPacientes = pacienteRepository.count();
        long totalConsultas = consultaRepository.count();

        LocalDate hoje = LocalDate.now();
        List<Consulta> consultasHoje = consultaRepository.findByData(hoje);

        model.addAttribute("totalPacientes", totalPacientes);
        model.addAttribute("totalConsultas", totalConsultas);
        model.addAttribute("consultasHoje", consultasHoje);

        return "dashboard";
    }
}
