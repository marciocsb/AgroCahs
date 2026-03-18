package model;

import java.io.*;

public class GravadorDeDados {

    public void salvarDados(Object dados, String arquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(dados);
        }
    }

    public Object recuperarDados(String arquivo) throws IOException, ClassNotFoundException {
        File file = new File(arquivo);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return ois.readObject();
        }
    }
}