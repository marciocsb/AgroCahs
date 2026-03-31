package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PainelFundo extends JPanel {
    private Image imagem;

    public PainelFundo() {
        URL imgURL = getClass().getResource("/imagem/fundo.png");
        if (imgURL != null) {
            imagem = new ImageIcon(imgURL).getImage();
            System.out.println("Imagem carregada: " + imgURL); // debug
        } else {
            System.out.println("Imagem não encontrada!");
        }
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagem != null) {
            g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
