package br.com.attornatus.pessoa.controladores.excecoes;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.attornatus.pessoa.servicos.excecoes.BancoDeDadosExcecao;
import br.com.attornatus.pessoa.servicos.excecoes.RecursoNaoEncontradoExcecao;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
    
    @ExceptionHandler(RecursoNaoEncontradoExcecao.class)
    public ResponseEntity<StandardError> resourceNotFound(RecursoNaoEncontradoExcecao e, HttpServletRequest request) {
        String error = "Recurso nao encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BancoDeDadosExcecao.class)
    public ResponseEntity<StandardError> database(BancoDeDadosExcecao e, HttpServletRequest request) {
        String error = "Erro no banco de dados";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}