package br.uniamerica.consultorio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime cadastro;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime atualizado;

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime excluido;

    public AbstractEntity() {
    }

    @PrePersist
    public void prePersist() {
        this.cadastro = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizado = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        this.excluido = LocalDateTime.now();
    }
}
