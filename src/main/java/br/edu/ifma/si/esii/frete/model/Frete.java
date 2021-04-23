package br.edu.ifma.si.esii.frete.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Frete implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    private Cidade cidade;
    @NotNull
    @ManyToOne
    private Cliente cliente;
    @NotNull
    @Length(max = 20)
    private String descricao;
    @NotNull
    @Positive
    private double peso;
    @NotNull
    @Positive
    private BigDecimal valor;

    public Frete() {
    }

    public Frete(Cidade cidade, Cliente cliente, String descricao, double peso, BigDecimal valor) {
        this.cidade = cidade;
        this.cliente = cliente;
        this.descricao = descricao;
        this.peso = peso;
        this.valor = valor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frete)) return false;

        Frete frete = (Frete) o;

        return getId() != null ? getId().equals(frete.getId()) : frete.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Frete{" +
                "id=" + id +
                ", cidade=" + cidade +
                ", cliente=" + cliente +
                ", descricao='" + descricao + '\'' +
                ", peso=" + peso +
                ", valor=" + valor +
                '}';
    }
}