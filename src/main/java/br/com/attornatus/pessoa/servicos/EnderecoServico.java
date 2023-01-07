package br.com.attornatus.pessoa.servicos;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatus.pessoa.entidades.Endereco;
import br.com.attornatus.pessoa.repositorios.EnderecoRepositorio;
import br.com.attornatus.pessoa.servicos.excecoes.BancoDeDadosExcecao;
import br.com.attornatus.pessoa.servicos.excecoes.RecursoNaoEncontradoExcecao;

@Service
public class EnderecoServico {
    
    @Autowired
    private EnderecoRepositorio repositorio;

    public List<Endereco> encontrarTodos() {
        return repositorio.findAll();
    }

    public Endereco encontrarPorId(Long id) {
        Optional<Endereco> endereco = repositorio.findById(id);
        return endereco.orElseThrow(() -> new RecursoNaoEncontradoExcecao(id));
    }

    public Endereco inserir(Endereco endereco) {
        if (Objects.nonNull(endereco.getId())) {
            throw  new BancoDeDadosExcecao("Id diferente de nulo");
        }
        else {
            return repositorio.save(endereco);
        }
    }
}