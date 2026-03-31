package model;

import javax.swing.*;
import java.util.List;

public class AgroController implements ISistemaFinancas {

    private AgroFinancas sistema;

    public AgroController() { sistema = new AgroFinancas(); }

    public AgroFinancas getSistema() { return sistema; }

    @Override
    public void adicionarUsuario(Usuario u) { sistema.adicionarUsuario(u); }

    @Override
    public boolean removerUsuario(Usuario u) {
        int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente remover o usuário " + u.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) return sistema.removerUsuario(u);
        return false;
    }

    @Override
    public boolean cpfExiste(String cpf) { return sistema.cpfExiste(cpf); }

    @Override
    public void adicionarLavoura(Usuario u, Lavoura l) throws Exception {
        if (l.getArea() <= 0) throw new Exception("Área inválida!");
        sistema.adicionarLavoura(u, l);
    }

    @Override
    public boolean removerLavoura(Usuario u, String nome) {
        int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a lavoura " + nome + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) return sistema.removerLavoura(u, nome);
        return false;
    }

    @Override
    public Lavoura pesquisarLavoura(Usuario u, String nomeLavoura) { return sistema.pesquisarLavoura(u, nomeLavoura); }

    // Correção: agora aceita TipoDespesa e valor direto
    @Override
    public void registrarDespesa(Usuario u, String nomeLavoura, TipoDespesa tipo, double valor) {
        Despesa d = new Despesa(tipo, valor);
        sistema.registrarDespesa(u, nomeLavoura, d);
    }

    @Override
    public List<Usuario> getUsuarios() { return sistema.getUsuarios(); }

    @Override
    public List<Lavoura> getLavouras(Usuario u) { return sistema.getLavouras(u); }

    @Override
    public String gerarRelatorio(Usuario u, String nomeLavoura) { return sistema.gerarRelatorio(u, nomeLavoura); }

    @Override
    public void salvarDados() throws Exception { sistema.salvarTodos(); }

    @Override
    public void recuperarDados() throws Exception { sistema.recuperarTodos(); }
}
