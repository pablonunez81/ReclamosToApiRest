package com.ande.reclamos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.ande.reclamos.ui.activity.ReclamosActivity;

/**
 * Actividad de edición de un reclamo.
 * Campos que pueden ser modificados en la vistaReclamo, no deberían poder ser modificados aquí
 */
public class EdicionReclamoActivity extends AppCompatActivity {

    private long id;
    private Reclamo reclamo;
    private EditText nombreCliente;
    private EditText telefono;
    private EditText direccion;
    private EditText referencia;
    private Spinner tipoReclamo;
    private Spinner tipoAveria;
    private long _id;

    /**
     * Esta actividad será llamada podrá ser llamada desde dos formas diferentes.
     * id indica que el reclamo será extraido de la posición del adaptador. EDICION
     * _id indica que el reclamo será extraido de la base de datos, utilizando su identificador. NUEVO
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_reclamo);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);
        _id = extras.getLong("_id", -1);
        if(_id != -1){
            reclamo = ReclamosActivity.reclamos.elemento((int) _id);
        } else {
            //reclamo = MainActivity.reclamos.elemento((int) id);
            /**
             * Ahora se accede al cursor que tiene la copia de la consulta SQL
             */
            //reclamo = ReclamosActivity.adaptador.reclamoPosicion((int) id);                   //TODO. Esto debo agregar nuevamente
        }
        nombreCliente = (EditText) findViewById(R.id.nombreCliente);
        nombreCliente.setText(reclamo.getNombreCliente());

        tipoReclamo = findViewById(R.id.tipoReclamo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TipoReclamo.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoReclamo.setAdapter(adaptador);
        tipoReclamo.setSelection(reclamo.getTipoReclamo().ordinal());

        tipoAveria = findViewById(R.id.tipoAveria);
        ArrayAdapter<String> adaptadorAveria = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TipoAveria.getNombres());
        adaptadorAveria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoAveria.setAdapter(adaptadorAveria);
        tipoAveria.setSelection(reclamo.getTipoAveria().ordinal());

        telefono = (EditText) findViewById(R.id.telefono);
        telefono.setText(String.valueOf(reclamo.getTelefono()));

        direccion = (EditText) findViewById(R.id.direccion);
        direccion.setText(reclamo.getDireccion());

        referencia = (EditText) findViewById(R.id.referencia);
        referencia.setText(reclamo.getReferencia());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edicion_reclamo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_cancelar:
                //si el registro es nuevo, el cursor aún no está actualizado, lo eliminamos de la base de datos
                if (_id!=-1) {
                    ReclamosActivity.reclamos.borrar((int) _id);
                }
                finish();
                return true;
            case R.id.accion_guardar:
                accionGuardarReclamo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Método que actualiza los campos de un reclamo.
     * Es llamado desde el menu de opcion de Guardar reclamo cuando está en modo edicion.
     *
     * La variable id corresponde a un indicador de posición dentro de la lista,
     * Para utilizar correctamente el método actualiza() de reclamosBD,
     * debemos obtener el id correspondiente a la primera columna de la tabla.
     * Este cambio lo realiza el método idPosicion() de adaptador.
     *
     * Actualizamos nuestro cursor consultando a la base de datos.
     * Finalmente,estamos notificando al adaptador que ha sido cambiado el elemento de una determinada posición.
     * Esto provocará una llamada al método onBindViewHolder() donde se refrescará la vista
     */
    private void accionGuardarReclamo(){
        reclamo.setTelefono(Integer.parseInt(telefono.getText().toString()));
        reclamo.setNombreCliente(nombreCliente.getText().toString());
        reclamo.setTipoReclamo(TipoReclamo.values()[tipoReclamo.getSelectedItemPosition()]);
        reclamo.setTipoAveria(TipoAveria.values()[tipoAveria.getSelectedItemPosition()]);
        reclamo.setDireccion(direccion.getText().toString());
        reclamo.setReferencia(referencia.getText().toString());
        //MainActivity.reclamos.actualiza((int) id, reclamo);
        if (_id == -1) {                //Edicion
            //_id = ReclamosActivity.adaptador.idPosicion((int) id);                //TODO. Esto debo agregar nuevamente
        }
        ReclamosActivity.reclamos.actualiza((int) _id, reclamo);
        //ReclamosActivity.adaptador.setCursor(ReclamosActivity.reclamos.extraeCursor());           //TODO. Esto debo agregar nuevamente
        if(id != -1){                  //Edicion
            //ReclamosActivity.adaptador.notifyItemChanged((int) id);                               //TODO. Esto debo agregar nuevamente
        }else {                         //Nuevo
            //ReclamosActivity.adaptador.notifyDataSetChanged();                                    //TODO. Esto debo agregar nuevamente
        }
        finish();
    }
}
