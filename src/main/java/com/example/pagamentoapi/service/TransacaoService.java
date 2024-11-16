package com.example.pagamentoapi.service;

import com.example.pagamentoapi.dto.PagamentoDTO;
import com.example.pagamentoapi.dto.TransacaoResponseDTO;
import com.example.pagamentoapi.model.Transacao;
import com.example.pagamentoapi.repository.TransacaoRepository;
import com.example.pagamentoapi.utils.TransacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransacaoService {


    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private TransacaoUtil transacaoUtil;

    public Transacao realizarPagamento(PagamentoDTO pagamentoDTO) {
        Transacao transacao = new Transacao();
        transacao.setCartao(pagamentoDTO.getTransacao().getCartao());
        transacao.setId(UUID.fromString(UUID.randomUUID().toString()));
        transacao.setValor(pagamentoDTO.getTransacao().getDescricao().getValor());
        transacao.setDataHora(LocalDateTime.now());
        transacao.setEstabelecimento(pagamentoDTO.getTransacao().getDescricao().getEstabelecimento());
        transacao.setNsu(transacaoUtil.gerarNsu());
        transacao.setCodigoAutorizacao(transacaoUtil.gerarAutorizacao());
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
        return repository.findById(UUID.fromString(id)).map(transacao -> {
            transacao.setStatus(Transacao.Status.CANCELADO);
            return repository.save(transacao); // Salva e retorna a transação
        });
    }


    public TransacaoResponseDTO mapToResponseDTO(Transacao transacao) {
        TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();
        TransacaoResponseDTO.TransacaoDTO transacaoDTO = new TransacaoResponseDTO.TransacaoDTO();

        transacaoDTO.setCartao(transacao.getCartao());
        transacaoDTO.setId(String.valueOf(transacao.getId()));

        TransacaoResponseDTO.TransacaoDTO.DescricaoDTO descricao = new TransacaoResponseDTO.TransacaoDTO.DescricaoDTO();
        descricao.setValor(transacao.getValor());
        descricao.setDataHora(transacaoUtil.convertData(transacao)); // Ajuste se necessário para o formato correto
        descricao.setEstabelecimento(transacao.getEstabelecimento());
        descricao.setNsu(transacao.getNsu());
        descricao.setCodigoAutorizacao(transacao.getCodigoAutorizacao());
        descricao.setStatus(transacao.getStatus());
        transacaoDTO.setDescricao(descricao);

        TransacaoResponseDTO.TransacaoDTO.FormaPagamentoDTO formaPagamento = new TransacaoResponseDTO.TransacaoDTO.FormaPagamentoDTO();
        formaPagamento.setTipo(transacao.getFormaPagamento().name());
        formaPagamento.setParcelas(transacao.getParcelas());
        transacaoDTO.setFormaPagamento(formaPagamento);

        responseDTO.setTransacao(transacaoDTO);
        return responseDTO;
    }





}
