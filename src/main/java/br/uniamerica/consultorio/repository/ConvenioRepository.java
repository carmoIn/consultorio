package br.uniamerica.consultorio.repository;

import br.uniamerica.consultorio.entity.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

    @Modifying
    @Query("UPDATE Convenio convenio " +
            "SET convenio.excluido = :data " +
            "WHERE convenio.id = :convenio")
    public void updateDataExcluido(@Param("data")LocalDateTime dataEx,
                                   @Param("convenio") Long idConvenio);
}
