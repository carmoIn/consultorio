package br.uniamerica.consultorio.service;

import br.uniamerica.consultorio.entity.Convenio;
import br.uniamerica.consultorio.repository.ConvenioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConvenioService {
    private ConvenioRepository convenioRepository;

    public Optional<Convenio> findById(Long id) {
        return this.convenioRepository.findById(id);
    }

    public Page<Convenio> findAll(Pageable pageable) {
        return this.convenioRepository.findAll(pageable);
    }

    @Transactional
    public void update(Long id, Convenio convenio) {
        if (id == convenio.getId()) {
            this.convenioRepository.save(convenio);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void insert(Convenio convenio) {
        this.convenioRepository.save(convenio);
    }

    @Transactional
    public void updateStatus(Long id, Convenio convenio) {
        if (id == convenio.getId()) {
            this.convenioRepository.updateStatus(
                    LocalDateTime.now(),
                    convenio.getId()
            );
        } else {
            throw new RuntimeException();
        }
    }

}
