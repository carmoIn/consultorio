package br.uniamerica.consultorio.service;

import br.uniamerica.consultorio.entity.Agenda;
import br.uniamerica.consultorio.entity.AgendaHistorico;
import br.uniamerica.consultorio.entity.Paciente;
import br.uniamerica.consultorio.entity.Secretaria;
import br.uniamerica.consultorio.repository.AgendaHistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AgendaHistoricoService {

    @Autowired
    AgendaHistoricoRepository agendaHistoricoRepository;

    public Optional<AgendaHistorico> findById(Long id) {
        return this.agendaHistoricoRepository.findById(id);
    }

    public Page<AgendaHistorico> listAll(Pageable pageable) {
        return this.agendaHistoricoRepository.findAll(pageable);
    }

    public void insert(Paciente paciente, Agenda agenda) {
        Assert.notNull(paciente, "Paciente não informado");
        Assert.notNull(agenda, "Agenda não informada");

        AgendaHistorico agendaHistorico = new AgendaHistorico();

        agendaHistorico.setAgenda(agenda);
        agendaHistorico.setPaciente(paciente);
        agendaHistorico.setDataDe(agenda.getDataDe());
        agendaHistorico.setDataAte(agenda.getDataAte());
        agendaHistorico.setStatusAgendamento(agenda.getStatusAgendamento());

        this.saveTransactional(agendaHistorico);
    }

    public void insert(Secretaria secretaria, Agenda agenda) {
        Assert.notNull(secretaria, "Secretaria não informada");
        Assert.notNull(agenda, "Agenda não informada");

        AgendaHistorico agendaHistorico = new AgendaHistorico();

        agendaHistorico.setAgenda(agenda);
        agendaHistorico.setSecretaria(secretaria);
        agendaHistorico.setDataDe(agenda.getDataDe());
        agendaHistorico.setDataAte(agenda.getDataAte());
        agendaHistorico.setStatusAgendamento(agenda.getStatusAgendamento());

        this.saveTransactional(agendaHistorico);
    }

    @Transactional
    public void saveTransactional(AgendaHistorico agendaHistorico) {
        this.agendaHistoricoRepository.save(agendaHistorico);
    }
}
