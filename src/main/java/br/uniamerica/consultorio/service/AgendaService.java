package br.uniamerica.consultorio.service;

import br.uniamerica.consultorio.entity.Agenda;
import br.uniamerica.consultorio.entity.Paciente;
import br.uniamerica.consultorio.entity.Secretaria;
import br.uniamerica.consultorio.entity.StatusAgendamento;
import br.uniamerica.consultorio.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private AgendaHistoricoService agendaHistoricoService;

    public Optional<Agenda> findById(Long id) {
        return this.agendaRepository.findById(id);
    }

    public Page<Agenda> listAll(Pageable pageable) {
        return this.agendaRepository.findAll(pageable);
    }

    public void update(Secretaria secretaria, Long id, Agenda novaAgenda) {
        Assert.notNull(secretaria, "Secretaria não informada");

        Agenda agenda = this.findById(id).orElse(null);

        Assert.notNull(agenda, "Id da agenda não encontrado");

        if (novaAgenda.getStatusAgendamento() != null) {
            //TODO: necessário validar os status de agendamento que a secretaria pode operar
            agenda.setStatusAgendamento(novaAgenda.getStatusAgendamento());
        }

        if (novaAgenda.getDataDe() != null && novaAgenda.getDataAte() != null) {
            this.validateDate(novaAgenda);

            if (!agenda.getEncaixe()) {
                Assert.isTrue(this.hasScheduleCollision(agenda, agenda.getDataDe(), agenda.getDataAte()),
                        "Já existe um agendamento realizado para este horário");
            }

            agenda.setDataDe(novaAgenda.getDataDe());
            agenda.setDataAte(novaAgenda.getDataAte());
        }

        // TODO: alterar por método que atualize somente os campos permitidos
        this.saveTransactional(secretaria, agenda);
    }

    public void update(Paciente paciente, Long id, Agenda novaAgenda) {
        Assert.notNull(paciente, "Paciente não informado");
        Assert.notNull(novaAgenda, "Agenda não informada");

        Agenda agenda = this.findById(id).orElse(null);

        Assert.notNull(agenda, "Id da agenda não encontrado");
        Assert.isTrue(paciente.getId().equals(agenda.getPaciente().getId()),
                "O agendamento informado não pertence ao paciente.");
        Assert.isTrue(!agenda.getEncaixe(), "O paciente não pode alterar o horário de um encaixe");

        if (novaAgenda.getStatusAgendamento() != null) {
            Assert.state(novaAgenda.getStatusAgendamento().equals(StatusAgendamento.Cancelado),
                    "Operação não permitida, o paciente só pode cancelar um agendamento");

            agenda.setStatusAgendamento(novaAgenda.getStatusAgendamento());
        }

        if (novaAgenda.getDataDe() != null && novaAgenda.getDataAte() != null) {
            this.validateDate(novaAgenda);

            agenda.setDataDe(novaAgenda.getDataDe());
            agenda.setDataAte(novaAgenda.getDataAte());
        }

        // TODO: alterar por método que atualize somente os campos permitidos
        this.saveTransactional(paciente, agenda);
    }

    public void insert(Paciente paciente, Agenda agenda) {
        try {
            Assert.notNull(paciente, "Necessário informar um paciente");

            Assert.isTrue(!agenda.getEncaixe(), "Paciente não pode realizar agendamento como encaixe");

            this.validateDate(agenda);

            Assert.state(agenda.getStatusAgendamento().equals(StatusAgendamento.Pendente),
                    "O status de agendamento deve ser pendente");

            this.saveTransactional(paciente, agenda);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Secretaria secretaria, Agenda agenda) {
        try {
            Assert.notNull(secretaria, "Necessário informar uma secretaria");

            this.validateDate(agenda);

            Assert.state(agenda.getStatusAgendamento().equals(StatusAgendamento.Aprovado),
                    "O status de agendamento deve ser aprovado");

            if (!agenda.getEncaixe()) {
                Assert.isTrue(this.hasScheduleCollision(agenda, agenda.getDataDe(), agenda.getDataAte()),
                        "Já existe um agendamento realizado para este horário");
            }

            this.saveTransactional(secretaria, agenda);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    protected void saveTransactional(Secretaria secretaria, Agenda agenda) {
        this.agendaHistoricoService.insert(secretaria, agenda);
        this.saveTransactional(agenda);
    }

    protected void saveTransactional(Paciente paciente, Agenda agenda) {
        this.agendaHistoricoService.insert(paciente, agenda);
        this.saveTransactional(agenda);
    }

    @Transactional
    protected void saveTransactional(Agenda agenda) {
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
        Assert.isTrue(this.isValidScheduleDate(agenda.getDataDe()),
                "A data atual precisa ser maior do que a atual");

        Assert.isTrue(this.isValidScheduleDate(agenda.getDataAte()),
                "A data de término precisa ser maior do que a atual");

        Assert.isTrue(this.isValidDateRange(agenda.getDataDe(), agenda.getDataAte()),
                "A data de término precisa ser maior do que a data inicial");

        Assert.isTrue(this.isValidDayOfWeek(agenda.getDataDe()),
                "Não é possível realizar agendamentos aos sábados e domingos");

        Assert.isTrue(this.isValidBusinessHour(agenda.getDataDe()),
                "A data de incio do agendamento deve estar dentro do horário de atendimento (08 às 12 e 14 as 18");

        Assert.isTrue(this.isValidBusinessHour(agenda.getDataAte()),
                "A data de incio do agendamento deve estar dentro do horário de atendimento (08 às 12 e 14 as 18");
    }

    public boolean hasScheduleCollision(Agenda agenda, LocalDateTime dataDe, LocalDateTime dataAte) {
        return this.agendaRepository.findScheduleCollision(
                agenda.getId(),
                agenda.getMedico().getId(),
                agenda.getPaciente().getId(),
                dataDe,
                dataAte
        ).isEmpty();
    }

    public boolean isValidScheduleDate(LocalDateTime date) {
        return this.isValidDateRange(LocalDateTime.now(), date);
    }

    public boolean isValidDateRange(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return dateEnd.compareTo(dateStart) >= 0;
    }

    public boolean isValidDayOfWeek(LocalDateTime date) {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    public boolean isValidBusinessHour(LocalDateTime date) {
        LocalTime time = date.toLocalTime();
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime lunchTime = LocalTime.of(12,0);
        LocalTime endTime = LocalTime.of(18,0);

        return (time.isAfter(startTime) && time.isBefore(lunchTime)) ||
                (lunchTime.isAfter(lunchTime.plus(2, ChronoUnit.HOURS)) && time.isBefore(endTime));
    }
}
