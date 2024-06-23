package com.jose.teste_pratico_iniflex.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.dto.mapper.FuncionarioMapper;
import com.jose.teste_pratico_iniflex.model.Funcionario;
import com.jose.teste_pratico_iniflex.repository.FuncionarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final Faker faker = new Faker();
    private final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    public void inserirFuncionarios() {
        List<FuncionarioDTO> funcionarioDTO = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String nome = faker.name().fullName();
            LocalDate dataNascimento = faker.date().birthday().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                    .toLocalDate();
            BigDecimal salario = BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 5000));
            String funcao = faker.job().title();

            FuncionarioDTO dto = new FuncionarioDTO(null, nome, dataNascimento, funcao, salario);
            funcionarioDTO.add(dto);
        }

        for (FuncionarioDTO dto : funcionarioDTO) {
            Funcionario funcionario = funcionarioMapper.toEntity(dto);
            funcionarioRepository.save(funcionario);
        }
    }

    public void removerFuncionarioPorNome(String nome) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByNome(nome);
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();
            funcionarioRepository.delete(funcionario);
        } else {
            throw new EntityNotFoundException("Funcionário com nome '" + nome + "' não encontrado");
        }
    }

    public void listarTodosOsFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        for (Funcionario funcionario : funcionarios) {
            System.out.println("ID: " + funcionario.getId());
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println("Salário: " + formatarValorNumerico(funcionario.getSalario())); // Supondo que getSalario
                                                                                               // retorne BigDecimal
            System.out.println("Data de Nascimento: " + formatarData(funcionario.getDataNascimento()));
            System.out.println("-------------------------");
        }
    }

    public void aumentarSalario() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
            funcionarioRepository.save(funcionario);
        }
    }

    private String formatarData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    private String formatarValorNumerico(BigDecimal valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(valor.doubleValue());
    }

}
