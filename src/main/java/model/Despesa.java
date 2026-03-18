package model;

import java.io.Serializable;

public class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;
    private TipoDespesa tipo;
    private double valor;

    public Despesa(TipoDespesa tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public TipoDespesa getTipo() { return tipo; }
    public double getValor() { return valor; }
}