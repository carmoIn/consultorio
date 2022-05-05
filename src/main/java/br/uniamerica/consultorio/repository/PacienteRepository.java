package br.uniamerica.consultorio.repository;

import br.uniamerica.consultorio.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Modifying
    @Query("UPDATE Paciente paciente " +
            "SET paciente.excluido = :data " +
            "WHERE paciente.id = :paciente")
    public void updateDataExcluido(@Param("data") LocalDateTime dataEx,
                                   @Param("paciente") Long idPaciente);
}
