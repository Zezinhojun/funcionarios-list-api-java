package com.jose.teste_pratico_iniflex.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.github.javafaker.Faker;
import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.dto.mapper.FuncionarioMapper;
import com.jose.teste_pratico_iniflex.exception.RecordNotFoundException;
import com.jose.teste_pratico_iniflex.model.Funcionario;
import com.jose.teste_pratico_iniflex.repository.FuncionarioRepository;

import jakarta.validation.constraints.NotNull;

@Validated
@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final Faker faker = new Faker();
    private final BigDecimal minSalary = new BigDecimal("1212.00");
    private static final String FUNCIONARIO_NAO_ENCONTRADO = "Não foram encontrados funcionários.";

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    public List<FuncionarioDTO> gerarFuncionariosFicticios() {
        List<FuncionarioDTO> funcionariosDTO = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
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

        Long idJoao = (long) (funcionariosDTO.size() + 1);
        String nomeJoao = "João";
        LocalDate dataNascimentoJoao = LocalDate.of(1995, 10, 24);
        BigDecimal salarioJoao = new BigDecimal("3500.00");
        String funcaoJoao = "Desenvolvedor Fullstack";

        FuncionarioDTO joaoDTO = new FuncionarioDTO(idJoao, nomeJoao, dataNascimentoJoao, funcaoJoao, salarioJoao);
        funcionariosDTO.add(joaoDTO);

        List<Funcionario> funcionarios = new ArrayList<>();
        for (FuncionarioDTO dto : funcionariosDTO) {
            Funcionario funcionario = funcionarioMapper.toEntity(dto);
            funcionarios.add(funcionario);
        }

        funcionarioRepository.saveAll(funcionarios);

        return funcionariosDTO;
    }

    public String removerFuncionarioPorNome(@NotNull String nome) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByNome(nome);
        if (funcionarioOpt.isPresent()) {
            funcionarioRepository.delete(funcionarioOpt.get());
            return "Funcionário deletado com sucesso: " + nome;
        } else {
            throw new RecordNotFoundException("Esse funcionário não existe: " + nome);
        }
    }

    public List<FuncionarioDTO> listarTodosOsFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }

        return funcionarios.stream()
                .map(funcionarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<FuncionarioDTO> aumentarSalario() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }

        DecimalFormat df = new DecimalFormat("#.##");

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioOriginal = funcionario.getSalario();
            BigDecimal novoSalario = salarioOriginal.multiply(new BigDecimal("1.10"));
            String salarioFormatado = df.format(novoSalario);
            BigDecimal salarioFinal = new BigDecimal(salarioFormatado.replace(',', '.'));

            funcionario.setSalario(salarioFinal);
            funcionarioRepository.save(funcionario);
        }
        return funcionarios.stream()
                .map(funcionarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public Map<String, List<FuncionarioDTO>> agruparPorFuncao() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }

        return funcionarios.stream()
                .map(funcionarioMapper::toDto)
                .collect(Collectors.groupingBy(FuncionarioDTO::funcao));
    }

    public List<FuncionarioDTO> listarFuncionariosAniversariantes() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }

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
        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }

        Optional<Funcionario> funcionarioOpt = funcionarios.stream()
                .max(Comparator.comparingInt(
                        funcionario -> Period.between(funcionario.getDataNascimento(), LocalDate.now()).getYears()));

        if (!funcionarioOpt.isPresent()) {
            throw new RecordNotFoundException("Nenhum funcionário encontrado");
        }

        Funcionario funcionarioMaisVelho = funcionarioOpt.get();
        return Map.of(
                "nome", funcionarioMaisVelho.getNome(),
                "idade", Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears());
    }

    public List<FuncionarioDTO> listarFuncionariosOrdenAlfabetica() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }
        return funcionarios.stream()
                .map(funcionarioMapper::toDto)
                .sorted(Comparator.comparing(FuncionarioDTO::nome))
                .collect(Collectors.toList());
    }

    public BigDecimal calcularTotalSalarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }

        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Map<String, Object>> calcularQuantificarSalariosMinimosDosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        if (funcionarios.isEmpty()) {
            throw new RecordNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }

        return funcionarios.stream()
                .map(funcionario -> {
                    BigDecimal salarioAtual = funcionario.getSalario();
                    BigDecimal salarioMinimos = salarioAtual.divide(minSalary, 2, RoundingMode.HALF_UP);
                    Map<String, Object> mapaFuncionario = new HashMap<>();
                    mapaFuncionario.put("id", funcionario.getId());
                    mapaFuncionario.put("nome", funcionario.getNome());
                    mapaFuncionario.put("salarioMinimos", salarioMinimos.doubleValue());

                    return mapaFuncionario;
                })
                .collect(Collectors.toList());
    }
}
