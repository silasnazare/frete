package br.edu.ifma.si.esii.frete.controller;

import br.edu.ifma.si.esii.frete.model.Cliente;
import br.edu.ifma.si.esii.frete.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    ClienteRepository clienteRepository;

    Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        clienteRepository.save(cliente);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
    }

    // GET

    @Test
    void mostraClienteComGetForEntity() {
        ResponseEntity<Cliente> resposta = testRestTemplate.getForEntity("/clientes/{id}", Cliente.class, cliente.getId());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(resposta.getHeaders().getContentType(), MediaType.parseMediaType("application/json"));
        assertEquals(cliente, resposta.getBody());
    }

    @Test
    void mostraClienteComComGetForObject() {
        Cliente resposta = testRestTemplate.getForObject("/clientes/{id}", Cliente.class, cliente.getId());
        assertEquals(cliente, resposta);
    }

    @Test
    void clienteNaoEncontradoComGetForEntity() {
        ResponseEntity<Cliente> resposta = testRestTemplate.getForEntity("/clientes/{id}", Cliente.class, 100);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    void clienteInexistente() {
        Cliente resposta = testRestTemplate.getForObject("/clientes/{id}", Cliente.class, 100);
        assertNull(resposta);
    }

    // POST

    @Test
    void salva() {
        Cliente cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente);
        ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes", HttpMethod.POST, httpEntity, Cliente.class);
        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
        Cliente resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(cliente.getNome(), resultado.getNome());
        assertEquals(cliente.getEndereco(), resultado.getEndereco());
        assertEquals(cliente.getTelefone(), resultado.getTelefone());
        clienteRepository.deleteAll();
    }

    @Test
    public void salvaComPostForEntity() {
        Cliente cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente);
        ResponseEntity<Cliente> resposta = testRestTemplate.postForEntity("/clientes", httpEntity, Cliente.class);
        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
        Cliente resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(cliente.getNome(), resultado.getNome());
        assertEquals(cliente.getEndereco(), resultado.getEndereco());
        assertEquals(cliente.getTelefone(), resultado.getTelefone());
        clienteRepository.deleteAll();
    }

    @Test
    public void salvaComPostForObject() {
        Cliente cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente);
        Cliente resultado = testRestTemplate.postForObject("/clientes", httpEntity, Cliente.class);

        assertNotNull(resultado.getId());
        assertEquals(cliente.getNome(), resultado.getNome());
        assertEquals(cliente.getEndereco(), resultado.getEndereco());
        assertEquals(cliente.getTelefone(), resultado.getTelefone());
        clienteRepository.deleteAll();
    }

    // PUTS e DELETES

    @Test
    public void atualiza() {
        cliente.setNome("Rita Teixeira");
        HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente);
        ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes/{id}", HttpMethod.PUT, httpEntity, Cliente.class, cliente.getId());

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Cliente resultado = resposta.getBody();
        assertEquals(cliente.getId(), resultado.getId());
        assertEquals("Calhau", resultado.getEndereco());
        assertEquals("98981", resultado.getTelefone());
        assertEquals("Rita Teixeira", resultado.getNome());
    }

    @Test
    public void atualizaComPut() {
        cliente.setNome("Rita Teixeira");
        testRestTemplate.put("/clientes/{id}", cliente, cliente.getId());

        Cliente resultado = clienteRepository.findById(cliente.getId()).get();
        assertEquals("Calhau", resultado.getEndereco());
        assertEquals("98981", resultado.getTelefone());
        assertEquals("Rita Teixeira", resultado.getNome());
    }

    @Test
    public void remove() {
        ResponseEntity<Cliente> resposta = testRestTemplate.exchange("/clientes/{id}", HttpMethod.DELETE, null, Cliente.class, cliente.getId());

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    public void removePorId() {
        testRestTemplate.delete("/clientes/"+cliente.getId());

        final Optional<Cliente> resultado = clienteRepository.findById(cliente.getId());
        assertEquals(Optional.empty(), resultado);
    }
}