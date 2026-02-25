package com.victor.consultas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victor.consultas.model.Consulta;
import com.victor.consultas.model.Paciente;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findAllByOrderByDataAscHoraAsc();

    List<Consulta> findByPaciente(Paciente paciente);

    List<Consulta> findByData(LocalDate data);

    List<Consulta> findByPacienteAndData(Paciente paciente, LocalDate data);
}
