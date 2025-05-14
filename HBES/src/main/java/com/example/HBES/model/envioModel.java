package com.example.HBES.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "Envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class envioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_envio;
    
}
