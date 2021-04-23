package br.edu.ifma.si.esii.frete.service;

import br.edu.ifma.si.esii.frete.exception.FreteException;
import br.edu.ifma.si.esii.frete.model.Cidade;
import br.edu.ifma.si.esii.frete.model.Cliente;
import br.edu.ifma.si.esii.frete.model.Frete;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
        cidade = new Cidade("São Luís", "MA", 1535.60);
        cidadeService.salva(cidade);
        
        cliente = new Cliente("Silas Nazare", "Calhau", "98981");
        clienteService.salva(cliente);
        
        frete = new Frete(cidade, cliente, "notebooks", 2534.98, BigDecimal.valueOf(25934.87));
        freteService.salva(frete);
    }

    @AfterEach
    void tearDown() {
        cidadeService.removeTudo();
        clienteService.removeTudo();
        freteService.removeTudo();
    }
    
    @Test
    void cadastraFreteSemCidadeException()  {
        FreteException exception = assertThrows(FreteException.class, () -> {frete.setCidade(null);
        freteService.salva(frete);}, "Deveria lançar um FreteException");
        assertTrue(exception.getMessage().contains("A Cidade é obrigatória!"));
    }
    
    @Test
    void cadastraFreteSemClienteException()  {
        FreteException exception = assertThrows(FreteException.class, () -> {frete.setCliente(null);
        freteService.salva(frete);}, "Deveria lançar um FreteException");
        assertTrue(exception.getMessage().contains("O Cliente é obrigatório!"));
    }
}