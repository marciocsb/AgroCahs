package model;

import java.util.List;

public interface ISistemaFinancas {

    void adicionarUsuario(Usuario u);
    boolean removerUsuario(Usuario u);
    boolean cpfExiste(String cpf);

    void adicionarLavoura(Usuario u, Lavoura l);
    boolean removerLavoura(Usuario u, String nome);

    Lavoura pesquisarLavoura(Usuario u, String nomeLavoura);
    void registrarDespesa(Usuario u, String nomeLavoura, Despesa d);

    List<Usuario> getUsuarios();
    List<Lavoura> getLavouras(Usuario u);
    String gerarRelatorio(Usuario u, String nomeLavoura);
}