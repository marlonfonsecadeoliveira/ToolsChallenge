package com.example.pagamentoapi.utils;

import com.example.pagamentoapi.model.Transacao;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Utilitário para operações relacionadas à transação.
 */
@Component
public class TransacaoUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final long NSU_BASE = 1000000000L;
    private static final int AUTORIZACAO_BASE = 100000000;

    private final Random random = new Random();

    /**
     * Gera um NSU (Número Sequencial Único) aleatório de 10 dígitos.
     *
     * @return NSU gerado como uma string.
     */
    public String gerarNsu() {
        long nsu = NSU_BASE + (long) (random.nextDouble() * (NSU_BASE * 9));
        return String.valueOf(nsu);
    }

    /**
     * Gera um código de autorização aleatório de 9 dígitos.
     *
     * @return Código de autorização gerado como uma string.
     */
    public String gerarAutorizacao() {
        int codigoAutorizacao = AUTORIZACAO_BASE + random.nextInt(AUTORIZACAO_BASE * 9);
        return String.valueOf(codigoAutorizacao);
    }

    /**
     * Converte a data e hora da transação para o formato "dd/MM/yyyy HH:mm:ss".
     *
     * @param transacao A transação que contém a data e hora.
     * @return Data e hora formatadas como string.
     */
    public String convertData(Transacao transacao) {
        return transacao.getDataHora().format(DATE_TIME_FORMATTER);
    }
}
