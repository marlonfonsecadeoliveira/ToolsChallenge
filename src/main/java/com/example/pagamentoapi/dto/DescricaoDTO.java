package com.example.pagamentoapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DescricaoDTO {
    private double valor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

    private String estabelecimento;
    private String nsu;
    private String codigoAutorizacao;
    private String status;


}
