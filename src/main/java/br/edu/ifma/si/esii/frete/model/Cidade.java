package br.edu.ifma.si.esii.frete.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "O nome da Cidade é obrigatório!")
    @Length(min = 3, max = 25)
    private String nome;
    @NotNull(message = "A sigla do estado é obrigatória!")
    @Length(min = 2, max = 2)
    private String uf;
    @Positive
    private BigDecimal taxa;

    public Cidade() {
    }

    public Cidade(String nome, String uf, BigDecimal taxa) {
        this.nome = nome;
        this.uf = uf;
        this.taxa = taxa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade)) return false;

        Cidade cidade = (Cidade) o;

        return getId() != null ? getId().equals(cidade.getId()) : cidade.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Cidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", uf='" + uf + '\'' +
                ", taxa=" + taxa +
                '}';
    }
}