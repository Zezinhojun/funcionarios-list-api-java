package com.jose.teste_pratico_iniflex.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.service.FuncionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Validated
@RestController
@Configuration
@RequestMapping("/api/funcionarios")
@Tag(name = "Funcionários", description = "APIs para gerenciamento de funcionários")
@AllArgsConstructor

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Operation(summary = "Insere funcionarios fictícios", description = "Insere funcionarios fictícios na base de dados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
    })
    @GetMapping("/inserir-funcionarios")
    public ResponseEntity<String> inserirFuncionarios() {
        List<FuncionarioDTO> funcionariosInseridos = funcionarioService.gerarFuncionariosFicticios();
        int quantidadeInserida = funcionariosInseridos.size();
        String mensagem = String.format("Inseridos %d funcionários fictícios com sucesso!", quantidadeInserida);
        return ResponseEntity.ok(mensagem);
    }

    @Operation(summary = "Remove um funcionário pelo nome", description = "Deleta um funcionário da base de dados com base no nome fornecido.", responses = {
            @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado.")
    })
    @DeleteMapping("/{nome}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFuncionarioPorNome(@PathVariable @NotNull String nome) {
        funcionarioService.removerFuncionarioPorNome(nome);
    }

    @Operation(summary = "Listar todos os funcionários", description = " Retorna uma lista de todos os funcionários cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    @GetMapping("")
    public ResponseEntity<List<FuncionarioDTO>> listarTodosOsFuncionarios() {
        List<FuncionarioDTO> funcionariosDTO = funcionarioService.listarTodosOsFuncionarios();
        return ResponseEntity.ok(funcionariosDTO);
    }

    @Operation(summary = "Aumentar salário de todos os funcionários em 10%", description = "Aumenta o salário de todos os funcionários em 10%.", responses = {
            @ApiResponse(responseCode = "200", description = "Salários aumentados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/aumentar-salario")
    public ResponseEntity<List<FuncionarioDTO>> aumentarSalario() {
        List<FuncionarioDTO> funcionariosAtualizados = funcionarioService.aumentarSalario();
        return ResponseEntity.ok(funcionariosAtualizados);
    }

    @Operation(summary = "Agrupar funcionários por função", description = "Agrupa os funcionários por função.", responses = {
            @ApiResponse(responseCode = "200", description = "Funcionários agrupados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/agrupados")
    public ResponseEntity<Map<String, List<FuncionarioDTO>>> getFuncionariosAgrupados() {
        Map<String, List<FuncionarioDTO>> funcionariosAgrupados = funcionarioService.agruparPorFuncao();
        return ResponseEntity.ok(funcionariosAgrupados);
    }

    @Operation(summary = "Listar funcionários aniversariantes", description = "Retorna uma lista de funcionários que fazem aniversário no mês atual.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de aniversariantes retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/aniversariantes")
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionariosAniversariantes() {
        List<FuncionarioDTO> aniversariantes = funcionarioService.listarFuncionariosAniversariantes();
        return ResponseEntity.ok(aniversariantes);
    }

    @Operation(summary = "Obter funcionário de maior idade", description = "Retorna o funcionário de maior idade.", responses = {
            @ApiResponse(responseCode = "200", description = "Funcionario com maior idade retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    @GetMapping("/maior-idade")
    public ResponseEntity<Map<String, Object>> funcionarioMaiorIdade() {
        Map<String, Object> funcionarioDetalhes = funcionarioService.funcionarioMaisVelho();
        return ResponseEntity.ok(funcionarioDetalhes);
    }

    @Operation(summary = "Listar funcionários em ordem alfabética", description = "Retorna uma lista de funcionários ordenada alfabeticamente.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/ordenado-alfabetica")
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionariosOrdenAlfabetica() {
        List<FuncionarioDTO> funcionariosOrdenados = funcionarioService.listarFuncionariosOrdenAlfabetica();
        return ResponseEntity.ok(funcionariosOrdenados);
    }

    @Operation(summary = "Obter o total dos salários dos funcionários", description = "Retorna o valor total dos salários de todos os funcionários.", responses = {
            @ApiResponse(responseCode = "200", description = "Total de salários retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/total-salarios")
    public ResponseEntity<BigDecimal> getTotalSalarios() {
        BigDecimal totalSalarios = funcionarioService.calcularTotalSalarios();
        return ResponseEntity.ok(totalSalarios);
    }

    @Operation(summary = "Calcular salários mínimos dos funcionários", description = "Calcula quantos salários mínimos cada funcionário recebe.", responses = {
            @ApiResponse(responseCode = "200", description = "Salários mínimos calculados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a solicitação"),
            @ApiResponse(responseCode = "404", description = "Não foram encontrados funcionários."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/salarios-minimos")
    public ResponseEntity<List<Map<String, Object>>> calcularSalariosMinimosDosFuncionarios() {
        List<Map<String, Object>> salariosMinimos = funcionarioService
                .calcularQuantificarSalariosMinimosDosFuncionarios();
        return ResponseEntity.ok(salariosMinimos);
    }

}
