package br.uniamerica.consultorio.controller;

import br.uniamerica.consultorio.entity.Especialidade;
import br.uniamerica.consultorio.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/especialidades")
public class EspecialidadeController {
    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping
    public ResponseEntity<Page<Especialidade>> listAlLEspecialidades(Pageable pageable) {
        return ResponseEntity.ok().body(
                this.especialidadeService.listAll(pageable)
        );
    }

    @GetMapping("/{idEspecialidade}")
    public ResponseEntity<Especialidade> findById(
            @PathVariable("idEspecialidade") Long idEspecialidade
    ) {
        return ResponseEntity.ok().body(
                this.especialidadeService.findById(idEspecialidade).get()
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Especialidade especialidade) {
        try {
            this.especialidadeService.insert(especialidade);
            return ResponseEntity.ok().body("Especialidade Cadastrada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/status/{idEspecialidade}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Especialidade especialidade,
            @PathVariable("idEspecialidade") Long idEspecialidade
    ) {
        try {
            this.especialidadeService.updateDataExcluido(idEspecialidade, especialidade);
            return ResponseEntity.ok().body("Especialidade Desativada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
