package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AgroFinancas implements ISistemaFinancas, Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ARQUIVO = "agrofinancas.dat";

    private List<Lavoura> lavouras;

    public AgroFinancas() {
        this.lavouras = carregar();
    }

    @Override
    public void adicionarLavoura(Lavoura l) {
        lavouras.add(l);
        salvar();
    }

    @Override
    public boolean removerLavoura(String nome) {
        boolean removido = lavouras.removeIf(
                l -> l.getNome().equalsIgnoreCase(nome)
        );

        if (removido) {
            salvar();
        }

        return removido;
    }

    @Override
    public List<Lavoura> getLavouras() {
        return lavouras;
    }

    @Override
    public void salvar() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {

            oos.writeObject(lavouras);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Lavoura> carregar() {

        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(arquivo))) {

            return (List<Lavoura>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}