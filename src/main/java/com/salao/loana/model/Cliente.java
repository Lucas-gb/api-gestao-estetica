package com.salao.loana.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private Integer idade;
    private String  sexo;
    private String telefone;
    private String email;

    //Campos para a ficha de anamnese (discursivas)
    @Column(columnDefinition = "TEXT")
    private String rotinaDiaria;

    @Column(columnDefinition = "TEXT")
    private String alergiasEObservacoes;

    private LocalDateTime dataCadastro = LocalDateTime.now();
}