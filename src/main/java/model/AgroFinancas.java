package model;

import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;
import java.util.Locale;

public class AgroFinancas implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PASTA_USUARIOS = "usuarios";

    private Map<Usuario, List<Lavoura>> lavourasPorUsuario;
    private GravadorDeDados gravador;

    public AgroFinancas() {
        gravador = new GravadorDeDados();
        lavourasPorUsuario = new HashMap<>();

        File pasta = new File(PASTA_USUARIOS);
        pasta.mkdirs(); // garante que a pasta exista

        // Carrega todos os arquivos de usuários existentes
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".dat"));
        if (arquivos != null) {
            for (File f : arquivos) {
                try {
                    @SuppressWarnings("unchecked")
                    List<Lavoura> lavouras = (List<Lavoura>) gravador.recuperarDados(f.getAbsolutePath());
                    String cpf = f.getName().replace(".dat", "");
                    // Cria usuário com nome genérico; você pode atualizar depois se quiser armazenar nomes reais
                    Usuario u = new Usuario("Usuário " + cpf, cpf);
                    lavourasPorUsuario.put(u, lavouras);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ==================== USUÁRIO ==================== //
    public void adicionarUsuario(Usuario usuario) {
        lavourasPorUsuario.putIfAbsent(usuario, new ArrayList<>());
        salvar(usuario);
    }

    public boolean removerUsuario(Usuario usuario) {
        if (lavourasPorUsuario.containsKey(usuario)) {
            lavourasPorUsuario.remove(usuario);
            // Remove arquivo do usuário
            File arquivo = new File(PASTA_USUARIOS + "/" + usuario.getCpf() + ".dat");
            if (arquivo.exists()) arquivo.delete();
            return true;
        }
        return false;
    }

    public boolean cpfExiste(String cpf) {
        return lavourasPorUsuario.keySet().stream()
                .anyMatch(u -> u.getCpf().replaceAll("\\D", "").equals(cpf.replaceAll("\\D", "")));
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(lavourasPorUsuario.keySet());
    }

    // ==================== LAVOURA ==================== //
    public void adicionarLavoura(Usuario usuario, Lavoura l) {
        lavourasPorUsuario.putIfAbsent(usuario, new ArrayList<>());
        lavourasPorUsuario.get(usuario).add(l);
        salvar(usuario);
    }

    public boolean removerLavoura(Usuario usuario, String nomeLavoura) {
        List<Lavoura> lavouras = lavourasPorUsuario.get(usuario);
        if (lavouras != null) {
            boolean removido = lavouras.removeIf(l -> l.getNome().equalsIgnoreCase(nomeLavoura));
            if (removido) salvar(usuario);
            return removido;
        }
        return false;
    }

    public List<Lavoura> getLavouras(Usuario usuario) {
        return lavourasPorUsuario.getOrDefault(usuario, new ArrayList<>());
    }

    public Lavoura pesquisarLavoura(Usuario usuario, String nomeLavoura) {
        List<Lavoura> lavouras = lavourasPorUsuario.get(usuario);
        if (lavouras != null) {
            return lavouras.stream()
                    .filter(l -> l.getNome().equalsIgnoreCase(nomeLavoura))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    // ==================== DESPESA ==================== //
    public void registrarDespesa(Usuario usuario, String nomeLavoura, Despesa d) {
        Lavoura l = pesquisarLavoura(usuario, nomeLavoura);
        if (l != null) {
            l.registrarDespesa(d);
            salvar(usuario);
        }
    }

    // ==================== RELATÓRIO ==================== //
    public String gerarRelatorio(Usuario usuario, String nomeLavoura) {
        Lavoura l = pesquisarLavoura(usuario, nomeLavoura);
        if (l == null) return "Lavoura não encontrada.";

        String cpfFormatado = usuario.getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        StringBuilder sb = new StringBuilder();
        sb.append("Usuário: ").append(usuario.getNome())
                .append("  CPF: ").append(cpfFormatado).append("\n");
        sb.append("Lavoura: ").append(l.getNome()).append("\n");
        sb.append("Área: ").append(l.getArea()).append(" hectares\n");
        sb.append("Despesas:\n");
        for (Despesa d : l.getDespesas()) {
            sb.append("- ").append(d.getTipo()).append(" - ").append(nf.format(d.getValor())).append("\n");
        }
        sb.append("TOTAL: ").append(nf.format(l.calcularTotal()));

        return sb.toString();
    }

    // ==================== MÉTODO DE SALVAMENTO ==================== //
    private void salvar(Usuario usuario) {
        try {
            File pasta = new File(PASTA_USUARIOS);
            pasta.mkdirs();
            gravador.salvarDados(lavourasPorUsuario.get(usuario),
                    PASTA_USUARIOS + "/" + usuario.getCpf() + ".dat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}