package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lavoura implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private double area;
    private List<Despesa> despesas;

    public Lavoura(String nome, double area) {
        this.nome = nome;
        this.area = area;
        this.despesas = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public double getArea() { return area; }
    public List<Despesa> getDespesas() { return despesas; }

    public void registrarDespesa(Despesa despesa) { despesas.add(despesa); }

    public double calcularTotal() {
        return despesas.stream().mapToDouble(Despesa::getValor).sum();
    }
}