package br.uniamerica.consultorio.controller;

import br.uniamerica.consultorio.entity.Medico;
import br.uniamerica.consultorio.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/medicos")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<Page<Medico>> listAlLMedicos(Pageable pageable) {
        return ResponseEntity.ok().body(
                this.medicoService.listAll(pageable)
        );
    }

    @GetMapping("/{idMedico}")
    public ResponseEntity<Medico> findById(
            @PathVariable("idMedico") Long idMedico
    ) {
        return ResponseEntity.ok().body(
                this.medicoService.findById(idMedico).get()
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Medico medico) {
        try {
            this.medicoService.insert(medico);
            return ResponseEntity.ok().body("Medico Cadastrada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/status/{idMedico}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Medico medico,
            @PathVariable("idMedico") Long idMedico
    ) {
        try {
            this.medicoService.updateDataExcluido(idMedico, medico);
            return ResponseEntity.ok().body("Medico Desativada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
