package br.uniamerica.consultorio.controller;

import br.uniamerica.consultorio.entity.Convenio;
import br.uniamerica.consultorio.service.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/convenios")
public class ConvenioController {
    @Autowired
    private ConvenioService convenioService;

    @GetMapping
    public ResponseEntity<Page<Convenio>> listAlLConvenios(Pageable pageable) {
        return ResponseEntity.ok().body(
                this.convenioService.listAll(pageable)
        );
    }

    @GetMapping("/{idConvenio}")
    public ResponseEntity<Convenio> findById(
            @PathVariable("idConvenio") Long idConvenio
    ) {
        return ResponseEntity.ok().body(
                this.convenioService.findById(idConvenio).get()
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Convenio convenio) {
        try {
            this.convenioService.insert(convenio);
            return ResponseEntity.ok().body("Convenio Cadastrada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/status/{idConvenio}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Convenio convenio,
            @PathVariable("idConvenio") Long idConvenio
    ) {
        try {
            this.convenioService.updateDataExcluido(idConvenio, convenio);
            return ResponseEntity.ok().body("Convenio Desativada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
