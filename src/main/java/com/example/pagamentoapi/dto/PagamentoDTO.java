package com.example.pagamentoapi.dto;

import com.example.pagamentoapi.model.Transacao.FormaPagamento;
import com.example.pagamentoapi.model.Transacao.Status;
import lombok.Data;

@Data
public class PagamentoDTO {
    private TransacaoDTO transacao;

}
