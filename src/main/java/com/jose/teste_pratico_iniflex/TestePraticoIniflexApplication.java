package com.jose.teste_pratico_iniflex;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.github.javafaker.Faker;
import com.jose.teste_pratico_iniflex.model.Funcionario;
import com.jose.teste_pratico_iniflex.repository.FuncionarioRepository;

@SpringBootApplication
public class TestePraticoIniflexApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestePraticoIniflexApplication.class, args);
	}

	// @Bean
	// CommandLineRunner initDatabase(FuncionarioRepository funcionarioRepository) {
	// Faker faker = new Faker(Locale.of("pt", "BR")); // Updated line
	// return args -> {
	// funcionarioRepository.deleteAll();

	// for (int i = 0; i < 10; i++) { // Altere o número de registros conforme
	// necessário
	// Funcionario f = new Funcionario();
	// f.setNome(faker.name().fullName());
	// LocalDate dataNascimento =
	// faker.date().birthday().toInstant().atZone(ZoneId.systemDefault())
	// .toLocalDate();
	// f.setDataNascimento(dataNascimento);
	// f.setFuncao(faker.job().title());

	// // Usando randomDouble() para gerar um número decimal aleatório
	// double salarioDouble = faker.number().randomDouble(2, 500, 3000); // Gera um
	// número aleatório entre 500
	// // e 3000
	// BigDecimal salarioBigDecimal = BigDecimal.valueOf(salarioDouble);
	// f.setSalario(salarioBigDecimal);

	// funcionarioRepository.save(f);
	// }
	// };
	// }
}
