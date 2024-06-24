package com.jose.teste_pratico_iniflex.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.dto.mapper.FuncionarioMapper;
import com.jose.teste_pratico_iniflex.model.Funcionario;
import com.jose.teste_pratico_iniflex.repository.FuncionarioRepository;

import jakarta.persistence.EntityNotFoundException;

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
        for (int i = 0; i < 400; i++) {
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

    public List<Funcionario> listarTodosOsFuncionarios() {
        return funcionarioRepository.findAll();
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

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public void imprimirFuncionariosAniversariantes() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        List<Funcionario> funcionariosFiltrados = funcionarios.stream()
                .filter(funcionario -> {
                    LocalDate dataNascimento = funcionario.getDataNascimento(); // Supondo que getDataNascimento()
                                                                                // retorne LocalDate
                    return dataNascimento.getMonthValue() == 10 || dataNascimento.getMonthValue() == 12;
                })
                .collect(Collectors.toList());
        funcionariosFiltrados.forEach(funcionario -> {
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println("ID: " + funcionario.getId());
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento());
            System.out.println("Salário: " + formatarValorNumerico(funcionario.getSalario()));
            System.out.println("-------------------------");
        });
    }

}
