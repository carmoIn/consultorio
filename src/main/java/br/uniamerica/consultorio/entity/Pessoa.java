package br.uniamerica.consultorio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Pessoa extends AbstractEntity {
    @Getter @Setter
    @Column(nullable = false, length = 200)
    private String nome;

    @Getter @Setter
    @Column(length = 12)
    private String telefone;

    @Getter @Setter
    @Column(length = 50)
    private String nacionalidade;

    @Getter @Setter
    @Column(length = 14)
    private String cpf;

    @Getter @Setter
    @Column(length = 20)
    private String rg;

    @Getter @Setter
    @Column(length = 50)
    private String email;

    @Getter @Setter
    @Column(length = 50)
    private String login;

    @Getter @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Sexo sexo;
}
