package br.uniamerica.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Table(name = "convenios", schema = "public")
public class Convenio extends AbstractEntity {
    @Getter @Setter
    @Column(length = 50)
    private String nome;

    @Getter @Setter
    @Digits(integer = 3, fraction = 3)
    @Column(nullable = false)
    private BigDecimal valor;
}
