package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.AgroController;
import model.Lavoura;
import model.TipoDespesa;

public class TelaPrincipal extends JFrame {
    private AgroController controller;
    private JTextArea textArea;
    private JTextField nomeField;
    private JTextField areaField;
    private JTextField valorField;
    private JComboBox<String> lavouraBox;
    private JComboBox<TipoDespesa> tipoDespesaBox;

    public TelaPrincipal(AgroController controller) {
        this.controller = controller;
        this.setTitle("Sistema AgroFinanças");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        PainelFundo painelFundo = new PainelFundo();
        JPanel painelLavoura = this.criarPainelLavoura();
        JPanel painelDespesa = this.criarPainelDespesa();
        JPanel painelRelatorio = this.criarPainelRelatorio();
        painelLavoura.setOpaque(false);
        painelDespesa.setOpaque(false);
        painelRelatorio.setOpaque(false);
        painelFundo.add(painelLavoura, "North");
        painelFundo.add(painelDespesa, "Center");
        painelFundo.add(painelRelatorio, "South");
        this.setContentPane(painelFundo);
    }

    private JPanel criarPainelLavoura() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Gerenciar Lavoura"));
        this.nomeField = new JTextField(8);
        this.areaField = new JTextField(5);
        JButton adicionarBtn = new JButton("Adicionar");
        JButton removerBtn = new JButton("Remover");
        adicionarBtn.setPreferredSize(new Dimension(120, 30));
        removerBtn.setPreferredSize(new Dimension(120, 30));
        adicionarBtn.addActionListener((e) -> {
            try {
                String nome = this.nomeField.getText();
                double area = Double.parseDouble(this.areaField.getText());
                this.controller.adicionarLavoura(nome, area);
                this.atualizarCombo();
                this.nomeField.setText("");
                this.areaField.setText("");
            } catch (NumberFormatException var5) {
                JOptionPane.showMessageDialog(this, "Digite apenas números na área!");
            }

        });
        removerBtn.addActionListener((e) -> {
            String nome = (String)this.lavouraBox.getSelectedItem();
            if (nome != null) {
                int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover a lavoura \"" + nome + "\"?", "Confirmação", 0);
                if (opcao == 0) {
                    this.controller.removerLavoura(nome);
                    this.atualizarCombo();
                }
            }

        });
        panel.add(new JLabel("Nome:"));
        panel.add(this.nomeField);
        panel.add(new JLabel("Área:"));
        panel.add(this.areaField);
        panel.add(adicionarBtn);
        panel.add(removerBtn);
        return panel;
    }

    private JPanel criarPainelDespesa() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Despesa"));
        this.lavouraBox = new JComboBox();
        this.tipoDespesaBox = new JComboBox(TipoDespesa.values());
        this.valorField = new JTextField(5);
        JButton despesaBtn = new JButton("Registrar");
        despesaBtn.setPreferredSize(new Dimension(120, 30));
        despesaBtn.addActionListener((e) -> {
            int index = this.lavouraBox.getSelectedIndex();
            if (index != -1) {
                try {
                    String nome = (String)this.lavouraBox.getSelectedItem();
                    TipoDespesa tipo = (TipoDespesa)this.tipoDespesaBox.getSelectedItem();
                    double valor = Double.parseDouble(this.valorField.getText());
                    this.controller.registrarDespesa(nome, tipo, valor);
                    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    JTextArea var10000 = this.textArea;
                    String var10001 = String.valueOf(tipo);
                    var10000.append("Despesa registrada: " + var10001 + " - " + nf.format(valor) + "\n");
                    this.valorField.setText("");
                } catch (NumberFormatException var8) {
                    JOptionPane.showMessageDialog(this, "Digite apenas números!");
                }
            }

        });
        panel.add(new JLabel("Lavoura:"));
        panel.add(this.lavouraBox);
        panel.add(new JLabel("Tipo:"));
        panel.add(this.tipoDespesaBox);
        panel.add(new JLabel("Valor:"));
        panel.add(this.valorField);
        panel.add(despesaBtn);
        return panel;
    }

    private JPanel criarPainelRelatorio() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Relatório"));
        this.textArea = new JTextArea(10, 35);
        this.textArea.setEditable(false);
        JButton relatorioBtn = new JButton("Gerar Relatório");
        relatorioBtn.setPreferredSize(new Dimension(150, 35));
        relatorioBtn.addActionListener((e) -> {
            String nome = (String)this.lavouraBox.getSelectedItem();
            if (nome != null) {
                this.textArea.setText(this.controller.gerarRelatorio(nome));
            }

        });
        panel.add(relatorioBtn);
        panel.add(new JScrollPane(this.textArea));
        return panel;
    }

    private void atualizarCombo() {
        this.lavouraBox.removeAllItems();

        for(Lavoura l : this.controller.getSistema().getLavouras()) {
            this.lavouraBox.addItem(l.getNome());
        }

    }
}