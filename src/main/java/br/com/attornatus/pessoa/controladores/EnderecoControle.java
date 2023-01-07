package br.com.attornatus.pessoa.controladores;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.attornatus.pessoa.entidades.Endereco;
import br.com.attornatus.pessoa.servicos.EnderecoServico;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoControle {
    
    @Autowired
    private EnderecoServico servico;

    @GetMapping
    public ResponseEntity<List<Endereco>> encontrarTodos() {
        List<Endereco> list = servico.encontrarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Endereco> encontrarPorId(@PathVariable Long id) {
        Endereco endereco = servico.encontrarPorId(id);
        return ResponseEntity.ok().body(endereco);
    }

    @PostMapping
    public ResponseEntity<Endereco> inserir(@RequestBody Endereco endereco) {
        endereco = servico.inserir(endereco);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).body(endereco);
    }
}