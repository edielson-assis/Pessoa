package br.com.attornatus.pessoa.servicos.excecoes;

public class RecursoNaoEncontradoExcecao extends RuntimeException {
    
    public RecursoNaoEncontradoExcecao(Object id) {
        super("Recurso nao encontrado. Id " + id);
    }
}