package com.johndiddles.todov2.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue
    private String id;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String username;
    @NotNull
    private String password;
}
