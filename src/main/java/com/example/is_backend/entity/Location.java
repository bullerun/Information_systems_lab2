package com.example.is_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long x;


    @NotNull(message = "Поле y не может быть null")
    private Float y;

    private float z;

    @NotNull(message = "Поле name не может быть null")
    @Size(max = 470, message = "Длина строки name не должна превышать 470 символов")
    private String name;
}
