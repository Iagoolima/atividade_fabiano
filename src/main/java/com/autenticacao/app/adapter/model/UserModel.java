package com.autenticacao.app.adapter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class UserModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID idUser;
    private String email;
    private String name;
    private UUID password;
    private String role;
}
