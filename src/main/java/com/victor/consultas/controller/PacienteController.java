package com.victor.consultas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.victor.consultas.model.Paciente;
import com.victor.consultas.repository.PacienteRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/pacientes")
public class PacienteController {
   
    private final PacienteRepository repository;

    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/novo")
    public String novoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "paciente-form";
    }

@PostMapping("/salvar")
public String salvarPaciente(
        @Valid @ModelAttribute Paciente paciente,
        BindingResult result,
        Model model) {

    var p = new Paciente(2L, "TEste2", "33333333333", "151515151515151");
    p.setId(3L);


    if (result.hasErrors()) {
        model.addAttribute("paciente", paciente);
        return "paciente-form";
    }

    repository.save(paciente);
    return "redirect:/pacientes/lista";
}

    @GetMapping("/lista")
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", repository.findAll());
        return "paciente-lista";
    }
    @GetMapping("/editar/{id}")
public String editarPaciente(@PathVariable Long id, Model model) {
    Paciente paciente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

    model.addAttribute("paciente", paciente);
    return "paciente-form";
    
}
    @GetMapping("/excluir/{id}")
public String excluirPaciente(@PathVariable Long id) {
    repository.deleteById(id);
    return "redirect:/pacientes/lista";
}


}
