package com.victor.consultas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victor.consultas.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Paciente findByCpf(String cpf);
    Paciente findByCartaoSus(String cartaoSus);
}
