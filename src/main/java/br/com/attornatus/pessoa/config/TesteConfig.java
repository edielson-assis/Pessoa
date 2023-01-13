package br.com.attornatus.pessoa.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.attornatus.pessoa.entidades.Endereco;
import br.com.attornatus.pessoa.entidades.Pessoa;
import br.com.attornatus.pessoa.repositorios.EnderecoRepositorio;
import br.com.attornatus.pessoa.repositorios.PessoaRepositorio;

@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        Endereco ed1 = new Endereco(null, "Rua Santos Souza", "103", "41555-003",  "Salvador");
        Endereco ed2 = new Endereco(null, "Avenida Otavio Magalhaes", "1007", "41678-023",  "Lauro de Freitas");
        Endereco ed3 = new Endereco(null, "Rua da Independencia", "10113", "40345-108",  "Camacari");
        Endereco ed4 = new Endereco(null, "Avenida Castro Alves", "115", "40345-551",  "Salvador");
        Endereco ed5 = new Endereco(null, "Rua Joao Silveira", "77", "40281-108",  "Camacari");

        enderecoRepositorio.saveAll(Arrays.asList(ed1, ed2, ed3, ed4, ed5));

        Pessoa p1 = new Pessoa(null, "Ana Pink", sdf.parse("23/08/1994"), ed2);
        Pessoa p2 = new Pessoa(null, "Erik Brow", sdf.parse("04/02/2006"), ed1);
        Pessoa p3 = new Pessoa(null, "Douglas White", sdf.parse("15/06/1987"), ed3);

        pessoaRepositorio.saveAll(Arrays.asList(p1, p2, p3));

        p1.getEnderecos().add(ed2);
        p1.getEnderecos().add(ed4);
        p2.getEnderecos().add(ed1);
        p2.getEnderecos().add(ed5);
        p3.getEnderecos().add(ed3);

        pessoaRepositorio.saveAll(Arrays.asList(p1, p2, p3));
    }
}