package com.example.pagamentoapi.service;

import com.example.pagamentoapi.dto.PagamentoDTO;
import com.example.pagamentoapi.model.Transacao;
import com.example.pagamentoapi.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public Transacao realizarPagamento(PagamentoDTO pagamentoDTO) {
        Transacao transacao = new Transacao();
        transacao.setCartao(pagamentoDTO.getTransacao().getCartao());
        transacao.setId(UUID.fromString(UUID.randomUUID().toString()));
        transacao.setValor(pagamentoDTO.getTransacao().getDescricao().getValor());
        transacao.setDataHora(LocalDateTime.now());
        transacao.setEstabelecimento(pagamentoDTO.getTransacao().getDescricao().getEstabelecimento());
        transacao.setNsu(gerarNsu());
        transacao.setCodigoAutorizacao(gerarAutorizacao());
        transacao.setFormaPagamento(Transacao.FormaPagamento.valueOf(pagamentoDTO.getTransacao().getFormaPagamento().getTipo()));
        transacao.setStatus(Transacao.Status.AUTORIZADO);
        transacao.setParcelas(pagamentoDTO.getTransacao().getFormaPagamento().getParcelas());


        return repository.save(transacao);
    }

    public Optional<Transacao> buscarTransacao(String id) {
        UUID uuid = UUID.fromString(id);
        return repository.findById(uuid);
    }

    public List<Transacao> buscarTodasTransacoes() {
        return repository.findAll();
    }

    public Optional<Transacao> estornarPagamento(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Transacao> transacaoOpt = repository.findById(uuid);
        transacaoOpt.ifPresent(transacao -> transacao.setStatus(Transacao.Status.CANCELADO));
        return transacaoOpt;
    }

    private String gerarNsu() {
        Random random = new Random();
        long nsu = 1000000000L + (long)(random.nextDouble() * 9000000000L); // Gera um número de 10 dígitos
        return String.valueOf(nsu);
    }
    private String gerarAutorizacao() {
        Random random = new Random();
        int nsu = 100000000 + random.nextInt(900000000); // Gera um número de 9 dígitos
        return String.valueOf(nsu);
    }

    public String convertData(Transacao transacao) {
        return transacao.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

}
