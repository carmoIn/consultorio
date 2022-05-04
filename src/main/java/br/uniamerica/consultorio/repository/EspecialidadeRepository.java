package br.uniamerica.consultorio.repository;

import br.uniamerica.consultorio.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    /**
     *
     * @param dataEx
     * @param idEspecialidade
     */
    @Modifying
    @Query("UPDATE Especialidade especialidade " +
            "SET especialidade.excluido = :data " +
            "WHERE especialidade.id = :especialidade")
    public void updateStatus(@Param("data") LocalDateTime dataEx,
                             @Param("especialidade") Long idEspecialidade);
}
