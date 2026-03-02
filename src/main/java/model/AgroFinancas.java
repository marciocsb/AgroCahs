package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AgroFinancas implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Lavoura> lavouras = this.carregar();
    private static final String ARQUIVO = "agrofinancas.dat";

    public void adicionarLavoura(Lavoura l) {
        this.lavouras.add(l);
        this.salvar();
    }

    public boolean removerLavoura(String nome) {
        boolean removido = this.lavouras.removeIf((l) -> l.getNome().equalsIgnoreCase(nome));
        if (removido) {
            this.salvar();
        }

        return removido;
    }

    public List<Lavoura> getLavouras() {
        return this.lavouras;
    }

    public void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("agrofinancas.dat"))) {
            oos.writeObject(this.lavouras);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Lavoura> carregar() {
        File f = new File("agrofinancas.dat");
        if (!f.exists()) {
            return new ArrayList();
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                return (List)ois.readObject();
            } catch (ClassNotFoundException | IOException e) {
                ((Exception)e).printStackTrace();
                return new ArrayList();
            }
        }
    }
}