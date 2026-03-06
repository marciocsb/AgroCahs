package model;

import java.util.List;

public interface ISistemaFinancas {
    void adicionarLavoura(Lavoura l);
    boolean removerLavoura(String nome);
    List<Lavoura> getLavouras();
    void salvar();
}