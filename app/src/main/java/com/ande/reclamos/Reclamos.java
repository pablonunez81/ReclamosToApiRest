package com.ande.reclamos;

/**
 * Esta interfaz permitirá almacenar una lista de objetos del tipo Reclamo.
 * Utiliza a la hora de implementar un ABM
 */
public interface Reclamos {

    final static String TAG = "MisReclamos";
    static GeoPunto posicionActual = new GeoPunto(0,0);

    /**
     * Devuelve el elemento dado su id
     * @param id
     * @return
     */
    Reclamo elemento(int id); //Devuelve el elemento dado su id

    /**
     * Añade el elemento indicado
     * @param reclamo
     */
    void anyade(Reclamo reclamo); //Añade el elemento indicado

    /**
     * Añade un elemento en blanco y devuelve su id
     * @return
     */
    int nuevo(); //Añade un elemento en blanco y devuelve su id

    /**
     * Elimina el elemento con el id indicado
     * @param id
     */
    void borrar(int id); //Elimina el elemento con el id indicado

    /**
     * Devuelve el número de elementos
     * @return
     */
    int tamanyo(); //Devuelve el número de elementos

    /**
     * Reemplaza un elemento
     * @param id
     * @param reclamo
     */
    void actualiza(int id, Reclamo reclamo); //Reemplaza un elemento



}
