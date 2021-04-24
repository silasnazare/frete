package br.edu.ifma.si.esii.frete.controller;

import br.edu.ifma.si.esii.frete.model.Cidade;
import br.edu.ifma.si.esii.frete.model.Cliente;
import br.edu.ifma.si.esii.frete.model.Frete;
import br.edu.ifma.si.esii.frete.repository.CidadeRepository;
import br.edu.ifma.si.esii.frete.repository.ClienteRepository;
import br.edu.ifma.si.esii.frete.repository.FreteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FreteControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    FreteRepository freteRepository;
    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    ClienteRepository clienteRepository;

    Frete frete;
    Cidade cidade;
    Cliente cliente;

    @BeforeEach
    void setUp() {
        cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        cidadeRepository.save(cidade);
        cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        clienteRepository.save(cliente);
        frete = new Frete(cidade, cliente, "notebooks", 2534.98, new BigDecimal(25934.87));
        freteRepository.save(frete);
    }

    @AfterEach
    void tearDown() {
        freteRepository.deleteAll();
    }

    // GET

    @Test
    void mostraFreteComGetForEntity() {
        ResponseEntity<Frete> resposta = testRestTemplate.getForEntity("/fretes/{id}", Frete.class, frete.getId());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(resposta.getHeaders().getContentType(), MediaType.parseMediaType("application/json"));
        assertEquals(frete, resposta.getBody());
    }

    @Test
    void mostraFreteComComGetForObject() {
        Frete resposta = testRestTemplate.getForObject("/fretes/{id}", Frete.class, frete.getId());
        assertEquals(frete, resposta);
    }

    @Test
    void freteNaoEncontradoComGetForEntity() {
        ResponseEntity<Frete> resposta = testRestTemplate.getForEntity("/fretes/{id}", Frete.class, 100);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    // POST

    @Test
    void salva() {
        Frete frete = new Frete(cidade, cliente, "notebooks", 2534.98, new BigDecimal(25934.87));
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<Frete> resposta = testRestTemplate.exchange("/fretes", HttpMethod.POST, httpEntity, Frete.class);
        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Frete resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(frete.getCidade(), resultado.getCidade());
        assertEquals(frete.getCidade(), resultado.getCidade());
        assertEquals(frete.getDescricao(), resultado.getDescricao());
        assertEquals(frete.getValor(), resultado.getValor());
        freteRepository.deleteAll();
    }

    @Test
    public void salvaComPostForEntity() {
        Frete frete = new Frete(cidade, cliente, "notebooks", 2534.98, new BigDecimal(25934.87));
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<Frete> resposta = testRestTemplate.postForEntity("/fretes", httpEntity, Frete.class);
        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Frete resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(frete.getCidade(), resultado.getCidade());
        assertEquals(frete.getCidade(), resultado.getCidade());
        assertEquals(frete.getDescricao(), resultado.getDescricao());
        assertEquals(frete.getValor(), resultado.getValor());
        freteRepository.deleteAll();
    }

    @Test
    public void salvaComPostForObject() {
        Frete frete = new Frete(cidade, cliente, "notebooks", 2534.98, new BigDecimal(25934.87));
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        Frete resultado = testRestTemplate.postForObject("/fretes", httpEntity, Frete.class);

        assertNotNull(resultado.getId());
        assertEquals(frete.getCidade(), resultado.getCidade());
        assertEquals(frete.getCidade(), resultado.getCidade());
        assertEquals(frete.getDescricao(), resultado.getDescricao());
        assertEquals(frete.getValor(), resultado.getValor());
        freteRepository.deleteAll();
    }

    // PUTS e DELETES

    @Test
    public void atualiza() {
        frete.setDescricao("smartphones");
        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<Frete> resposta = testRestTemplate.exchange("/fretes/{id}", HttpMethod.PUT, httpEntity, Frete.class, frete.getId());

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Frete resultado = resposta.getBody();
        assertEquals(frete.getId(), resultado.getId());
        assertEquals(cidade, resultado.getCidade());
        assertEquals(cliente, resultado.getCliente());
        assertEquals(2534.98, resultado.getPeso());
        //assertEquals(25934.87, resultado.getValor());
        assertEquals("smartphones", resultado.getDescricao());
    }

    @Test
    public void atualizaComPut() {
        frete.setDescricao("smartphones");
        testRestTemplate.put("/fretes/{id}", frete, frete.getId());

        Frete resultado = freteRepository.findById(frete.getId()).get();
        assertEquals(cidade, resultado.getCidade());
        assertEquals(cliente, resultado.getCliente());
        assertEquals(2534.98, resultado.getPeso());
        //assertEquals(25934.87, resultado.getValor());
        assertEquals("smartphones", resultado.getDescricao());
    }

    @Test
    public void remove() {
        ResponseEntity<Frete> resposta = testRestTemplate.exchange("/fretes/{id}", HttpMethod.DELETE, null, Frete.class, frete.getId());

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    /*@Test
    public void removePorId() {
        testRestTemplate.delete("/fretes"+cliente.getId());

        final Optional<Frete> resultado = freteRepository.findById(frete.getId());
        assertEquals(Optional.empty(), resultado);
    }*/
}