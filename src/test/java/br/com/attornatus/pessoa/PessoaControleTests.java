package br.com.attornatus.pessoa;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.attornatus.pessoa.controladores.PessoaControle;
import br.com.attornatus.pessoa.entidades.Pessoa;
import lombok.var;

@AutoConfigureMockMvc
@SpringBootTest
class PessoaControleTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PessoaControle pessoaControle;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(pessoaControle).build();
	}

	// Este teste vai solicitar a URL e vai esperar um retorno de sucesso
	@Test
	public void deveListarAsPessoasERetornarStatus_200() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pessoas"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Este teste vai retornar uma pessoa pelo ID e vai esperar um retorno de
	// sucesso
	@Test
	public void deveRetornarUmaPessoaPeloIdEStatusOk() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/1"))
			.andExpect(MockMvcResultMatchers.status().isOk());

		// Verifica se o método de selecionar por id foi chamado
		verify(pessoaControle, times(1)).encontrarPorId(1L);
	}

	// Este teste vai retornar 200 e vai esperar um retorno de sucesso
	@Test
	public void deveAdicionarUmaPessoaERetornarSucesso() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(pessoa())))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Este teste vai atualizar uma pessoa e vai esperar um retorno de sucesso
	@Test
	public void deveAtualizarUmaPessoaERetornarSucesso() throws Exception {
		Pessoa pessoa = pessoa(); 
		when(pessoaControle.atualizar(1L, pessoa)).thenReturn(ResponseEntity.ok().body(pessoa));

		this.mockMvc.perform(MockMvcRequestBuilders.put("/pessoas/1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(pessoa)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Edielson"));

		// Verifica se o método de atualização foi chamado
		verify(pessoaControle, times(1)).atualizar(1L, pessoa);
	}

	// Este teste vai deletar uma pessoa e vai esperar um retorno de sucesso
	@Test
	public void deveDeletarUmaPessoaERetornarSucesso() throws Exception {
		when(pessoaControle.deletar(1L)).thenReturn(ResponseEntity.noContent().build());

		this.mockMvc.perform(MockMvcRequestBuilders.delete("/pessoas/1"))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		// Verifica se o método de exclusão foi chamado
		verify(pessoaControle, times(1)).deletar(1L);
	}

	// Metodo para instanciar uma pessoa para testes
	final private Pessoa pessoa() throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		final var endereco = new EnderecoControleTests();
		final var pessoa = new Pessoa(1L, "Edielson", sdf.parse("23/04/1987"), endereco.getEndereco());
		return pessoa;
	}
}