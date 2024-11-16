package com.example.pagamentoapi.test;

import com.example.pagamentoapi.controller.TransacaoController;
import com.example.pagamentoapi.dto.PagamentoDTO;
import com.example.pagamentoapi.dto.TransacaoResponseDTO;
import com.example.pagamentoapi.model.Transacao;
import com.example.pagamentoapi.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @InjectMocks
    private TransacaoController transacaoController;

    @MockBean
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRealizarPagamentoComArquivo() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        PagamentoDTO pagamentoDTO = objectMapper.readValue(
                getClass().getClassLoader().getResource("pagamento_simulacao.json"),
                PagamentoDTO.class
        );
        ResponseEntity<TransacaoResponseDTO> response = transacaoController.realizarPagamento(pagamentoDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode(),"Realizou o Pagamento");
    }

    @Test
    void testBuscarTransacao_Sucesso() {
        String transacaoId = "d99cc9be-1dd2-494b-951e-5cd42302d7fa";
        Transacao transacaoSimulada = criarTransacaoSimulada();
        when(transacaoService.buscarTransacao(transacaoId)).thenReturn(Optional.of(transacaoSimulada));
        ResponseEntity<TransacaoResponseDTO> response = transacaoController.buscarTransacao(transacaoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoSimulada.getId().toString(), "d99cc9be-1dd2-494b-951e-5cd42302d7fa");
    }

    @Test
    void testBuscarTransacao_NaoEncontrada() {
        String transacaoId = "999999999";
        when(transacaoService.buscarTransacao(transacaoId)).thenReturn(Optional.empty());
        ResponseEntity<TransacaoResponseDTO> response = transacaoController.buscarTransacao(transacaoId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testBuscarTodasTransacoes() {
        when(transacaoService.buscarTodasTransacoes()).thenReturn(Collections.emptyList());
        ResponseEntity<List<TransacaoResponseDTO>> response = transacaoController.buscarTodasTransacoes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void testEstornarPagamento_Sucesso() {
        // Setup
        String transacaoId = "d99cc9be-1dd2-494b-951e-5cd42302d7fa";
        Transacao transacaoSimulada = criarTransacaoSimulada();
        transacaoSimulada.setStatus(Transacao.Status.CANCELADO);
        when(transacaoService.estornarPagamento(transacaoId)).thenReturn(Optional.of(transacaoSimulada));
        ResponseEntity<TransacaoResponseDTO> response = transacaoController.estornarPagamento(transacaoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Transacao.Status.CANCELADO, transacaoSimulada.getStatus());

    }

    @Test
    void testEstornarPagamento_NaoEncontrado() {
        String transacaoId = "999999999";
        when(transacaoService.estornarPagamento(transacaoId)).thenReturn(Optional.empty());
        ResponseEntity<TransacaoResponseDTO> response = transacaoController.estornarPagamento(transacaoId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Transacao criarTransacaoSimulada() {
        Transacao transacao = new Transacao();
        transacao.setId(UUID.fromString("d99cc9be-1dd2-494b-951e-5cd42302d7fa"));
        transacao.setCartao("4444********1234");
        transacao.setEstabelecimento("PetShop Mundo cão");
        transacao.setValor(1600.5);
        transacao.setNsu("7628163859");
        transacao.setCodigoAutorizacao("470927334");
        transacao.setStatus(Transacao.Status.AUTORIZADO);
        return transacao;
    }
}
