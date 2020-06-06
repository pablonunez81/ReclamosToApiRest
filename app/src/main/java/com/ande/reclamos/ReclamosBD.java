package com.ande.reclamos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReclamosBD extends SQLiteOpenHelper implements Reclamos {

    Context contexto;

    /**
     * Creamos la base de datos RECLAMOS si es que no existe
     * @param contexto
     */
    public ReclamosBD(Context contexto) {
        super(contexto, "reclamos5", null, 1);
        this.contexto = contexto;
    }

    /**
     * actualizar todos los atributos de reclamo con los valores de la fila apuntada por el cursor
     * @param cursor
     * @return
     */
    public static Reclamo extraeReclamo(Cursor cursor) {
        Reclamo reclamo = new Reclamo();
        reclamo.setNumeroReclamo(cursor.getString(1));
        reclamo.setTelefono(cursor.getInt(2));
        reclamo.setNombreCliente(cursor.getString(3));
        reclamo.setNis(cursor.getInt(4));
        reclamo.setTipoReclamo(TipoReclamo.values()[cursor.getInt(5)]);
        reclamo.setTipoAveria(TipoAveria.values()[cursor.getInt(6)]);
        reclamo.setDireccion(cursor.getString(7));
        reclamo.setReferencia(cursor.getString(8));
        reclamo.setPosicion(new GeoPunto(cursor.getDouble(9), cursor.getDouble(10)));
        //reclamo.setFechaRecepcion(cursor.getLong(11));
        /*
        *       "numeroReclamo TEXT, " +
                "telefono INTEGER," +
                "nombreCliente TEXT," +
                "nis INTEGER," +
                "tipoReclamo INTEGER," +
                "tipoAveria INTEGER," +
                "direccion TEXT," +
                "referencia TEXT," +
                "longitud REAL, " +
                "latitud REAL, " +
                "fechaRecepcion BIGINT)"*/
        return reclamo;
    }

    /**
     * Consultamos la base de datos, para actualizar nuestro cursor
     * @return
     */
    public Cursor extraeCursor() {
        String consulta = "SELECT * From reclamos5";
        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int i =1;
    }

    /**
     * Creamos la tabla reclamos e insertamos valores
     * @param bd
     */
    @Override public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE reclamos5 ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "numeroReclamo TEXT, " +
                "telefono INTEGER, " +
                "nombreCliente TEXT, " +
                "nis INTEGER, " +
                "tipoReclamo INTEGER, " +
                "tipoAveria INTEGER, " +
                "direccion TEXT, " +
                "referencia TEXT, " +
                "longitud REAL, " +
                "latitud REAL, " +               // , " +
                "fechaRecepcion BIGINT)");

        bd.execSQL("INSERT INTO reclamos5 VALUES (null, '1000', 0985123456, 'Profesor' , 555000, 1, 1, 'Honorio Gonzalez y Tomas Romero Pereira', 'Frente a la iglesia Católica',-55.860403,-27.330679, " + System.currentTimeMillis() + ")");
        bd.execSQL("INSERT INTO reclamos5 VALUES (null, '1001', 0985456123, 'Berlin', 555111, 1, 1, 'Villarica y antequera', 'Frente al colegio CREE A',-55.871250, -27.330972, " + System.currentTimeMillis() + ")");
    }

    //-------inicio de implements Reclamos

    /**
     * Buscamos el reclamo según su id
     * @param id
     * @return
     */
    @Override
    public Reclamo elemento(int id) {
        Reclamo reclamo = null;
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM reclamos5 WHERE _id = " + id,
                null);
        if (cursor.moveToNext()) {
            reclamo = extraeReclamo(cursor);
        }
        cursor.close();
        bd.close();
        return reclamo;
    }

    @Override
    public void anyade(Reclamo reclamo) {

    }

    /**
     * Se crea un reclamo utilizando un constructor con fecha, tipo de reclamo, tipo de avería y posición
     * Si hubiese algún problema, se devolverá -1.
     * Creamos un registro en la bd con estos datos.
     * consultamos por la fecha de recepción el id
     *
     * @return
     */
    @Override
    public int nuevo() {
        int _id = -1;
        Reclamo reclamo = new Reclamo();
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("INSERT INTO reclamos5 (longitud, latitud, tipoReclamo, tipoAveria,  fechaRecepcion) "+
                "VALUES ( " + reclamo.getPosicion().getLongitud()+","+
                reclamo.getPosicion().getLatitud()+", "+
                reclamo.getTipoReclamo().ordinal()+", "+
                reclamo.getTipoAveria().ordinal()+", "+
                reclamo.getFechaRecepcion()+")");
        Cursor c = bd.rawQuery("SELECT _id FROM reclamos5 WHERE fechaRecepcion = " +
                reclamo.getFechaRecepcion(), null);
        if (c.moveToNext()){
            _id = c.getInt(0);
        }
        c.close();
        bd.close();
        return _id;
    }

    /**
     * Eliminar registros de la base de datos
     * @param id
     */
    @Override
    public void borrar(int id) {
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("DELETE FROM reclamos5 WHERE _id = " + id );
        bd.close();
    }

    @Override
    public int tamanyo() {
        return 0;
    }

    /**
     * Actualizamos la base de datos
     * @param id
     * @param reclamo
     */
    @Override
    public void actualiza(int id, Reclamo reclamo) {
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("UPDATE reclamos5 SET numeroReclamo = '" + reclamo.getNumeroReclamo() +
                "', telefono = " + reclamo.getTelefono() +
                ", nombreCliente = '" + reclamo.getNombreCliente() +
                "' , nis = " + reclamo.getNis() +
                ", tipoReclamo = " + reclamo.getTipoReclamo().ordinal() +
                ", tipoAveria = " + reclamo.getTipoAveria().ordinal() +
                ", direccion = '" + reclamo.getDireccion() +
                "', referencia = '" + reclamo.getReferencia() +
                "', longitud = " + reclamo.getPosicion().getLongitud() +
                ", latitud = " + reclamo.getPosicion().getLatitud() +
                " WHERE _id = " + id);
        bd.close();
    }
    //-------fin de implements Reclamos
}
