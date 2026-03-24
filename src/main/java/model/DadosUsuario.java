package model;

import java.io.Serializable;
import java.util.List;

public class DadosUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private List<Lavoura> lavouras;

    public DadosUsuario(Usuario usuario, List<Lavoura> lavouras) {
        this.usuario = usuario;
        this.lavouras = lavouras;
    }

    public Usuario getUsuario() { return usuario; }
    public List<Lavoura> getLavouras() { return lavouras; }
}