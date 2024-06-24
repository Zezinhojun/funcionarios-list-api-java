package com.jose.teste_pratico_iniflex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jose.teste_pratico_iniflex.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByNome(String nome);

    @Query("SELECT f.funcao AS funcao, COLLECT_SET(f) FROM Funcionario f GROUP BY f.funcao")
    List<Object[]> findFuncionariosAgrupadosByFuncao();
}
