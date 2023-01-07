package br.com.attornatus.pessoa.servicos;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.attornatus.pessoa.entidades.Pessoa;
import br.com.attornatus.pessoa.repositorios.PessoaRepositorio;
import br.com.attornatus.pessoa.servicos.excecoes.BancoDeDadosExcecao;
import br.com.attornatus.pessoa.servicos.excecoes.RecursoNaoEncontradoExcecao;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PessoaServico {
    
    @Autowired
    private PessoaRepositorio repositorio;

    public List<Pessoa> encontrarTodos() {
        return repositorio.findAll();
    }

    public Pessoa encontrarPorId(Long id) {
        Optional<Pessoa> pessoa = repositorio.findById(id);
        return pessoa.orElseThrow(() -> new RecursoNaoEncontradoExcecao(id));
    }

    public Pessoa inserir(Pessoa pessoa) {
        if (Objects.nonNull(pessoa.getId())) {
            throw  new BancoDeDadosExcecao("Id diferente de nulo");
        } 
        return repositorio.save(pessoa);
    }

    public void deletar(Long id) {
        try {
            repositorio.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new RecursoNaoEncontradoExcecao(id);
        } 
        catch (DataIntegrityViolationException e) {
            throw new BancoDeDadosExcecao(e.getMessage());
        }
    }

    public Pessoa atualizar(Long id, Pessoa obj) {
        try {
            Pessoa entidade = repositorio.getReferenceById(id);
            atualizacaoDeDados(entidade, obj);
            return repositorio.save(entidade);
        }
        catch (EntityNotFoundException e) {
            throw new RecursoNaoEncontradoExcecao(id);
        }   
    }

    private void atualizacaoDeDados(Pessoa entidade, Pessoa obj) {
        entidade.setNome(obj.getNome());
        entidade.setDataDeNascimento(obj.getDataDeNascimento());
    }
}