package br.edu.ifma.si.esii.frete.service;

import br.edu.ifma.si.esii.frete.model.Frete;
import br.edu.ifma.si.esii.frete.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class FreteService {
    private final FreteRepository freteRepository;

    @Autowired
    public FreteService(FreteRepository freteRepository) {
        this.freteRepository = freteRepository;
    }

    public Page<Frete> buscaComPaginacao(Pageable paginacao) {
        return freteRepository.findAll(paginacao);
    }

    public Optional<Frete> buscaPorId(Long id) {
        return freteRepository.findById(id);
    }

    @Transactional
    public Frete salva(Frete frete) {
        return freteRepository.save(frete);
    }

    @Transactional
    public void removePorId(Long id) {
        freteRepository.deleteById(id);
    }
    
    @Transactional
    public void removeTudo() {
        freteRepository.deleteAll();
    }

    public BigDecimal calculaValorDoFrete(Frete frete) {
        BigDecimal taxa = frete.getCidade().getTaxa();
        frete.setValor(new BigDecimal(frete.getPeso() * 10).add(taxa).setScale(1, RoundingMode.UP));
        return frete.getValor();
    }
}
