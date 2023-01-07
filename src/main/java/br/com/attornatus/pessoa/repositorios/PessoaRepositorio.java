package br.com.attornatus.pessoa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatus.pessoa.entidades.Pessoa;

public interface PessoaRepositorio extends JpaRepository<Pessoa, Long> {
}