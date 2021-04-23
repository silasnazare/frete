package br.edu.ifma.si.esii.frete.controller;

import br.edu.ifma.si.esii.frete.model.Cliente;
import br.edu.ifma.si.esii.frete.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public Page<Cliente> clientes(Pageable paginacao) {
        return clienteService.buscaComPaginacao(paginacao);
    }

    @GetMapping("/nome")
    public ResponseEntity<Cliente> buscaPorNome(String nome) {
        return clienteService.buscaPorNome(nome).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefone")
    public ResponseEntity<Cliente> buscaPorTelefone(String telefone) {
        return clienteService.buscaPorTelefone(telefone).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscaPorId(@PathVariable Long id) {
        return clienteService.buscaPorId(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> salva(@RequestBody @Valid Cliente cliente, UriComponentsBuilder builder) {
        final Cliente clienteSalvo = clienteService.salva(cliente);
        final URI uri = builder.path("/clientes/{id}").buildAndExpand(clienteSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualiza(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteService.buscaPorId(id);
        if (clienteOptional.isPresent()) {
            cliente.setId(id);
            Cliente clienteAtualizado = clienteService.salva(cliente);
            return ResponseEntity.ok(clienteAtualizado);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        clienteService.removePorId(id);
    }
}