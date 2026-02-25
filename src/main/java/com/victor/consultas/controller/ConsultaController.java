package com.victor.consultas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.victor.consultas.model.Consulta;
import com.victor.consultas.model.Paciente;
import com.victor.consultas.repository.ConsultaRepository;
import com.victor.consultas.repository.PacienteRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaController(ConsultaRepository consultaRepository,
                              PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping("/nova")
    public String novaConsulta(Model model) {
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("pacientes", pacienteRepository.findAll());
        return "consulta-form";
    }

    @PostMapping("/salvar")
    public String salvarConsulta(@Valid Consulta consulta,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("pacientes", pacienteRepository.findAll());
            return "consulta-form";
    }

    consultaRepository.save(consulta);
    return "redirect:/consultas/lista";
    }

    @GetMapping("/lista")
    public String listarConsultas(
            @RequestParam(required = false) Long pacienteId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Model model) {

        List<Consulta> consultas;

        if (pacienteId != null && data != null) {
            Paciente paciente = pacienteRepository.findById(pacienteId).orElse(null);
            consultas = consultaRepository.findByPacienteAndData(paciente, data);

        } else if (pacienteId != null) {
            Paciente paciente = pacienteRepository.findById(pacienteId).orElse(null);
            consultas = consultaRepository.findByPaciente(paciente);

        } else if (data != null) {
            consultas = consultaRepository.findByData(data);

        } else {
            consultas = consultaRepository.findAllByOrderByDataAscHoraAsc();
        }

        model.addAttribute("consultas", consultas);
        model.addAttribute("pacientes", pacienteRepository.findAll());

        return "consulta-lista";
    }

    @GetMapping("/editar/{id}")
    public String editarConsulta(@PathVariable Long id, Model model) {
    Consulta consulta = consultaRepository.findById(id).orElseThrow();
    model.addAttribute("consulta", consulta);
    model.addAttribute("pacientes", pacienteRepository.findAll());
    return "consulta-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirConsulta(@PathVariable Long id) {
    consultaRepository.deleteById(id);
    return "redirect:/consultas/lista";
    }

}
