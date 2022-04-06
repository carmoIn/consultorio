package br.uniamerica.consultorio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes", schema = "public")
public class Paciente extends Pessoa {
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtendimento tipoAtendimento;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private Convenio convenio;

    @Getter @Setter
    @Column(length = 20)
    private String numeroCartaoConvenio;

    @Getter @Setter
    @Column
    private LocalDateTime dataVencimento;
}
