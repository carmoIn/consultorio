package br.uniamerica.consultorio.service;

import br.uniamerica.consultorio.entity.Paciente;
import br.uniamerica.consultorio.entity.TipoAtendimento;
import br.uniamerica.consultorio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Optional<Paciente> findById(Long id) {
        return this.pacienteRepository.findById(id);
    }

    public Page<Paciente> listAll(Pageable pageable) {
        return this.pacienteRepository.findAll(pageable);
    }

    public void update(Long id, Paciente paciente) {
        if (id == paciente.getId()) {
            validatePaciente(paciente);
            this.saveTransactional(paciente);
        } else {
            throw new RuntimeException();
        }
    }

    public void insert(Paciente paciente) {
        validatePaciente(paciente);
        this.saveTransactional(paciente);
    }

    @Transactional
    public void saveTransactional(Paciente paciente) {
        this.pacienteRepository.save(paciente);
    }

    @Transactional
    public void updateDataExcluido(Long id, Paciente paciente) {
        if (id == paciente.getId()) {
            this.pacienteRepository.updateDataExcluido(
                    LocalDateTime.now(),
                    paciente.getId());
        } else {
            throw new RuntimeException();
        }
    }

    public void validateConvenio(Paciente paciente) {
        if (paciente.getConvenio().getId() == null) {
            throw new RuntimeException("Convenio não informado para tipo de atendimento convênio");
        }
        if (paciente.getNumeroCartaoConvenio() == null) {
            throw new RuntimeException("Número do cartão do convênio não foi informado");
        }
        if (paciente.getDataVencimento() == null) {
            throw new RuntimeException("Data de vencimento do cartão do convênio não foi informada");
        }
        if (paciente.getDataVencimento().compareTo(LocalDateTime.now()) > 0) {
            throw new RuntimeException("Cartão do convênio com data de vencimento expirada");
        }
    }

    public void validatePaciente(Paciente paciente) {
        if (isConvenio(paciente)) {
            validateConvenio(paciente);
        } else {
            paciente.setConvenio(null);
            paciente.setNumeroCartaoConvenio(null);
            paciente.setDataVencimento(null);
        }
    }

    public boolean isValidConvenio(Paciente paciente) {
        return  paciente.getNumeroCartaoConvenio() != null &&
                paciente.getDataVencimento() != null &&
                paciente.getConvenio() != null;
    }

    public boolean isConvenio(Paciente paciente) {
        return paciente.getTipoAtendimento() == TipoAtendimento.Convenio;
    }
}
