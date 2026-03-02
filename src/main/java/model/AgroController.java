package model;

public class AgroController {
    private AgroFinancas sistema = new AgroFinancas();

    public AgroFinancas getSistema() {
        return this.sistema;
    }

    public void adicionarLavoura(String nome, double area) {
        this.sistema.adicionarLavoura(new Lavoura(nome, area));
    }

    public void removerLavoura(String nome) {
        this.sistema.removerLavoura(nome);
    }

    public void registrarDespesa(String nomeLavoura, TipoDespesa tipo, double valor) {
        for(Lavoura l : this.sistema.getLavouras()) {
            if (l.getNome().equalsIgnoreCase(nomeLavoura)) {
                l.registrarDespesa(new Despesa(tipo, valor));
                this.sistema.salvar();
                break;
            }
        }

    }

    public String gerarRelatorio(String nomeLavoura) {
        for(Lavoura l : this.sistema.getLavouras()) {
            if (l.getNome().equalsIgnoreCase(nomeLavoura)) {
                return l.gerarRelatorio();
            }
        }

        return "Lavoura não encontrada.";
    }
}