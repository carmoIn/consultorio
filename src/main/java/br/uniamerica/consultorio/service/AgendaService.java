package br.uniamerica.consultorio.service;

import br.uniamerica.consultorio.entity.Agenda;
import br.uniamerica.consultorio.entity.StatusAgendamento;
import br.uniamerica.consultorio.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    public Optional<Agenda> findById(Long id) {
        return this.agendaRepository.findById(id);
    }

    public Page<Agenda> listAll(Pageable pageable) {
        return this.agendaRepository.findAll(pageable);
    }

    public void update(Long id, Agenda agenda) {
        if (this.hasValidStatus(agenda)) {
            if (id == agenda.getId()) {
                this.saveTransactional(agenda);
            } else {
                throw new RuntimeException();
            }
        }
    }

    public void insert(Agenda agenda) {
        if (this.hasValidStatus(agenda)) {
            this.saveTransactional(agenda);
        }
    }

    @Transactional
    public void saveTransactional(Agenda agenda) {
        this.agendaRepository.save(agenda);
    }

    @Transactional
    public void updateDataExcluido(Long id, Agenda agenda) {
        if (id == agenda.getId()) {
            this.agendaRepository.updateDataExcluido(
                    LocalDateTime.now(),
                    agenda.getId()
            );
        }
    }

    public boolean hasValidStatus(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.Compareceu) &&
                agenda.getDataDe().compareTo(LocalDateTime.now()) > 0) {
            return false;
        }
        else if (agenda.getStatusAgendamento().equals(StatusAgendamento.NaoCompareceu) &&
                agenda.getDataDe().compareTo(LocalDateTime.now()) > 0) {
            return false;
        }
        else if (agenda.getStatusAgendamento().equals(StatusAgendamento.Pendente) &&
                (agenda.getEncaixe() || agenda.getDataDe().compareTo(LocalDateTime.now()) < 0)) {
            return false;
        }
        return true;
    }
}
