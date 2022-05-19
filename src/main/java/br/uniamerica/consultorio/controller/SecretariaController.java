package br.uniamerica.consultorio.controller;

import br.uniamerica.consultorio.entity.Secretaria;
import br.uniamerica.consultorio.service.SecretariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class SecretariaController {
    @Autowired
    private SecretariaService secretariaService;

    @GetMapping
    public ResponseEntity<Page<Secretaria>> listAlLSecretarias(Pageable pageable) {
        return ResponseEntity.ok().body(
                this.secretariaService.listAll(pageable)
        );
    }

    @GetMapping("/{idSecretaria}")
    public ResponseEntity<Secretaria> findById(
            @PathVariable("idSecretaria") Long idSecretaria
    ) {
        return ResponseEntity.ok().body(
                this.secretariaService.findById(idSecretaria).get()
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Secretaria secretaria) {
        try {
            this.secretariaService.insert(secretaria);
            return ResponseEntity.ok().body("Secretaria Cadastrada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/status/{idSecretaria}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Secretaria secretaria,
            @PathVariable("idSecretaria") Long idSecretaria
    ) {
        try {
            this.secretariaService.updateDataExcluido(idSecretaria, secretaria);
            return ResponseEntity.ok().body("Secretaria Desativada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
