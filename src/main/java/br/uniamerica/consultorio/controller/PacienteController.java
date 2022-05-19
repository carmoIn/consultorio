package br.uniamerica.consultorio.controller;

import br.uniamerica.consultorio.entity.Paciente;
import br.uniamerica.consultorio.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<Page<Paciente>> listAlLPacientes(Pageable pageable) {
        return ResponseEntity.ok().body(
                this.pacienteService.listAll(pageable)
        );
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> findById(
            @PathVariable("idPaciente") Long idPaciente
    ) {
        return ResponseEntity.ok().body(
                this.pacienteService.findById(idPaciente).get()
        );
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Paciente paciente) {
        try {
            this.pacienteService.insert(paciente);
            return ResponseEntity.ok().body("Paciente Cadastrada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/status/{idPaciente}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Paciente paciente,
            @PathVariable("idPaciente") Long idPaciente
    ) {
        try {
            this.pacienteService.updateDataExcluido(idPaciente, paciente);
            return ResponseEntity.ok().body("Paciente Desativada com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
