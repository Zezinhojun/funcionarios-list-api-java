package com.jose.teste_pratico_iniflex.dto.mapper;

import org.springframework.stereotype.Component;

import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.model.Funcionario;

@Component
public class FuncionarioMapper {

    public FuncionarioDTO toDto(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getDataNascimento(),
                funcionario.getFuncao(), funcionario.getSalario());
    }

    public Funcionario toEntity(FuncionarioDTO funcionarioDTO) {
        if (funcionarioDTO == null) {
            return null;
        }
        Funcionario funcionario = new Funcionario();
        if (funcionarioDTO.id() != null) {
            funcionario.setId(funcionarioDTO.id());
        }

        funcionario.setNome(funcionarioDTO.nome());
        funcionario.setDataNascimento(funcionarioDTO.dataNascimento());
        funcionario.setFuncao(funcionarioDTO.funcao());
        funcionario.setSalario(funcionarioDTO.salario());

        return funcionario;
    }

}
