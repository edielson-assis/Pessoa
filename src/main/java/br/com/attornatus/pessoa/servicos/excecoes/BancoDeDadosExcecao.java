package br.com.attornatus.pessoa.servicos.excecoes;

public class BancoDeDadosExcecao extends RuntimeException {
    
    public BancoDeDadosExcecao(String msg) {
        super(msg);
    }
}