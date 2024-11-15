package com.example.pagamentoapi.controller;

import com.example.pagamentoapi.dto.PagamentoDTO;
import com.example.pagamentoapi.dto.TransacaoResponseDTO;
import com.example.pagamentoapi.model.Transacao;
import com.example.pagamentoapi.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagamentos")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Transacao> realizarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        Transacao transacao = transacaoService.realizarPagamento(pagamentoDTO);
        return ResponseEntity.ok(transacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> buscarTransacao(@PathVariable String id) {
        // Busca a transação pelo ID
        Optional<Transacao> transacaoOpt = transacaoService.buscarTransacao(id);

        // Se a transação for encontrada, mapeia para TransacaoResponseDTO, senão retorna 404
        return transacaoOpt.map(transacao -> {
            TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();
            TransacaoResponseDTO.TransacaoDTO transacaoDTO = new TransacaoResponseDTO.TransacaoDTO();

            // Setando dados da transação
            transacaoDTO.setCartao(transacao.getCartao());
            transacaoDTO.setId(transacao.getId().toString());

            // Setando a descrição
            TransacaoResponseDTO.TransacaoDTO.DescricaoDTO descricao = new TransacaoResponseDTO.TransacaoDTO.DescricaoDTO();
            descricao.setValor(transacao.getValor());
            descricao.setDataHora(transacaoService.convertData(transacao)); // Ajuste se necessário para o formato correto
            descricao.setEstabelecimento(transacao.getEstabelecimento());
            descricao.setNsu(transacao.getNsu());
            descricao.setCodigoAutorizacao(transacao.getCodigoAutorizacao());
            descricao.setStatus(transacao.getStatus());
            transacaoDTO.setDescricao(descricao);

            // Setando a forma de pagamento
            TransacaoResponseDTO.TransacaoDTO.FormaPagamentoDTO formaPagamento = new TransacaoResponseDTO.TransacaoDTO.FormaPagamentoDTO();
            formaPagamento.setTipo(transacao.getFormaPagamento().name());
            formaPagamento.setParcelas(transacao.getParcelas());
            transacaoDTO.setFormaPagamento(formaPagamento);

            // Setando o DTO final
            responseDTO.setTransacao(transacaoDTO);

            return ResponseEntity.ok(responseDTO); // Retorna o DTO mapeado
        }).orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrar a transação
    }


    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> buscarTodasTransacoes() {
        List<Transacao> transacoes = transacaoService.buscarTodasTransacoes();

        List<TransacaoResponseDTO> resposta = transacoes.stream().map(transacao -> {
            TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();
            TransacaoResponseDTO.TransacaoDTO transacaoDTO = new TransacaoResponseDTO.TransacaoDTO();

            // Setando dados da transação
            transacaoDTO.setCartao(transacao.getCartao());
            transacaoDTO.setId(transacao.getId().toString());

            // Setando a descrição
            TransacaoResponseDTO.TransacaoDTO.DescricaoDTO descricao = new TransacaoResponseDTO.TransacaoDTO.DescricaoDTO();
            descricao.setValor(transacao.getValor());
            descricao.setDataHora(transacaoService.convertData(transacao)); // Ajuste se necessário para o formato correto
            descricao.setEstabelecimento(transacao.getEstabelecimento());
            descricao.setNsu(transacao.getNsu());
            descricao.setCodigoAutorizacao(transacao.getCodigoAutorizacao());
            descricao.setStatus(transacao.getStatus());
            transacaoDTO.setDescricao(descricao);

            // Setando a forma de pagamento
            TransacaoResponseDTO.TransacaoDTO.FormaPagamentoDTO formaPagamento = new TransacaoResponseDTO.TransacaoDTO.FormaPagamentoDTO();
            // AQUI, OBTEVE O TIPO DE PAGAMENTO DO ENUM E PASSOU COMO STRING
            formaPagamento.setTipo(transacao.getFormaPagamento().name());
            formaPagamento.setParcelas(transacao.getParcelas());
            transacaoDTO.setFormaPagamento(formaPagamento);

            responseDTO.setTransacao(transacaoDTO);
            return responseDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }



    @PostMapping("/{id}/estorno")
    public ResponseEntity<Transacao> estornarPagamento(@PathVariable String id) {
        return transacaoService.estornarPagamento(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
