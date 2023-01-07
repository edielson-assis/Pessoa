package br.com.attornatus.pessoa.controladores;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.attornatus.pessoa.entidades.Pessoa;
import br.com.attornatus.pessoa.servicos.PessoaServico;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/pessoas")
public class PessoaControle {
    
    @Autowired
    private PessoaServico servico;

    @GetMapping
    public ResponseEntity<List<Pessoa>> encontrarTodos() {
        List<Pessoa> list = servico.encontrarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> encontrarPorId(@PathVariable Long id) {
        Pessoa pessoa = servico.encontrarPorId(id);
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> inserir(@RequestBody Pessoa pessoa) {
        pessoa = servico.inserir(pessoa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(uri).body(pessoa);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Pessoa> deletar(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        pessoa = servico.atualizar(id, pessoa);
        return ResponseEntity.ok().body(pessoa);
    }
}