package com.ande.reclamos;

/**
 * Lista de tipos de reclamos.
 * El entero se utiliza para indicar el recurso gráfico en android con un icono representativo del tipo
 */
public enum TipoReclamo {
    FE("Falta de energía", R.drawable.icon_casa),
    AL("Alumbrado Público", R.drawable.icon_foco);

    private final String texto;
    private final int recurso;


    TipoReclamo(String texto, int recurso) {
        this.texto = texto;
        this.recurso = recurso;
    }

    /**
     * Metodo utilizado en la actividad EdicionReclamo, en el ArrayAdapter para visualizar los tipos de reclamos.
     * Se utiliza cuando se utiliza el objeto Spinner en las vistas
     * @return
     */
    public static String[] getNombres() {
        String[] resultado = new String[TipoReclamo.values().length];
        for (TipoReclamo tipo : TipoReclamo.values()){
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
