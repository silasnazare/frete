package br.edu.ifma.si.esii.frete.service;

import br.edu.ifma.si.esii.frete.model.Cidade;
import br.edu.ifma.si.esii.frete.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CidadeService {
    private final CidadeRepository cidadeRepository;

    @Autowired
    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public Page<Cidade> buscaComPaginacao(Pageable paginacao) {
        return cidadeRepository.findAll(paginacao);
    }

    public Optional<Cidade> buscaPorNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }

    public Optional<Cidade> buscaPorUf(String uf) {
        return cidadeRepository.findByUf(uf);
    }

    public Optional<Cidade> buscaPorId(Long id) {
        return cidadeRepository.findById(id);
    }

    @Transactional
    public Cidade salva(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void removePorId(Long id) {
        cidadeRepository.deleteById(id);
    }
    
    @Transactional
    public void removeTudo() {
        cidadeRepository.deleteAll();
    }
}
