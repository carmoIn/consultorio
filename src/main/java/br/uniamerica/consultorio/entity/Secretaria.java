package br.uniamerica.consultorio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "secretarias", schema = "public")
public class Secretaria extends Pessoa {
    @Getter @Setter
    @Digits(integer = 6, fraction = 3)
    private BigDecimal salario;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dataContratacao;

    @Getter @Setter
    @Column(nullable = true, length = 20)
    private String pis;
}
