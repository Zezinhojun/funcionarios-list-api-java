package com.jose.teste_pratico_iniflex.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.dto.mapper.FuncionarioMapper;
import com.jose.teste_pratico_iniflex.exception.RecordNotFoundException;
import com.jose.teste_pratico_iniflex.model.Funcionario;
import com.jose.teste_pratico_iniflex.repository.FuncionarioRepository;

import jakarta.validation.constraints.NotNull;

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

    public List<FuncionarioDTO> gerarFuncionariosFicticios() {
        List<FuncionarioDTO> funcionariosDTO = new ArrayList<>();

        for (int i = 0; i < 10; i++) { // Gerando 10 funcionários fictícios
            Long id = (long) (i + 1);
            String nome = faker.name().fullName();
            LocalDate dataNascimento = faker.date().birthday().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                    .toLocalDate();
            BigDecimal salario = BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 5000));
            String funcao = faker.job().title();

            FuncionarioDTO funcionarioDTO = new FuncionarioDTO(id, nome, dataNascimento, funcao, salario);
            funcionariosDTO.add(funcionarioDTO);
        }

        List<Funcionario> funcionarios = new ArrayList<>();
        for (FuncionarioDTO dto : funcionariosDTO) {
            Funcionario funcionario = funcionarioMapper.toEntity(dto);
            funcionarios.add(funcionario);
        }

        funcionarioRepository.saveAll(funcionarios);

        return funcionariosDTO;
    }

    public FuncionarioDTO inserirFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario entity = funcionarioMapper.toEntity(funcionarioDTO);
        Funcionario savedEntity = funcionarioRepository.save(entity);
        return funcionarioMapper.toDto(savedEntity);
    }

    public void removerFuncionarioPorNome(@NotNull String nome) {
        funcionarioRepository.delete(funcionarioRepository.findByNome(nome)
                .orElseThrow(() -> new RecordNotFoundException(nome)));
    }

    public List<Funcionario> listarTodosOsFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public List<Funcionario> aumentarSalario() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        DecimalFormat df = new DecimalFormat("#.##"); // Define o padrão de formatação

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioOriginal = funcionario.getSalario();
            BigDecimal novoSalario = salarioOriginal.multiply(new BigDecimal("1.10"));
            String salarioFormatado = df.format(novoSalario);
            BigDecimal salarioFinal = new BigDecimal(salarioFormatado.replace(',', '.'));

            funcionario.setSalario(salarioFinal);
            funcionarioRepository.save(funcionario);
        }

        return funcionarios;
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public List<FuncionarioDTO> listarFuncionariosAniversariantes() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        List<Funcionario> funcionariosFiltrados = funcionarios.stream()
                .filter(funcionario -> {
                    LocalDate dataNascimento = funcionario.getDataNascimento();
                    return dataNascimento.getMonthValue() == 10 || dataNascimento.getMonthValue() == 12;
                })
                .collect(Collectors.toList());

        return funcionariosFiltrados.stream()
                .map(funcionarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public Map<String, Object> funcionarioMaisVelho() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        Optional<Funcionario> funcionarioOpt = funcionarios.stream()
                .max(Comparator.comparingInt(
                        funcionario -> Period.between(funcionario.getDataNascimento(), LocalDate.now()).getYears()));

        if (!funcionarioOpt.isPresent()) {
            throw new RecordNotFoundException("Nenhum funcionário encontrado");
        }

        Funcionario funcionarioMaisVelho = funcionarioOpt.get();
        Map<String, Object> funcionarioDetalhes = Map.of(
                "nome", funcionarioMaisVelho.getNome(),
                "idade", Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears());
        return funcionarioDetalhes;
    }

}
