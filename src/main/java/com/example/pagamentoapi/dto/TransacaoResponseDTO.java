package com.example.pagamentoapi.dto;

import com.example.pagamentoapi.model.Transacao;
import lombok.Data;

@Data
public class TransacaoResponseDTO {
    private TransacaoDTO transacao;

    @Data
    public static class TransacaoDTO {
        private String cartao;
        private String id;
        private DescricaoDTO descricao;
        private FormaPagamentoDTO formaPagamento;

        @Data
        public static class DescricaoDTO {
            private Double valor;
            private String dataHora;
            private String estabelecimento;
            private String nsu;
            private String codigoAutorizacao;
            private Transacao.Status status;
        }

        @Data
        public static class FormaPagamentoDTO {
            private String tipo;
            private int parcelas;
        }
    }
}
