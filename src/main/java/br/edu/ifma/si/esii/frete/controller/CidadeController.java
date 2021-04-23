package br.edu.ifma.si.esii.frete.controller;

import br.edu.ifma.si.esii.frete.model.Cidade;
import br.edu.ifma.si.esii.frete.service.CidadeService;
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
@RequestMapping("/cidades")
public class CidadeController {
    private final CidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public Page<Cidade> cidades(Pageable paginacao) {
        return cidadeService.buscaComPaginacao(paginacao);
    }

    @GetMapping("/nome")
    public ResponseEntity<Cidade> buscaPorNome(String nome) {
        return cidadeService.buscaPorNome(nome).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uf")
    public ResponseEntity<Cidade> buscaPorUf(String uf) {
        return cidadeService.buscaPorUf(uf).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscaPorId(@PathVariable Long id) {
        return cidadeService.buscaPorId(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cidade> salva(@RequestBody @Valid Cidade cidade, UriComponentsBuilder builder) {
        final Cidade cidadeSalva = cidadeService.salva(cidade);
        final URI uri = builder.path("/cidades/{id}").buildAndExpand(cidadeSalva.getId()).toUri();
        return ResponseEntity.ok(cidadeSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualiza(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
        Optional<Cidade> cidadeOptional = cidadeService.buscaPorId(id);
        if (cidadeOptional.isPresent()) {
            cidade.setId(id);
            Cidade cidadeAtualizada = cidadeService.salva(cidade);
            return ResponseEntity.ok(cidadeAtualizada);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        cidadeService.removePorId(id);
    }
}
