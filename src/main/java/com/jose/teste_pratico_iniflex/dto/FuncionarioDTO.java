package com.jose.teste_pratico_iniflex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioDTO(

                Long id,
                @NotNull String nome,
                LocalDate dataNascimento,
                @NotNull @NotBlank String funcao,
                BigDecimal salario

) {

}
