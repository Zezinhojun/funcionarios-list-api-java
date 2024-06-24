package com.jose.teste_pratico_iniflex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioDTO(

                Long id,
                @NotNull String nome,
                @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
                @NotNull @NotBlank String funcao,
                BigDecimal salario

) {

}
