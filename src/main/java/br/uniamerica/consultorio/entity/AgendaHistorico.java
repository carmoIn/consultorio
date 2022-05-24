package br.uniamerica.consultorio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agenda_historicos", schema = "public")
public class AgendaHistorico extends AbstractEntity {
    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private Agenda agenda;

    @Getter @Setter
    @Column(length = 100)
    private String observacao;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private Secretaria secretaria;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dataDe;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dataAte;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusAgendamento statusAgendamento;
}
