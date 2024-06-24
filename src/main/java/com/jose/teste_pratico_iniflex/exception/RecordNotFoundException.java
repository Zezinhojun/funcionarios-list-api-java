package com.jose.teste_pratico_iniflex.exception;

public class RecordNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String nome) {
        super("Registro não encontrado com o nome : " + nome);
    }
}