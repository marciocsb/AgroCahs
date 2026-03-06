package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PainelFundo extends JPanel {
    private Image imagem;

    public PainelFundo() {
        URL imgURL = this.getClass().getResource("/imagem/fundo.png");
        if (imgURL != null) {
            this.imagem = (new ImageIcon(imgURL)).getImage();
        } else {
            System.err.println("Erro: imagem não encontrada!");
        }

        this.setLayout(new BorderLayout());

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if  ( imagem != null) {
            g.drawImage(this.imagem, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}