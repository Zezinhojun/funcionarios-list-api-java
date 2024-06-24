package com.jose.teste_pratico_iniflex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.teste_pratico_iniflex.model.Funcionario;
import com.jose.teste_pratico_iniflex.repository.FuncionarioRepository;
import com.jose.teste_pratico_iniflex.service.FuncionarioService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
@AllArgsConstructor

public class FuncionarioController {

    private FuncionarioRepository funcionarioRepository;
    private FuncionarioService funcionarioService;

    @GetMapping("/inserir-funcionarios")
    public String inserirFuncionarios() {
        funcionarioService.inserirFuncionarios();
        return "Funcionários inseridos com sucesso!";
    }

    @DeleteMapping("/nome/{nome}")
    public ResponseEntity<Void> removerFuncionarioPorNome(@PathVariable String nome) {
        try {
            funcionarioService.removerFuncionarioPorNome(nome);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Funcionario>> listarTodosOsFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarTodosOsFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/aumentar-salario")
    public String aumentarSalario() {
        funcionarioService.aumentarSalario();
        return "Salários atualizados com sucesso!";
    }

    @GetMapping("/agrupados")
    public ResponseEntity<Map<String, List<Funcionario>>> getFuncionariosAgrupados() {
        Map<String, List<Funcionario>> funcionariosAgrupados = funcionarioService.agruparPorFuncao();
        return ResponseEntity.ok(funcionariosAgrupados);
    }

    @GetMapping("/aniversariantes")
    public String imprimirFuncionariosAniversariantes() {
        funcionarioService.imprimirFuncionariosAniversariantes();
        return "Funcionários que fazem aniversário nos meses de outubro e dezembro foram impressos.";
    }

}
