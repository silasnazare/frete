package br.edu.ifma.si.esii.frete.repository;

import br.edu.ifma.si.esii.frete.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String nome);
    Optional<Cidade> findByUf(String uf);
}
