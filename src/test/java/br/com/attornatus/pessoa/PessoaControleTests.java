package br.com.attornatus.pessoa;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pessoas")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Este teste vai retornar uma pessoa pelo ID e vai esperar um retorno de sucesso
	@Test
	public void deveRetornarUmaPessoaPeloIdEStatusOk() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Este teste vai retornar 200 e vai esperar um retorno de sucesso
	@Test
	public void deveAdicionarUmaPessoaERetornarSucesso() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(pessoa())))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Metodo para instanciar uma pessoa para testes
	private Pessoa pessoa() throws Exception {
		var pessoa = new Pessoa(1L, "Edielson", sdf.parse("23/04/1987"), null);
		return pessoa;
	}
}