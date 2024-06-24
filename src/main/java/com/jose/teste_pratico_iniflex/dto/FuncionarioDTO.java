package com.jose.teste_pratico_iniflex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FuncionarioDTO(

                @NotNull Long id,
                @NotNull @NotBlank @Length(max = 255) String nome,
                @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
                @NotNull @NotBlank @Length(max = 50) String funcao,
                @Positive BigDecimal salario

) {

}
