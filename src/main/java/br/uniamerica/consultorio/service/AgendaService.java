package br.uniamerica.consultorio.service;

import br.uniamerica.consultorio.entity.Agenda;
import br.uniamerica.consultorio.entity.StatusAgendamento;
import br.uniamerica.consultorio.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        try {
            if (id == agenda.getId()) {
                this.validateDate(agenda);
                this.saveTransactional(agenda);
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            throw e;
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

    public void validateDate(Agenda agenda) throws RuntimeException {
        if (agenda.getDataDe().compareTo(LocalDateTime.now()) < 0) {
            throw new RuntimeException("Data de inicio do agendamento menor do que data atual");
        }
        else if (agenda.getDataAte().compareTo(LocalDateTime.now()) < 0) {
            throw new RuntimeException("Data de término do agendamento menor do que data atual");
        }
        else if (agenda.getDataDe().compareTo(agenda.getDataAte()) < 0) {
            throw new RuntimeException("Data de término não pode ser inferior a data de término");
        }
        if (isValidDayOfWeek(agenda.getDataDe())) {
            throw new RuntimeException("Não é possível realizar agendamentos aos sábados e domingos");
        }
        if (isValidBusinessHour(agenda.getDataDe()) || isValidBusinessHour(agenda.getDataAte())) {
            throw new RuntimeException("Data do agendamento fora do horário de funcionamento");
        }
    }

    public boolean isValidDayOfWeek(LocalDateTime date) {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    public boolean isValidBusinessHour(LocalDateTime date) {
        LocalTime time = date.toLocalTime();
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(18,0);

        return time.isAfter(startTime) && time.isBefore(endTime);
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
