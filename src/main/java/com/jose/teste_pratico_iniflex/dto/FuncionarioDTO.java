package com.jose.teste_pratico_iniflex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioDTO(
                Long id,
                String nome,
                LocalDate dataNascimento,
                String funcao,
                BigDecimal salario) {
}
