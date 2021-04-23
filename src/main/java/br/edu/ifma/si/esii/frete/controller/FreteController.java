package br.edu.ifma.si.esii.frete.controller;

import br.edu.ifma.si.esii.frete.model.Frete;
import br.edu.ifma.si.esii.frete.service.FreteService;
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
@RequestMapping("/fretes")
public class FreteController {
    private final FreteService freteService;

    @Autowired
    public FreteController(FreteService freteService) {
        this.freteService = freteService;
    }

    @GetMapping
    public Page<Frete> fretes(Pageable paginacao) {
        return freteService.buscaComPaginacao(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frete> buscaPorId(@PathVariable Long id) {
        return freteService.buscaPorId(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Frete> salva(@RequestBody @Valid Frete frete, UriComponentsBuilder builder) {
        final Frete freteSalvo = freteService.salva(frete);
        final URI uri = builder.path("/fretes/{id}").buildAndExpand(freteSalvo.getId()).toUri();
        return ResponseEntity.ok(freteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frete> atualiza(@PathVariable Long id, @RequestBody @Valid Frete frete) {
        Optional<Frete> freteOptional = freteService.buscaPorId(id);
        if (freteOptional.isPresent()) {
            frete.setId(id);
            Frete freteAtualizado = freteService.salva(frete);
            return ResponseEntity.ok(freteAtualizado);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        freteService.removePorId(id);
    }
}
