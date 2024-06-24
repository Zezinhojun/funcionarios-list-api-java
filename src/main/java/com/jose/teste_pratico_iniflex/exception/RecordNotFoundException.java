package com.jose.teste_pratico_iniflex.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String nome) {
        super("Registro não encontrado com o nome : " + nome);
    }
}