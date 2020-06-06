package com.ande.reclamos;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        //System.out.println("Hola Mundo");
        /*Reclamo reclamo = new Reclamo("000001", 398513456, "Carlos Duarte", 7000000,
                "Tomas Romero Pereira", "Frente a la iglesia Católica", TipoReclamo.FE, TipoAveria.BT,
                Municipio.EN, Barrio.SR, 0,0);
        System.out.println("Reclamo: "+ reclamo);*/
    }

    /**
     * Verifará si están cargados los reclamos de ejemplos
     */
    @Test
    public void reclamosVector() {
        Reclamos reclamos = new ReclamosVector();
        /*for (int i=0; i<reclamos.tamanyo(); i++) {
            System.out.println(reclamos.elemento(i).toString());
        }*/
        assertEquals(2, reclamos.tamanyo());
    }
}