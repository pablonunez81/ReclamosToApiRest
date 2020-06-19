package com.ande.reclamos.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
//import android.content.Intent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ande.reclamos.EdicionReclamoActivity;
import com.ande.reclamos.R;
import com.ande.reclamos.model.Reclamo;
import com.ande.reclamos.ui.activity.ReclamosActivity;
import com.squareup.picasso.Picasso;

/**
 * Actividad que visualiza un reclamo
 */
public class VistaReclamoActivity extends AppCompatActivity {

    private long id;
    private Reclamo reclamo;
    final static int RESULTADO_EDITAR = 1;

    /**
     * El método será ejecutado cuando se cree la actividad de Visualización de un reclamo.
     * Se asocia a un layout e inicializa los valores.
     * Se averigua el ID del reclamo a mostrar, mediante el EXTRA.
     * Obtenemos el Reclamo.
     * Desde ese momento, el reclamo puede ser accedido desde cualquier método de la clase
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_reclamo);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);
        actualizarVistaReclamo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_reclamo, menu);
        return true;
    }

    /**
     * Agregamos a la vista botones de opciones como:
     * Accion Compartir: permitirá enviar como texto plano los dato del reclamo
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_compartir:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        reclamo.getNombre() + " - " +
                                reclamo.getTiporeclamoid().getTipo() + " - " +
                                reclamo.getTipoaveria().getTipoaveria() + " - " +
                                reclamo.getTelefono() + " - " +
                                reclamo.getDireccion() + " - " +
                                reclamo.getBarrio().getNombre() + " - " +
                                reclamo.getCiudadid().getNombre()
                );
                startActivity(intent);
                return true;
            case R.id.accion_llegar:
                verMapa(null);
                return true;
            case R.id.accion_editar:
                //lanzarEdicionReclamo(null, id);
                return true;
            case R.id.accion_borrar:
                //Busca el id del reclamo según su posición en el cursor
                /*int _id = ReclamosActivity.adaptador.idPosicion((int) id);                        //TODO. Esto debo agregar nuevamente
                borrarReclamo((int) _id);*/
                //MainActivity.reclamos.borrar((int) id);
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Este metodo será llamado dandole click en una parte de la vista del reclamo,
     * y también desde el menu de opciones de la vistaReclamo: ACCION LLEGAR.
     * Obtenemos la latitud y longitud.
     * Si alguna de ellas es distinto a 0, lo consideramos introducido.
     * Si no, creamos la URI en basandonos en la dirección del reclamo:
     * reclamo.getDireccion()
     *
     * @param view
     */
    public void verMapa(View view) {
        Uri uri;
        double lat = reclamo.getPosicion().getLatitud();
        double lon = reclamo.getPosicion().getLongitud();
        if (lat != 0 || lon != 0) {
            uri = Uri.parse("geo:" + lat + "," + lon);
        } else {
            uri = Uri.parse("geo:0,0?q=" + reclamo.getDireccion() + " " + reclamo.getBarrio().getNombre() + " " + reclamo.getCiudadid().getNombre());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * Intención que permite realizar una llamada telefónica
     * @param view
     */
    public void llamadaTelefono(View view){
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + reclamo.getTelefono())));
    }

    public void borrarReclamo(final int id){
        //MainActivity.reclamos.borrar((int) id);

        /*new AlertDialog.Builder(this)                                                             //TODO. Esto debo agregar nuevamente
                .setTitle("Borrado de un reclamo")
                .setMessage("¿Estas seguro que quieres eliminar este reclamo?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReclamosActivity.reclamos.borrar((int) id);
                        //Luego de borrar de la base de datos, actualizamos el cursor y el gráfico.
                        ReclamosActivity.adaptador.setCursor(
                                ReclamosActivity.reclamos.extraeCursor());
                        ReclamosActivity.adaptador.notifyDataSetChanged();

                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();*/
    }

    /**
     * Método que lanza la actividad de Edición de reclamo,
     * pero que a su vuelta se llama en forma automática al método onActivityResult
     * @param view
     * @param id
     */
    public void lanzarEdicionReclamo(View view, final long id){
        Intent i = new Intent(this,
                EdicionReclamoActivity.class);
        i.putExtra("id", id);
        //startActivity(i);
        startActivityForResult(i, RESULTADO_EDITAR);
    }

    /**
     * Fuerza a volcar los datos a la vista.
     * Es utilizado especialmente por el caso de que se edite un reclamo, y se
     * vuelva a la vistaReclamo.
     * En este caso, la actividad volverá a consultar datos actualizados para mostrar
     */
    public void actualizarVistaReclamo(){
        reclamo = ReclamosActivity.mAdapter.reclamoPosicion((int) id);

        TextView nombreCliente = (TextView) findViewById(R.id.nombreCliente);
        nombreCliente.setText(reclamo.getNombre());

        ImageView logo_tipoReclamo = (ImageView) findViewById(R.id.logo_tipoReclamo);
        Picasso.get().
                load(reclamo.getTiporeclamoid().getImageTipoReclamoUrl()).
                into(logo_tipoReclamo);

        TextView tipoReclamo = (TextView) findViewById(R.id.tipoReclamo);
        tipoReclamo.setText(reclamo.getTiporeclamoid().getTipo());

        TextView tipoAveria = (TextView) findViewById(R.id.tipoAveria);
        tipoAveria.setText(reclamo.getTipoaveria().getTipoaveria());

        if(reclamo.getTelefono() == 0) {
            findViewById(R.id.barra_telefono).setVisibility(View.GONE);
        } else {
            TextView telefono = (TextView) findViewById(R.id.telefono);
            telefono.setText(String.valueOf(reclamo.getTelefono()));
        }

        TextView direccion = (TextView) findViewById(R.id.direccion);
        direccion.setText(reclamo.getDireccion());

        TextView barrio = (TextView) findViewById(R.id.barrio);
        barrio.setText(reclamo.getBarrio().getNombre() + ", " + reclamo.getCiudadid().getNombre());

        TextView nis = (TextView) findViewById(R.id.nis);
        nis.setText(reclamo.getSuministroid().getNis());
    }

    /**
     * método que es llamado en forma automática al terminar de editar un reclamo.
     * Al finalizar la actividad lanzarEdicionReclamo
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTADO_EDITAR) {
            actualizarVistaReclamo();
            findViewById(R.id.scrollViewVistaReclamo).invalidate();
        }
    }
}
