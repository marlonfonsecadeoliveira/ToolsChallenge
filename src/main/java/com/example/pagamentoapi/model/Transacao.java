package com.example.pagamentoapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    @Id
    private UUID id;

    private String cartao;
    private Double valor;
    private LocalDateTime dataHora;
    private String estabelecimento;
    private String nsu;
    private String codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private int parcelas;



    public enum FormaPagamento {
        AVISTA, PARCELADO_LOJA, PARCELADO_EMISSOR;
    }

    public enum Status{
        AUTORIZADO, NEGADO, CANCELADO;
    }

}
