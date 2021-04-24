package br.edu.ifma.si.esii.frete.controller;

import br.edu.ifma.si.esii.frete.model.Cidade;
import br.edu.ifma.si.esii.frete.repository.CidadeRepository;
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
class CidadeControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    CidadeRepository cidadeRepository;

    Cidade cidade;

    @BeforeEach
    void setUp() {
        cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        cidadeRepository.save(cidade);
    }

    @AfterEach
    void tearDown() {
        cidadeRepository.deleteAll();
    }

    // GET

    @Test
    void mostraCidadeComGetForEntity() {
        ResponseEntity<Cidade> resposta = testRestTemplate.getForEntity("/cidades/{id}", Cidade.class, cidade.getId());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(resposta.getHeaders().getContentType(), MediaType.parseMediaType("application/json"));
        assertEquals(cidade, resposta.getBody());
    }

    @Test
    void mostraCidadeComComGetForObject() {
        Cidade resposta = testRestTemplate.getForObject("/cidades/{id}", Cidade.class, cidade.getId());
        assertEquals(cidade, resposta);
    }

    @Test
    void cidadeNaoEncontradaComGetForEntity() {
        ResponseEntity<Cidade> resposta = testRestTemplate.getForEntity("/cidades/{id}", Cidade.class, 100);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    void cidadeInexistente() {
        Cidade resposta = testRestTemplate.getForObject("/cidades/{id}", Cidade.class, 100);
        assertNull(resposta);
    }

    // POST

    @Test
    void salva() {
        Cidade cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        HttpEntity<Cidade> httpEntity = new HttpEntity<>(cidade);
        ResponseEntity<Cidade> resposta = testRestTemplate.exchange("/cidades", HttpMethod.POST, httpEntity, Cidade.class);
        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
        Cidade resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(cidade.getNome(), resultado.getNome());
        assertEquals(cidade.getUf(), resultado.getUf());
        cidadeRepository.deleteAll();
    }

    @Test
    public void salvaComPostForEntity() {
        Cidade cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        HttpEntity<Cidade> httpEntity = new HttpEntity<>(cidade);
        ResponseEntity<Cidade> resposta = testRestTemplate.postForEntity("/cidades", httpEntity, Cidade.class);
        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Cidade resultado = resposta.getBody();

        assertNotNull(resultado.getId());
        assertEquals(cidade.getNome(), resultado.getNome());
        assertEquals(cidade.getUf(), resultado.getUf());
        cidadeRepository.deleteAll();
    }

    @Test
    public void salvaComPostForObject() {
        Cidade cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        HttpEntity<Cidade> httpEntity = new HttpEntity<>(cidade);
        Cidade resultado = testRestTemplate.postForObject("/cidades", httpEntity, Cidade.class);

        assertNotNull(resultado.getId());
        assertEquals(cidade.getNome(), resultado.getNome());
        assertEquals(cidade.getUf(), resultado.getUf());
        cidadeRepository.deleteAll();
    }

    // PUTS e DELETES

    @Test
    public void atualiza() {
        cidade.setNome("Imperatriz");
        HttpEntity<Cidade> httpEntity = new HttpEntity<>(cidade);
        ResponseEntity<Cidade> resposta = testRestTemplate.exchange("/cidades/{id}", HttpMethod.PUT, httpEntity, Cidade.class, cidade.getId());

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Cidade resultado = resposta.getBody();
        assertEquals(cidade.getId(), resultado.getId());
        assertEquals("MA", resultado.getUf());
        assertEquals("Imperatriz", resultado.getNome());
    }

    @Test
    public void atualizaComPut() {
        cidade.setNome("Imperatriz");
        testRestTemplate.put("/cidades/{id}", cidade, cidade.getId());

        Cidade resultado = cidadeRepository.findById(cidade.getId()).get();
        assertEquals("MA", resultado.getUf());
        //assertEquals(1535.60, resultado.getTaxa());
        assertEquals("Imperatriz", resultado.getNome());
    }

    @Test
    public void remove() {
        ResponseEntity<Cidade> resposta = testRestTemplate.exchange("/cidades/{id}", HttpMethod.DELETE, null, Cidade.class, cidade.getId());

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        assertNull(resposta.getBody());
    }

    @Test
    public void removePorId() {
        testRestTemplate.delete("/cidades/"+cidade.getId());

        final Optional<Cidade> resultado = cidadeRepository.findById(cidade.getId());
        assertEquals(Optional.empty(), resultado);
    }
}