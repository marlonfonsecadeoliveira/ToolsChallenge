package com.example.pagamentoapi.test;

import com.example.pagamentoapi.controller.TransacaoController;
import com.example.pagamentoapi.dto.PagamentoDTO;
import com.example.pagamentoapi.dto.TransacaoResponseDTO;
import com.example.pagamentoapi.model.Transacao;
import com.example.pagamentoapi.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransacaoControllerTest {

    @InjectMocks
    private TransacaoController transacaoController;

    @Mock
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRealizarPagamento() {
        // Dado um PagamentoDTO e Transacao simulados
        PagamentoDTO pagamentoDTO = new PagamentoDTO(); // Preencher conforme necessário
        Transacao transacaoSimulada = new Transacao();
        transacaoSimulada.setId(UUID.fromString("0ec26c13-777e-4593-aad6-f69988e15afa"));

        when(transacaoService.realizarPagamento(any(PagamentoDTO.class))).thenReturn(transacaoSimulada);

        // Quando o método realizarPagamento é chamado
        ResponseEntity<Transacao> response = transacaoController.realizarPagamento(pagamentoDTO);

        // Então verifica-se que o status é 200 OK e o corpo contém a transação
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoSimulada, response.getBody());
    }

    @Test
    void testBuscarTransacao_Sucesso() {
        // Dado um ID de transação existente
        String transacaoId = "100023568900001";
        Transacao transacaoSimulada = new Transacao();
        transacaoSimulada.setId(UUID.fromString(transacaoId));

        when(transacaoService.buscarTransacao(transacaoId)).thenReturn(Optional.of(transacaoSimulada));

        // Quando o método buscarTransacao é chamado
        ResponseEntity<TransacaoResponseDTO> response = transacaoController.buscarTransacao(transacaoId);

        // Então verifica-se que o status é 200 OK e o corpo contém a transação
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoSimulada, response.getBody());
    }

    @Test
    void testBuscarTransacao_NaoEncontrada() {
        // Dado um ID de transação inexistente
        String transacaoId = "999999999";
        when(transacaoService.buscarTransacao(transacaoId)).thenReturn(Optional.empty());

        // Quando o método buscarTransacao é chamado
        ResponseEntity<TransacaoResponseDTO> response = transacaoController.buscarTransacao(transacaoId);

        // Então verifica-se que o status é 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testBuscarTodasTransacoes() {
        // Dado que o serviço retorna uma lista vazia de transações
        when(transacaoService.buscarTodasTransacoes()).thenReturn(List.of());

        // Quando o método buscarTodasTransacoes é chamado
        ResponseEntity<List<TransacaoResponseDTO>> response = transacaoController.buscarTodasTransacoes();

        // Então verifica-se que o status é 200 OK e a lista de transações está vazia
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void testEstornarPagamento_Sucesso() {
        // Dado um ID de transação existente que pode ser estornado
        String transacaoId = "100023568900001";
        Transacao transacaoSimulada = new Transacao();
        transacaoSimulada.setId(UUID.fromString(transacaoId));
        transacaoSimulada.setStatus(Transacao.Status.valueOf("CANCELADO"));

        when(transacaoService.estornarPagamento(transacaoId)).thenReturn(Optional.of(transacaoSimulada));

        // Quando o método estornarPagamento é chamado
        ResponseEntity<Transacao> response = transacaoController.estornarPagamento(transacaoId);

        // Então verifica-se que o status é 200 OK e o corpo contém a transação estornada
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoSimulada, response.getBody());
    }

    @Test
    void testEstornarPagamento_NaoEncontrado() {
        // Dado um ID de transação inexistente
        String transacaoId = "999999999";
        when(transacaoService.estornarPagamento(transacaoId)).thenReturn(Optional.empty());

        // Quando o método estornarPagamento é chamado
        ResponseEntity<Transacao> response = transacaoController.estornarPagamento(transacaoId);

        // Então verifica-se que o status é 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }




}
