package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaPrincipal extends JFrame {

    private AgroController controller;
    private JTextArea textArea;

    private JTextField usuarioNomeField, usuarioCpfField;
    private JComboBox<Usuario> usuarioBox;
    private Usuario usuarioAtual;

    private JTextField nomeField, areaField;
    private JComboBox<String> lavouraBox;

    private JComboBox<TipoDespesa> tipoDespesaBox;
    private JTextField valorField;

    public TelaPrincipal(AgroController controller) {
        this.controller = controller;

        setTitle("Sistema AgroFinanças");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Correção: define o PainelFundo como fundo da tela
        setContentPane(new PainelFundo());

        setLayout(new BorderLayout(10, 10));
        add(criarPainelUsuario(), BorderLayout.NORTH);
        add(criarPainelCentro(), BorderLayout.CENTER);
        add(criarPainelRelatorio(), BorderLayout.SOUTH);

        atualizarComboUsuarios();

        pack();
    }

    private JPanel criarPainelUsuario() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Usuário"));
        panel.setOpaque(false); // deixa transparente

        usuarioNomeField = new JTextField();
        usuarioCpfField = new JTextField();
        usuarioBox = new JComboBox<>();

        usuarioBox.addActionListener(e -> {
            usuarioAtual = (Usuario) usuarioBox.getSelectedItem();
            atualizarComboLavouras();
        });

        JButton definirBtn = new JButton("Definir Usuário");
        definirBtn.addActionListener(e -> {
            String nome = usuarioNomeField.getText().trim();
            String cpf = usuarioCpfField.getText().trim().replaceAll("\\D", "");

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha nome e CPF!");
                return;
            }

            if (controller.cpfExiste(cpf)) {
                JOptionPane.showMessageDialog(this, "CPF já cadastrado!");
                return;
            }

            usuarioAtual = new Usuario(nome, cpf);
            controller.adicionarUsuario(usuarioAtual);
            atualizarComboUsuarios();

            usuarioNomeField.setText("");
            usuarioCpfField.setText("");
        });

        JButton removerBtn = new JButton("Remover Usuário");
        removerBtn.addActionListener(e -> {
            if (usuarioAtual == null) return;
            controller.removerUsuario(usuarioAtual);
            atualizarComboUsuarios();
        });

        panel.add(new JLabel("Nome:"));
        panel.add(usuarioNomeField);
        panel.add(new JLabel("CPF:"));
        panel.add(usuarioCpfField);
        panel.add(definirBtn);
        panel.add(removerBtn);
        panel.add(new JLabel("Usuários:"));
        panel.add(usuarioBox);

        return panel;
    }

    private JPanel criarPainelCentro() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setOpaque(false); // deixa transparente
        panel.add(criarPainelLavoura());
        panel.add(criarPainelDespesa());
        return panel;
    }

    private JPanel criarPainelLavoura() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Lavoura"));
        panel.setOpaque(false); // deixa transparente

        nomeField = new JTextField();
        areaField = new JTextField();
        lavouraBox = new JComboBox<>();

        JButton addBtn = new JButton("Adicionar");
        JButton remBtn = new JButton("Remover");

        addBtn.addActionListener(e -> {
            if (usuarioAtual == null) return;
            try {
                controller.adicionarLavoura(usuarioAtual,
                        new Lavoura(nomeField.getText(), Double.parseDouble(areaField.getText())));
                atualizarComboLavouras();
                nomeField.setText("");
                areaField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro na área!");
            }
        });

        remBtn.addActionListener(e -> {
            if (usuarioAtual == null) return;
            String nome = (String) lavouraBox.getSelectedItem();
            if (nome != null) {
                controller.removerLavoura(usuarioAtual, nome);
                atualizarComboLavouras();
            }
        });

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Área:"));
        panel.add(areaField);
        panel.add(addBtn);
        panel.add(remBtn);
        panel.add(new JLabel("Lavouras:"));
        panel.add(lavouraBox);

        return panel;
    }

    private JPanel criarPainelDespesa() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Despesa"));
        panel.setOpaque(false); // deixa transparente

        tipoDespesaBox = new JComboBox<>(TipoDespesa.values());
        valorField = new JTextField();

        JButton btn = new JButton("Registrar");
        btn.addActionListener(e -> {
            if (usuarioAtual == null) return;
            try {
                String lavoura = (String) lavouraBox.getSelectedItem();
                TipoDespesa tipo = (TipoDespesa) tipoDespesaBox.getSelectedItem();
                double valor = Double.parseDouble(valorField.getText());

                controller.registrarDespesa(usuarioAtual, lavoura, tipo, valor);

                NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                textArea.append(tipo + ": " + nf.format(valor) + "\n");

                valorField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro no valor!");
            }
        });

        panel.add(new JLabel("Lavoura:"));
        panel.add(lavouraBox);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoDespesaBox);
        panel.add(new JLabel("Valor:"));
        panel.add(valorField);
        panel.add(new JLabel());
        panel.add(btn);

        return panel;
    }

    private JPanel criarPainelRelatorio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Relatório"));
        panel.setOpaque(false); // deixa transparente

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);

        JButton btn = new JButton("Gerar Relatório");
        btn.addActionListener(e -> {
            if (usuarioAtual == null) return;
            String lavoura = (String) lavouraBox.getSelectedItem();
            if (lavoura != null) {
                textArea.setText(controller.gerarRelatorio(usuarioAtual, lavoura));
            }
        });

        panel.add(btn, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return panel;
    }

    private void atualizarComboUsuarios() {
        usuarioBox.removeAllItems();
        for (Usuario u : controller.getSistema().getUsuarios()) {
            usuarioBox.addItem(u);
        }
        if (usuarioBox.getItemCount() > 0) {
            usuarioBox.setSelectedIndex(0);
            usuarioAtual = (Usuario) usuarioBox.getSelectedItem();
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
