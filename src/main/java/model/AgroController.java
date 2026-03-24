package model;

import java.util.List;

public class AgroController {

    private AgroFinancas sistema;

    public AgroController() { sistema = new AgroFinancas(); }

    public AgroFinancas getSistema() { return sistema; }

    public void adicionarUsuario(Usuario u) { sistema.adicionarUsuario(u); }
    public void removerUsuario(Usuario u) { sistema.removerUsuario(u); }
    public boolean cpfExiste(String cpf) { return sistema.cpfExiste(cpf); }

    public void adicionarLavoura(Usuario u, Lavoura l) { sistema.adicionarLavoura(u, l); }
    public boolean removerLavoura(Usuario u, String nome) { return sistema.removerLavoura(u, nome); }
    public List<Lavoura> getLavouras(Usuario u) { return sistema.getLavouras(u); }
    public Lavoura pesquisarLavoura(Usuario u, String nomeLavoura) { return sistema.pesquisarLavoura(u, nomeLavoura); }

    public void registrarDespesa(Usuario u, String nomeLavoura, TipoDespesa tipo, double valor) {
        sistema.registrarDespesa(u, nomeLavoura, new Despesa(tipo, valor));
    }

    public String gerarRelatorio(Usuario u, String nomeLavoura) {
        return sistema.gerarRelatorio(u, nomeLavoura);
    }

    public List<Usuario> getUsuarios() { return sistema.getUsuarios(); }
}
