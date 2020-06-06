package com.ande.reclamos;

public enum Barrio {

    SR("San Roque", 0),
    SB("San Blas", 0);

    private final String texto;
    private final int recurso;

    Barrio(String texto, int recurso) {
        this.texto = texto;
        this.recurso = recurso;
    }

    public String getTexto() {
        return texto;
    }

    public int getRecurso() {
        return recurso;
    }
}
