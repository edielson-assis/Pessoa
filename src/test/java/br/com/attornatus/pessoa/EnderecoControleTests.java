package br.com.attornatus.pessoa;

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

import br.com.attornatus.pessoa.controladores.EnderecoControle;
import br.com.attornatus.pessoa.entidades.Endereco;
import lombok.var;

@AutoConfigureMockMvc
@SpringBootTest
class EnderecoControleTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EnderecoControle enderecoControle;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(enderecoControle).build();
	}

	// Este teste vai solicitar a URL e vai esperar um retorno de sucesso
	@Test
	public void deveListarOsEnderecosERetornarStatus_200() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/enderecos")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Este teste vai retornar uma Endereco pelo ID e vai esperar um retorno de sucesso
	@Test
	public void deveRetornarUmEnderecoPeloIdEStatusOk() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/enderecos/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Este teste vai retornar 200 e vai esperar um retorno de sucesso
	@Test
	public void deveAdicionarUmEnderecoERetornarSucesso() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/enderecos")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(endereco())))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

    // Metodo para instanciar um endereco para testes
	private Endereco endereco() throws Exception {
		var endereco = new Endereco(1L, "Rua Santos Souza", "103", "41555-003",  "Salvador");
		return endereco;
	}
}