package com.autenticacao.app.adapter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "esqueceu_senha")
public class ForgotPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private UUID tokenUpdate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "idUser", foreignKey = @ForeignKey(name = "fk_usuario"))
    private UserEntity idUser;
    private String code;
    private boolean validated = false;
    private LocalDateTime recordedTime;
}
