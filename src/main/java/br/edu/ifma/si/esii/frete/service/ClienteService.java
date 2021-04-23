package br.edu.ifma.si.esii.frete.service;

import br.edu.ifma.si.esii.frete.model.Cliente;
import br.edu.ifma.si.esii.frete.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> buscaComPaginacao(Pageable paginacao) {
        return clienteRepository.findAll(paginacao);
    }

    public Optional<Cliente> buscaPorNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    public Optional<Cliente> buscaPorTelefone(String telefone) {
        return clienteRepository.findByTelefone(telefone);
    }

    public Optional<Cliente> buscaPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente salva(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void removePorId(Long id) {
        clienteRepository.deleteById(id);
    }
    
    @Transactional
    public void removeTudo() {
        clienteRepository.deleteAll();
    }
}
