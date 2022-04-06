package br.uniamerica.consultorio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendas", schema = "public")
public class Agenda extends AbstractEntity {
    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private Medico medico;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusAgendamento statusAgendamento;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dataAgendamento;

    @Getter @Setter
    @Column(nullable = true, columnDefinition = "boolean DEFAULT false")
    private Boolean encaixe;
}
