package com.ande.reclamos;

public enum TipoAveria {
    TE("Tensión Elevada", 0),
    BT("Baja Tensión", 0);

    private final String texto;
    private final int recurso;

    TipoAveria(String texto, int recurso ) {
        this.texto = texto;
        this.recurso = recurso;
    }

    public static String[] getNombres() {
        String[] resultado = new String[TipoAveria.values().length];
        for (TipoAveria tipo : TipoAveria.values()){
            resultado[tipo.ordinal()] = tipo.texto;
        }
        return resultado;
    }

    public String getTexto() {
        return texto;
    }

    public int getRecurso() {
        return recurso;
    }
}
