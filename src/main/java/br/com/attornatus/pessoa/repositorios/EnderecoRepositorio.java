package br.com.attornatus.pessoa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatus.pessoa.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {
}