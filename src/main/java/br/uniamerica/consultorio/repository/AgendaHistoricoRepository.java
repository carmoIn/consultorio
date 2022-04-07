package br.uniamerica.consultorio.repository;

import br.uniamerica.consultorio.entity.AgendaHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaHistoricoRepository extends JpaRepository<AgendaHistorico, Long> {
}
