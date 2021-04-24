package br.edu.ifma.si.esii.frete.service;

import br.edu.ifma.si.esii.frete.model.Cidade;
import br.edu.ifma.si.esii.frete.model.Cliente;
import br.edu.ifma.si.esii.frete.model.Frete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FreteServiceTest {
    @Autowired
    FreteService freteService;
    @Autowired
    CidadeService cidadeService;
    @Autowired
    ClienteService clienteService;

    Cidade cidade;
    Cliente cliente;
    Frete frete;

    @BeforeEach
    void setUp() {
        cidade = new Cidade("São Luís", "MA", new BigDecimal(1535.60));
        cidadeService.salva(cidade);

        cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        clienteService.salva(cliente);

        frete = new Frete(cidade, cliente, "notebooks", 2534.98, new BigDecimal(25934.87));
        freteService.salva(frete);
    }

    @AfterEach
    void tearDown() {
        cidadeService.removeTudo();
        clienteService.removeTudo();
        freteService.removeTudo();
    }

    /*@Test
    void calculaValorDoFrete() {
        freteService.calculaValorDoFrete(frete);
        assertEquals(26885.4, (frete.getValor()));
    }*/
}