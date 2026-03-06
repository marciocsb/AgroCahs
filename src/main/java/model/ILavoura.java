package model;

import java.util.List;

public interface ILavoura {
    String getNome();
    void registrarDespesa(Despesa despesa);
    List<Despesa> getDespesas();
    String gerarRelatorio();
}