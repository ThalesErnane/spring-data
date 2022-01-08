package br.com.alura.spring.data.repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> { // PagingAndSortingRepository
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	// Derived Query - queries criadas através de comandos Java
	// JPQL - queries criadas através de uma estrutura SQL, porém com os nomes das entidades Java
	// Native Query - queries padrões SQL que conseguimos executar no nosso Client SQL 
	
	// List<Funcionario> findAll();

	List<Funcionario> findByNome(String nome); // Derived Query 
	
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome "
			+ "AND f.salario >= :salario AND f.dataContratacao = :data") // JPQL
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);
	
	@Query("SELECT f FROM Funcionario f WHERE f.nome= :nome AND f.salario >= :salario AND f.dataContratacao = :data")
	public List<Funcionario> findByNomeSalarioMaiorDataContratacao(@Param("nome") String nome, @Param("salario") BigDecimal salario, @Param("data") LocalDate data);
	
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", // Native Query
			nativeQuery = true)
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	// Criação da projeção
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();
}