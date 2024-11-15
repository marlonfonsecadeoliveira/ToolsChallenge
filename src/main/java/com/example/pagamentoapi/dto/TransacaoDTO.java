package com.example.pagamentoapi.dto;

import lombok.Data;

@Data
public class TransacaoDTO {
    private String cartao;
    private String id;
    private DescricaoDTO descricao;
    private FormaPagamentoDTO formaPagamento;


}
