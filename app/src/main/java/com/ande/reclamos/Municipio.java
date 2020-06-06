package com.ande.reclamos;

public enum Municipio {

    EN("Encarnación", 0),
    CA("Cambyreta", 0);

    private final String texto;
    private final int recurso;

    public String getTexto() {
        return texto;
    }

    public int getRecurso() {
        return recurso;
    }

    Municipio(String texto, int recurso) {
        this.texto = texto;
        this.recurso = recurso;
    }
}
