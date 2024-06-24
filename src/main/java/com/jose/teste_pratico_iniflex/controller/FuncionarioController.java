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

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Validated
@RestController
@Configuration
@RequestMapping("/api/funcionarios")
@AllArgsConstructor

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping("/inserir-funcionarios")
    public ResponseEntity<String> inserirFuncionarios() {
        List<FuncionarioDTO> funcionariosInseridos = funcionarioService.gerarFuncionariosFicticios();
        int quantidadeInserida = funcionariosInseridos.size();
        String mensagem = String.format("Inseridos %d funcionários fictícios com sucesso!", quantidadeInserida);
        return ResponseEntity.ok(mensagem);
    }

    @DeleteMapping("/{nome}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFuncionarioPorNome(@PathVariable @NotNull String nome) {
        funcionarioService.removerFuncionarioPorNome(nome);
    }

    @GetMapping("")
    public ResponseEntity<List<FuncionarioDTO>> listarTodosOsFuncionarios() {
        List<FuncionarioDTO> funcionariosDTO = funcionarioService.listarTodosOsFuncionarios();
        return ResponseEntity.ok(funcionariosDTO);
    }

    @GetMapping("/aumentar-salario")
    public ResponseEntity<List<FuncionarioDTO>> aumentarSalario() {
        List<FuncionarioDTO> funcionariosAtualizados = funcionarioService.aumentarSalario();
        return ResponseEntity.ok(funcionariosAtualizados);
    }

    @GetMapping("/agrupados")
    public ResponseEntity<Map<String, List<FuncionarioDTO>>> getFuncionariosAgrupados() {
        Map<String, List<FuncionarioDTO>> funcionariosAgrupados = funcionarioService.agruparPorFuncao();
        return ResponseEntity.ok(funcionariosAgrupados);
    }

    @GetMapping("/aniversariantes")
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionariosAniversariantes() {
        List<FuncionarioDTO> aniversariantes = funcionarioService.listarFuncionariosAniversariantes();
        return ResponseEntity.ok(aniversariantes);
    }

    @GetMapping("/maior-idade")
    public ResponseEntity<Map<String, Object>> funcionarioMaiorIdade() {
        Map<String, Object> funcionarioDetalhes = funcionarioService.funcionarioMaisVelho();
        return ResponseEntity.ok(funcionarioDetalhes);
    }

    @GetMapping("/ordenado-alfabetica")
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionariosOrdenAlfabetica() {
        List<FuncionarioDTO> funcionariosOrdenados = funcionarioService.listarFuncionariosOrdenAlfabetica();
        return ResponseEntity.ok(funcionariosOrdenados);
    }

    @GetMapping("/total-salarios")
    public ResponseEntity<BigDecimal> getTotalSalarios() {
        BigDecimal totalSalarios = funcionarioService.calcularTotalSalarios();
        return ResponseEntity.ok(totalSalarios);
    }

    @GetMapping("/salarios-minimos")
    public ResponseEntity<List<Map<String, Object>>> calcularSalariosMinimosDosFuncionarios() {
        List<Map<String, Object>> salariosMinimos = funcionarioService
                .calcularQuantificarSalariosMinimosDosFuncionarios();
        return ResponseEntity.ok(salariosMinimos);
    }

}
