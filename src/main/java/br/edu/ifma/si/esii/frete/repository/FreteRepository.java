package br.edu.ifma.si.esii.frete.repository;

import br.edu.ifma.si.esii.frete.model.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {
    List<Frete> findByCliente_Nome(String nome);
}
