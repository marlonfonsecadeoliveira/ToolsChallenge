package com.example.pagamentoapi.controller;

import com.example.pagamentoapi.dto.PagamentoDTO;
import com.example.pagamentoapi.dto.TransacaoResponseDTO;
import com.example.pagamentoapi.model.Transacao;
import com.example.pagamentoapi.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/pagamentos")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> realizarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        Transacao transacao = transacaoService.realizarPagamento(pagamentoDTO);
        return ResponseEntity.ok(transacaoService.mapToResponseDTO(transacao));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> buscarTransacao(@PathVariable String id) {
        Optional<Transacao> transacaoOpt = transacaoService.buscarTransacao(id);
        return transacaoOpt
                .map(transacao -> ResponseEntity.ok(transacaoService.mapToResponseDTO(transacao)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> buscarTodasTransacoes() {
        List<Transacao> transacoes = transacaoService.buscarTodasTransacoes();
        List<TransacaoResponseDTO> resposta = transacoes.stream()
                .map(transacao -> transacaoService.mapToResponseDTO(transacao)) // Use uma expressão lambda
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }



    @PostMapping("/{id}/estorno")
    public ResponseEntity<TransacaoResponseDTO> estornarPagamento(@PathVariable String id) {
        return transacaoService.estornarPagamento(id)
                .map(transacao -> ResponseEntity.ok(transacaoService.mapToResponseDTO(transacao)))
                .orElse(ResponseEntity.notFound().build());
    }





}
