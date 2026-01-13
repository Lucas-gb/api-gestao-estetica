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

    // 1. DADOS PESSOAIS
    private String nome;
    private String dataNascimento;
    private Integer idade;
    private String profissao;
    private String telefone;
    private String email;
    private String endereco;

    // 2. HISTÓRICO DE SAÚDE
    private boolean usaMarcaPasso;
    private boolean gestanteLactante;
    private boolean historicoHerpes;

    private boolean tratamentoMedico;
    private String tratamentoMedicoQual;

    private boolean possuiAlergias;
    private String alergiasQual;

    private boolean usaAcidos;
    private String usaAcidosQual;

    private boolean problemasHormonais;
    private String problemasHormonaisQual;

    // --- 3. HÁBITOS ---
    private boolean exposicaoSol;
    private boolean usaFiltroSolar;
    private boolean tabagismo;
    private String rotinaCuidados; // Texto longo
    @Enumerated(EnumType.STRING)
    private QualidadeSono qualidadeSono;
    public enum QualidadeSono { BOA, REGULAR, RUIM }

    // 4. AVALIAÇÃO BIOTIPOLÓGICA
    private String biotipoCutaneo; // Eudérmica, Lipídica, Alípica, Mista
    private String fototipo; // I a VI

    private String observacoesAdicionais;
}