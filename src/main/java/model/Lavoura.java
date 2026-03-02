package model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Lavoura implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private double area;
    private List<Despesa> despesas;

    public Lavoura(String nome, double area) {
        this.nome = nome;
        this.area = area;
        this.despesas = new ArrayList();
    }

    public String getNome() {
        return this.nome;
    }

    public void registrarDespesa(Despesa despesa) {
        this.despesas.add(despesa);
    }

    public List<Despesa> getDespesas() {
        return this.despesas;
    }

    public String gerarRelatorio() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        StringBuilder sb = new StringBuilder();
        sb.append("===== RELATÓRIO DA LAVOURA =====\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Área: ").append(this.area).append(" hectares\n\n");
        double total = (double)0.0F;
        sb.append("Despesas registradas:\n");

        for(Despesa d : this.despesas) {
            sb.append("- ").append(d.getTipo()).append(" - ").append(nf.format(d.getValor())).append("\n");
            total += d.getValor();
        }

        sb.append("\nTOTAL GASTO: ").append(nf.format(total)).append("\n");
        return sb.toString();
    }
}