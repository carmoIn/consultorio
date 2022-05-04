package br.uniamerica.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@Table(name = "medicos", schema = "public")
public class Medico extends Pessoa {
    @Getter @Setter
    @Column(nullable = false, length = 50)
    private String CRM;

    @Getter @Setter
    @Digits(integer = 3, fraction = 3)
    @Column(nullable = false)
    private BigDecimal porcentagemParticipacao;

    @Getter @Setter
    @Column(nullable = false, length = 50)
    private String consultorio;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private Especialidade especialidade;
}
