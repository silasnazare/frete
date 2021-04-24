package br.edu.ifma.si.esii.frete.repository;

import br.edu.ifma.si.esii.frete.model.Cidade;
import br.edu.ifma.si.esii.frete.model.Cliente;
import br.edu.ifma.si.esii.frete.model.Frete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FreteRepositoryTest {
    @Autowired
    FreteRepository freteRepository;
    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        Cidade cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        cidadeRepository.save(cidade);
        Cliente cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        clienteRepository.save(cliente);
        Frete frete = new Frete(cidade, cliente, "notebooks", 2534.98, new BigDecimal(25934.87));
        freteRepository.save(frete);
        frete = new Frete(cidade, cliente, "celulares", 1744.98, new BigDecimal(16976.87));
        freteRepository.save(frete);
    }

    @AfterEach
    void tearDown() {
        freteRepository.deleteAll();
    }

    @Test
    void salva() {
        List<Frete> fretes = freteRepository.findAll();
        assertEquals(2, fretes.size());
    }

    @Test
    void remove() {
        freteRepository.deleteAll();
        List<Frete> fretes = freteRepository.findAll();
        assertEquals(0, fretes.size());
    }
}