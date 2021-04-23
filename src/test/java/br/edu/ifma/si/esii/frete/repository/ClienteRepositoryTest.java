package br.edu.ifma.si.esii.frete.repository;

import br.edu.ifma.si.esii.frete.model.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ClienteRepositoryTest {
    @Autowired
    ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        clienteRepository.save(cliente);
        cliente = new Cliente("Rita Teixeira", "Calhau", "98982");
        clienteRepository.save(cliente);
        cliente = new Cliente("Gandalf", "Centro", "98983");
        clienteRepository.save(cliente);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
    }

    @Test
    void salva() {
        List<Cliente> clientes = clienteRepository.findAll();
        assertEquals(3, clientes.size());
    }

    @Test
    void remove() {
        clienteRepository.deleteAll();
        List<Cliente> clientes = clienteRepository.findAll();
        assertEquals(0, clientes.size());
    }

    @Test
    void buscaPorNome() {
        Optional<Cliente> optionalCliente = clienteRepository.findByNome("Silas Nazare");
        assertTrue(optionalCliente.get().getNome().equals("Silas Nazare"));
    }

    @Test
    void buscaPorTelefone() {
        Optional<Cliente> optionalCliente = clienteRepository.findByTelefone("98983");
        assertTrue(optionalCliente.get().getTelefone().equals("98983"));
    }
}