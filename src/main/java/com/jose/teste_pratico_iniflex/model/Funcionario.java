package com.jose.teste_pratico_iniflex.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private String funcao;

    public Funcionario(Long id, String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(id, nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }
}
