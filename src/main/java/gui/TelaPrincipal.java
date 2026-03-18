package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TelaPrincipal extends JFrame {

    private AgroController controller;
    private JTextArea textArea;

    // Campos de usuário
    private JTextField usuarioNomeField, usuarioCpfField;
    private JComboBox<Usuario> usuarioBox;
    private Usuario usuarioAtual;

    // Campos de lavoura
    private JTextField nomeField, areaField;
    private JComboBox<String> lavouraBox;

    // Campos de despesa
    private JComboBox<TipoDespesa> tipoDespesaBox;
    private JTextField valorField;

    public TelaPrincipal(AgroController controller) {
        this.controller = controller;
        setTitle("Sistema AgroFinanças");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PainelFundo painelFundo = new PainelFundo();
        painelFundo.add(criarPainelUsuario(), BorderLayout.NORTH);
        painelFundo.add(criarPainelLavoura(), BorderLayout.WEST);
        painelFundo.add(criarPainelDespesa(), BorderLayout.CENTER);
        painelFundo.add(criarPainelRelatorio(), BorderLayout.SOUTH);

        setContentPane(painelFundo);
        atualizarComboUsuarios();
    }

    // ==================== PAINEL USUÁRIO ==================== //
    private JPanel criarPainelUsuario() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Usuário"));

        usuarioNomeField = new JTextField(10);
        usuarioCpfField = new JTextField(10);
        usuarioBox = new JComboBox<>();

        // Atualiza usuarioAtual quando muda a seleção
        usuarioBox.addActionListener(e -> {
            Usuario selecionado = (Usuario) usuarioBox.getSelectedItem();
            if (selecionado != null) {
                usuarioAtual = selecionado;
                atualizarComboLavouras();
            }
        });

        JButton definirUsuarioBtn = new JButton("Definir Usuário");
        definirUsuarioBtn.setPreferredSize(new Dimension(160, 30));
        definirUsuarioBtn.addActionListener(e -> {
            String nome = usuarioNomeField.getText().trim();
            String cpfLimpo = usuarioCpfField.getText().trim().replaceAll("\\D", "");

            if (nome.isEmpty() || cpfLimpo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha nome e CPF!");
                return;
            }

            if (controller.cpfExiste(cpfLimpo)) {
                JOptionPane.showMessageDialog(this, "CPF já cadastrado!");
                return;
            }

            usuarioAtual = new Usuario(nome, cpfLimpo);
            controller.adicionarUsuario(usuarioAtual);
            atualizarComboUsuarios();
            JOptionPane.showMessageDialog(this, "Usuário definido: " + nome);

            usuarioNomeField.setText("");
            usuarioCpfField.setText("");
        });

        JButton removerUsuarioBtn = new JButton("Remover Usuário");
        removerUsuarioBtn.setPreferredSize(new Dimension(160, 30));
        removerUsuarioBtn.addActionListener(e -> {
            if (usuarioAtual == null) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário!");
                return;
            }
            controller.removerUsuario(usuarioAtual);
            usuarioAtual = null;
            atualizarComboUsuarios();
            atualizarComboLavouras();
        });

        panel.add(new JLabel("Nome:"));
        panel.add(usuarioNomeField);
        panel.add(new JLabel("CPF:"));
        panel.add(usuarioCpfField);
        panel.add(definirUsuarioBtn);
        panel.add(removerUsuarioBtn);
        panel.add(new JLabel("Usuários:"));
        panel.add(usuarioBox);
        return panel;
    }

    // ==================== PAINEL LAVOURA ==================== //
    private JPanel criarPainelLavoura() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Gerenciar Lavoura"));

        nomeField = new JTextField(8);
        areaField = new JTextField(5);
        lavouraBox = new JComboBox<>();

        JButton adicionarBtn = new JButton("Adicionar");
        JButton removerBtn = new JButton("Remover");
        adicionarBtn.setPreferredSize(new Dimension(115, 30));
        removerBtn.setPreferredSize(new Dimension(120, 30));

        adicionarBtn.addActionListener(e -> {
            if (usuarioAtual == null) {
                JOptionPane.showMessageDialog(this, "Defina um usuário!");
                return;
            }
            try {
                controller.adicionarLavoura(usuarioAtual, new Lavoura(nomeField.getText().trim(),
                        Double.parseDouble(areaField.getText())));
                atualizarComboLavouras();
                nomeField.setText("");
                areaField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Área inválida!");
            }
        });

        removerBtn.addActionListener(e -> {
            if (usuarioAtual == null) {
                JOptionPane.showMessageDialog(this, "Defina um usuário!");
                return;
            }
            String nomeLavoura = (String) lavouraBox.getSelectedItem();
            if (nomeLavoura != null) {
                controller.removerLavoura(usuarioAtual, nomeLavoura);
                atualizarComboLavouras();
            }
        });

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Área:"));
        panel.add(areaField);
        panel.add(adicionarBtn);
        panel.add(removerBtn);
        panel.add(new JLabel("Lavouras:"));
        panel.add(lavouraBox);
        return panel;
    }

    // ==================== PAINEL DESPESA ==================== //
    private JPanel criarPainelDespesa() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Despesa"));

        tipoDespesaBox = new JComboBox<>(TipoDespesa.values());
        valorField = new JTextField(10);

        JButton despesaBtn = new JButton("Registrar");
        despesaBtn.setPreferredSize(new Dimension(120, 40));
        despesaBtn.addActionListener(e -> {
            if (usuarioAtual == null) {
                JOptionPane.showMessageDialog(this, "Defina um usuário!");
                return;
            }
            String nomeLavoura = (String) lavouraBox.getSelectedItem();
            if (nomeLavoura != null) {
                try {
                    TipoDespesa tipo = (TipoDespesa) tipoDespesaBox.getSelectedItem();
                    double valor = Double.parseDouble(valorField.getText());
                    controller.registrarDespesa(usuarioAtual, nomeLavoura, tipo, valor);
                    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    textArea.append("Despesa registrada: " + tipo + " - " + nf.format(valor) + "\n");
                    valorField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valor inválido!");
                }
            }
        });

        panel.add(new JLabel("Lavoura:"));
        panel.add(lavouraBox);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoDespesaBox);
        panel.add(new JLabel("Valor:"));
        panel.add(valorField);
        panel.add(despesaBtn);
        return panel;
    }

    // ==================== PAINEL RELATÓRIO ==================== //
    private JPanel criarPainelRelatorio() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Relatório"));

        textArea = new JTextArea(10, 35);
        textArea.setEditable(false);

        JButton relatorioBtn = new JButton("Gerar Relatório");
        relatorioBtn.setPreferredSize(new Dimension(140, 30));
        relatorioBtn.addActionListener(e -> {
            String nomeLavoura = (String) lavouraBox.getSelectedItem();
            if (usuarioAtual != null && nomeLavoura != null) {
                textArea.setText(controller.gerarRelatorio(usuarioAtual, nomeLavoura));
            }
        });

        panel.add(relatorioBtn);
        panel.add(new JScrollPane(textArea));
        return panel;
    }

    // ==================== MÉTODOS AUXILIARES ==================== //
    private void atualizarComboUsuarios() {
        usuarioBox.removeAllItems();
        for (Usuario u : controller.getSistema().getUsuarios()) {
            usuarioBox.addItem(u);
        }
        // Seleciona primeiro usuário, se houver
        if (usuarioBox.getItemCount() > 0){
            usuarioBox.setSelectedIndex(0);
            usuarioAtual = (Usuario) usuarioBox.getSelectedItem();
        } else {
            usuarioAtual = null;
        }
        atualizarComboLavouras();
    }

    private void atualizarComboLavouras() {
        lavouraBox.removeAllItems();
        if (usuarioAtual != null) {
            for (Lavoura l : controller.getSistema().getLavouras(usuarioAtual)) {
                lavouraBox.addItem(l.getNome());
            }
        }
    }
}