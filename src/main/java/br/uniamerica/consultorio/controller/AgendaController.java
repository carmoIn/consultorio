package br.uniamerica.consultorio.controller;

import br.uniamerica.consultorio.entity.Agenda;
import br.uniamerica.consultorio.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/agendas")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @GetMapping
    public ResponseEntity<Page<Agenda>> listAlLAgendas(Pageable pageable) {
        return ResponseEntity.ok().body(
                this.agendaService.listAll(pageable)
        );
    }

    @GetMapping("/{idAgenda}")
    public ResponseEntity<Agenda> findById(
            @PathVariable("idAgenda") Long idAgenda
    ) {
        return ResponseEntity.ok().body(
                this.agendaService.findById(idAgenda).get()
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Agenda agenda) {
        try {
            this.agendaService.insert(agenda);
            return ResponseEntity.ok().body("Agenda Cadastrada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/status/{idAgenda}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Agenda agenda,
            @PathVariable("idAgenda") Long idAgenda
    ) {
        try {
            this.agendaService.updateDataExcluido(idAgenda, agenda);
            return ResponseEntity.ok().body("Agenda Desativada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
