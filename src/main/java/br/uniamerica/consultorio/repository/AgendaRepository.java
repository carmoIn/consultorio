package br.uniamerica.consultorio.repository;

import br.uniamerica.consultorio.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Modifying
    @Query("UPDATE Agenda agenda " +
            "SET agenda.excluido = :data " +
            "WHERE agenda.id = :agenda")
    public void updateDataExcluido(@Param("data") LocalDateTime dataEx,
                                   @Param("agenda") Long idagenda);

    @Query("FROM Agenda agenda " +
            "WHERE not agenda.encaixe " +
            "AND agenda.id <> :agenda_id " +
            "AND (agenda.medico = :medico_id OR agenda.paciente = :paciente_id) " +
            "AND ((:dataDe BETWEEN agenda.dataDe AND agenda.dataAte) " +
            "OR (:dataAte BETWEEN agenda.dataDe AND agenda.dataAte))"
    )
    public List<Agenda> findScheduleCollision(@Param("agenda_id") Long agendaId,
                                              @Param("medico_id") Long medicoId,
                                              @Param("paciente_id") Long pacienteId,
                                              @Param("data_de") LocalDateTime dataDe,
                                              @Param("data_ate") LocalDateTime dataAte);
}
