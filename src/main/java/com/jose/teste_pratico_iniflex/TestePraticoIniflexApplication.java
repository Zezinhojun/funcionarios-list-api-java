package com.jose.teste_pratico_iniflex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jose.teste_pratico_iniflex.dto.FuncionarioDTO;
import com.jose.teste_pratico_iniflex.service.FuncionarioService;

@SpringBootApplication
public class TestePraticoIniflexApplication implements CommandLineRunner {

	private final FuncionarioService funcionarioService;

	public TestePraticoIniflexApplication(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestePraticoIniflexApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// 3.1 Inserir todos os funcionários fictícios
		List<FuncionarioDTO> funcionarios = funcionarioService.gerarFuncionariosFicticios();

		// 3.2 Remover o funcionário “João” da lista
		funcionarioService.removerFuncionarioPorNome("João");
		System.out.println("Funcionário 'João' foi removido da lista.");

		// 3.3 Imprimir todos os funcionários com todas suas informações em formato de
		System.out.println("-----------------------------------------------------------------------------");
		System.out.printf("| %-20s | %-15s | %-15s | %-10s |\n", "Nome", "Data Nascimento", "Salário (R$)", "Função");
		System.out.println("-----------------------------------------------------------------------------");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

		for (FuncionarioDTO func : funcionarios) {
			String nome = func.nome();
			String dataNascimento = sdf
					.format(Date.from(func.dataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			String salario = "R$ " + df.format(func.salario());
			String funcao = func.funcao();

			System.out.printf("| %-20s | %-15s | %-15s | %-10s |\n", nome, dataNascimento, salario, funcao);
		}

		System.out.println("-----------------------------------------------------------------------------");

		// 3.4 Aumentar o salário de todos os funcionários em 10%
		funcionarios = funcionarioService.aumentarSalario();

		// 3.3 (Repetido) Imprimir todos os funcionários atualizados após o aumento de
		// salário
		System.out.println("Funcionários atualizados após o aumento de salário:");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.printf("| %-20s | %-15s | %-15s | %-10s |\n", "Nome", "Data Nascimento", "Salário (R$)", "Função");
		System.out.println("-----------------------------------------------------------------------------");

		for (FuncionarioDTO func : funcionarios) {
			String nome = func.nome();
			String dataNascimento = sdf
					.format(Date.from(func.dataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			String salario = "R$ " + df.format(func.salario());
			String funcao = func.funcao();

			System.out.printf("| %-20s | %-15s | %-15s | %-10s |\n", nome, dataNascimento, salario, funcao);
		}

		System.out.println("-----------------------------------------------------------------------------");

		// 3.5 Agrupar funcionários por função em um MAP
		Map<String, List<FuncionarioDTO>> agrupadosPorFuncao = funcionarioService.agruparPorFuncao();

		// 3.6 Imprimir funcionários agrupados por função de forma compacta
		agrupadosPorFuncao.forEach((funcao, funcs) -> {
			System.out.println(funcao + ":");
			funcs.forEach(func -> System.out.println(
					"    - " + func.nome() + ", " + func.dataNascimento() + ", R$ " + df.format(func.salario())));
			System.out.println();
		});

		// 3.7 Imprimir funcionários que fazem aniversário em outubro (10) ou dezembro
		// (12)
		System.out.println("Funcionários que fazem aniversário em outubro (10) ou dezembro (12):");
		System.out.printf("| %-20s | %-15s |\n", "Nome", "Data de Nascimento");
		System.out.println("---------------------------------------");

		funcionarios.stream()
				.filter(func -> {
					if (func.dataNascimento() == null) {
						return false;
					}
					int month = func.dataNascimento().getMonthValue();
					return month == 10 || month == 12;
				})
				.forEach(func -> {
					String nome = func.nome();
					LocalDate localDate = func.dataNascimento();
					Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
					String dataNascimento = sdf.format(date);

					System.out.printf("| %-20s | %-15s |\n", nome, dataNascimento);
					System.out.println("---------------------------------------");
				});

		// 3.9 Imprimir o funcionário mais velho
		System.out.println("Funcionário mais velho:");
		Map<String, ?> funcionarioMaisVelho = funcionarioService.funcionarioMaisVelho();
		String nomeMaisVelho = (String) funcionarioMaisVelho.get("nome");
		int idadeMaisVelho = (int) funcionarioMaisVelho.get("idade");
		System.out.printf("Nome: %-20s | Idade: %d\n", nomeMaisVelho, idadeMaisVelho);
		System.out.println("-------------------------");

		// 3.10 Imprimir a lista de funcionários por ordem alfabética
		List<FuncionarioDTO> funcionariosOrdenados = funcionarioService.listarFuncionariosOrdenAlfabetica();
		System.out.println("Lista de funcionários em ordem alfabética:");
		System.out.printf("| %-20s |\n", "Nome");
		System.out.println("-------------------------");

		funcionariosOrdenados.forEach(func -> {
			String nome = func.nome();
			System.out.printf("| %-20s |\n", nome);
			System.out.println("-------------------------");
		});

		// 3.11 Imprimir o total dos salários dos funcionários
		BigDecimal totalSalarios = funcionarioService.calcularTotalSalarios();
		System.out.println("Total dos salários: R$ " + df.format(totalSalarios));
		System.out.println("-------------------------");

		// 3.12 Imprimir quantos salários mínimos ganha cada funcionário
		BigDecimal salarioMinimo = new BigDecimal("1212.00");
		System.out.println("Quantidade de salários mínimos que cada funcionário recebe:");
		funcionarios.forEach(func -> {
			BigDecimal salariosMinimos = func.salario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
			System.out.printf("Nome: %-20s | Salários mínimos: %.2f\n", func.nome(), salariosMinimos);
			System.out.println("-------------------------");
		});

	}
}
