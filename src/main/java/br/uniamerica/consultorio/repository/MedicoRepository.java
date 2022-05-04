package br.uniamerica.consultorio.repository;

import br.uniamerica.consultorio.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    /**
     *
     * @param dataEx
     * @param idMedico
     */
    @Modifying
    @Query("UPDATE Medico medico " +
            "SET medico.excluido = :data " +
            "WHERE medico.id = :medico")
    public void updateDataExcluido(@Param("data") LocalDateTime dataEx,
                                   @Param("medico") Long idMedico);
}
