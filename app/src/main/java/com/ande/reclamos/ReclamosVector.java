package com.ande.reclamos;

import java.util.ArrayList;
import java.util.List;

public class ReclamosVector implements Reclamos {

    protected List<Reclamo> vectorReclamos;// = ejemploReclamos();

    /**
     * Constructor
     */
    public ReclamosVector() {
        vectorReclamos = ejemploReclamos();
    }

    @Override
    public Reclamo elemento(int id) {
        return vectorReclamos.get(id);
    }

    @Override
    public void anyade(Reclamo reclamo) {
        vectorReclamos.add(reclamo);
    }

    @Override
    public int nuevo() {
        Reclamo reclamo = new Reclamo();
        vectorReclamos.add(reclamo);
        return vectorReclamos.size()-1;
    }

    @Override
    public void borrar(int id) {
        vectorReclamos.remove(id);
    }

    @Override
    public int tamanyo() {
        return vectorReclamos.size();
    }

    @Override
    public void actualiza(int id, Reclamo reclamo) {
        vectorReclamos.set(id, reclamo);
    }

    /**
     * Función utilizada por el constructor para cargar datos de ejemplos
     * @return
     */
    public static ArrayList<Reclamo> ejemploReclamos() {
        ArrayList<Reclamo> reclamos = new ArrayList<Reclamo>();
        reclamos.add(
                new Reclamo("000001", 398513456, "Carlos Duarte", 7000000,
                "Honorio Gonzalez y Tomas Romero Pereira", "Frente a la iglesia Católica", TipoReclamo.FE, TipoAveria.BT,
                Municipio.EN, Barrio.SR,  -55.860403,-27.330679)
        );
        reclamos.add(
                new Reclamo("000002", 985729851, "Ester Figueredo", 8000000,
                        "Villarica y antequera", "Frente al colegio CREE A", TipoReclamo.AL, TipoAveria.BT,
                        Municipio.EN, Barrio.SR,  -55.871250, -27.330972)
        );
        return reclamos;
    }
}
