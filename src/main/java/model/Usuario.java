package model;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;

    public Usuario(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return cpf.equals(usuario.cpf);
    }

    @Override
    public int hashCode() { return Objects.hash(cpf); }

    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }
}