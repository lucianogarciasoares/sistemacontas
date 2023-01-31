package br.com.cotiinformatica.repositories;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.cotiinformatica.entities.Conta;

public class ContaRepository {
	
	private JdbcTemplate jdbcTemplate;
	public ContaRepository(DataSource dataSource) {
		
		jdbcTemplate = new JdbcTemplate(dataSource);	
	}
}