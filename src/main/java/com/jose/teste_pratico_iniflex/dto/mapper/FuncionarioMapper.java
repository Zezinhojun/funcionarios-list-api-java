package com.jose.teste_pratico_iniflex.dto.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.model.Funcionario;

@Component
public class FuncionarioMapper {

    public FuncionarioDTO toDto(Optional<Funcionario> funcionarioOpt) {
        return funcionarioOpt.map(funcionario -> new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getDataNascimento(),
                funcionario.getFuncao(),
                funcionario.getSalario())).orElse(null);
    }

    public Funcionario toEntity(FuncionarioDTO funcionarioDTO) {
        if (funcionarioDTO == null) {
            return null;
        }
        return new Funcionario(
                funcionarioDTO.id(),
                funcionarioDTO.nome(),
                funcionarioDTO.dataNascimento(),
                funcionarioDTO.salario(),
                funcionarioDTO.funcao());
    }

    public List<FuncionarioDTO> toDtoList(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Optional::ofNullable)
                .map(this::toDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Funcionario> toEntityList(List<FuncionarioDTO> funcionarioDTOs) {
        return funcionarioDTOs.stream()
                .map(this::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
