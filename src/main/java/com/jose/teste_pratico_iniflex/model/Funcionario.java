package com.jose.teste_pratico_iniflex.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Funcionario extends Pessoa {

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotBlank
    @Size(max = 50)
    private String funcao;

    public Funcionario(Long id, String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(id, nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }
}
