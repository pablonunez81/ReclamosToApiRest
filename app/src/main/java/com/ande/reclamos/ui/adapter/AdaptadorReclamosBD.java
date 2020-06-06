package com.ande.reclamos.ui.adapter;

import android.content.Context;
import android.database.Cursor;

import com.ande.reclamos.Reclamo;
import com.ande.reclamos.Reclamos;
import com.ande.reclamos.ReclamosBD;
import com.ande.reclamos.ui.adapter.AdaptadorReclamos;

/**
 * Extendemos de adaptdor de reclamos, para aprovechar parte de su codigo, y
 * le indicamos las diferencias.
 * Tenemos un nuevo parámetro CURSOR, que es el resultado de la consulta a la base de datos.
 * Cursor será donde buscar los elementos a listar en el recyclerView.
 * LLamamos al constructor, disponibilizamos los getter y setter del cursor desde afuera de la clase.
 */
public class AdaptadorReclamosBD extends AdaptadorReclamos {
    protected Cursor cursor;

    /**
     * Constructor. Se inicializa el conjunto de datos a mostrar, y variables globales.
     * El objeto inflador permitirá crear una vista a partir de su XML
     *
     * @param contexto
     * @param reclamos
     */
    public AdaptadorReclamosBD(Context contexto, Reclamos reclamos, Cursor cursor) {
        super(contexto, reclamos);
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    /**
     * podremos acceder a un reclamo indicando su posicion en el cursor.
     * @param posicion
     * @return
     */
    public Reclamo reclamoPosicion(int posicion){
        cursor.moveToPosition(posicion);
        return ReclamosBD.extraeReclamo(cursor);
    }

    /**
     * Busca el id del reclamo según su posición en el cursor
     * @param posicion
     * @return
     */
    public int idPosicion(int posicion){
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    /**
     * Con esto, realizará consultas al cursor constantemente y no a la base de datos,
     * logrando así ser más eficiente.
     * @param holder
     * @param posicion
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Reclamo reclamo = reclamoPosicion(posicion);
        personalizarVista(holder, reclamo);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
