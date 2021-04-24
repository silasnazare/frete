package br.edu.ifma.si.esii.frete.repository;

import br.edu.ifma.si.esii.frete.model.Cidade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CidadeRepositoryTest {
    @Autowired
    CidadeRepository cidadeRepository;

    @BeforeEach
    void setUp() {
        Cidade cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        cidadeRepository.save(cidade);
        cidade = new Cidade("Belém", "PA", new BigDecimal(1437.98));
        cidadeRepository.save(cidade);
        cidade = new Cidade("Teresina", "PI", new BigDecimal(2095.81));
        cidadeRepository.save(cidade);
    }

    @AfterEach
    void tearDown() {
        cidadeRepository.deleteAll();
    }

    @Test
    void salva() {
        List<Cidade> cidades = cidadeRepository.findAll();
        assertEquals(3, cidades.size());
    }

    @Test
    void remove() {
        cidadeRepository.deleteAll();
        List<Cidade> cidades = cidadeRepository.findAll();
        assertEquals(0, cidades.size());
    }

    @Test
    void buscaPorNome() {
        Optional<Cidade> optionalCidade = cidadeRepository.findByNome("São Luís");
        assertTrue(optionalCidade.get().getNome().equals("São Luís"));
    }

    @Test
    void buscaPorTelefone() {
        Optional<Cidade> optionalCidade = cidadeRepository.findByUf("PA");
        assertTrue(optionalCidade.get().getUf().equals("PA"));
    }
}