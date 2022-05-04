package br.uniamerica.consultorio.service;

import br.uniamerica.consultorio.entity.Especialidade;
import br.uniamerica.consultorio.entity.Medico;
import br.uniamerica.consultorio.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class MedicoService {
    @Autowired
    MedicoRepository medicoRepository;

    public Optional<Medico> findById(Long id) {
        return this.medicoRepository.findById(id);
    }

    public Page<Medico> listAll(Pageable pageable) {
        return this.medicoRepository.findAll(pageable);
    }

    @Transactional
    public void update(Long id, Medico medico) {
        if (id == medico.getId()) {
            this.medicoRepository.save(medico);
        }
        else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void insert(Medico medico) {
        this.medicoRepository.save(medico);
    }

    @Transactional
    public void setUpdateExcluido(Long id, Medico medico) {
        if (id == medico.getId()) {
            this.medicoRepository.setUpdateExcluido(
                    LocalDateTime.now(),
                    medico.getId());
        } else {
            throw new RuntimeException();
        }
    }
}
