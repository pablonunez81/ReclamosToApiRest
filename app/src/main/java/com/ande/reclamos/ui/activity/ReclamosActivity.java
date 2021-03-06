package com.ande.reclamos.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ande.reclamos.EdicionReclamoActivity;
import com.ande.reclamos.R;
import com.ande.reclamos.Reclamos;
import com.ande.reclamos.ReclamosBD;
import com.ande.reclamos.io.MyApiAdapter;
import com.ande.reclamos.model.Movil;
import com.ande.reclamos.model.Reclamo;
import com.ande.reclamos.ui.adapter.ReclamosAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReclamosActivity extends AppCompatActivity implements LocationListener, Callback {

    private Button bAcercaDe;
    private Button bSalir;
    public static ReclamosBD reclamos;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LocationManager manejador;
    private Location mejorLocaliz;              //almacenará la mejor localización actual
    private static final int SOLICITUD_PERMISO_LOCALIZACION = 0;
    private static final long DOS_MINUTOS = 2 * 60 * 1000;
    private static int movilId = -1;
    private static String movilNumero = null;
    public static ReclamosAdapter mAdapter;
    private static final int CODE_VISTA_RECLAMO = 1;

    /**
     * El objeto reclamos será accedido desde cualquier clase,
     * Static, una instancia única de reclamos.
     * Para acceder: MainActivity.reclamos
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamos);
        Bundle extras = getIntent().getExtras();
        movilId = extras.getInt("movil_id", -1);
        movilNumero = extras.getString("movil_numero", null);
        //Crea la base de datos, tabla e inserta valores definidos
        reclamos = new ReclamosBD(this);

        //Buscamos recycler por su identificador
        recyclerView = findViewById(R.id.recycler_view);

        //Creamos un nuevo layoutManager del tipo LinearLayoutManager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ReclamosAdapter(this);
        recyclerView.setAdapter(mAdapter);
        //Trae los reclamos filtrados por movil logeado
        fetchReclamosMovil();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Se añade un escuchador para poder selecionar objetos de la lista de RecyclerView.
         * el metodo getChildAdapterPosition nos indicará la posición
         * de una vista dentro del adaptador.
         */
        mAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReclamosActivity.this, VistaReclamoActivity.class);
                i.putExtra("id", (long) recyclerView.getChildAdapterPosition(v));
                startActivityForResult(i, CODE_VISTA_RECLAMO);
            }
        });

        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
        ultimaLocalizacion();
    }

    /**
     * Antes de obtener una localizacion, hay que verificar que tenemos permiso para hacerlo.
     * Hay que agregar en el Manifest ACCESS_FINE_LOCATION
     */
    private void ultimaLocalizacion() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                actualizaMejorLocaliz(manejador.getLastKnownLocation(
                        LocationManager.GPS_PROVIDER
                ));
            }
            if (manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                actualizaMejorLocaliz(manejador.getLastKnownLocation(
                        LocationManager.NETWORK_PROVIDER
                ));
            }

        } else {
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION,
                    "Sin el permiso localización no puedo mostrar la distancia" +
                            "a los lugares de reclamos.", SOLICITUD_PERMISO_LOCALIZACION, this);
        }
    }

    /**
     * Actualizar una mejor localización:
     * Si todavia no ha sido inicializada,
     * o la nueva localización tiene una precisión aceptable (al menos la mitad que la actual),
     * o la diferencia de tiempo es superior a dos minutos.
     * Finalmente, actualiza la variable mejorLocaliz,
     * y guaramos la posición en Reclamos.posicionActual
     *
     * @param localiz
     */
    private void actualizaMejorLocaliz(Location localiz) {
        if (localiz != null && (mejorLocaliz == null
                || localiz.getAccuracy() < 2 * mejorLocaliz.getAccuracy()
                || localiz.getTime() - mejorLocaliz.getTime() > DOS_MINUTOS)) {
            Log.d(Reclamos.TAG, "Nueva mejor localización");
            mejorLocaliz = localiz;
            Reclamos.posicionActual.setLatitud(localiz.getLatitude());
            Reclamos.posicionActual.setLongitud(localiz.getLongitude());

            Call<Movil> call = MyApiAdapter.getApiService().actualizarUbicacion(
                    String.valueOf(movilId),
                    String.valueOf(movilNumero),
                    String.valueOf(localiz.getLatitude()),
                    String.valueOf(localiz.getLongitude())
            );
            call.enqueue(this);
        }
    }

    /**
     * Para poder solicitar permiso desde diferentes puntos de la aplicación.
     * Cuando el usuario decida si da o no permiso, se llamará al método onRequestPermissionsResult.
     * El código permitirá identificar diferentes solicitudes
     *
     * @param permiso       El permiso a solicitar
     * @param justificacion La justificación de porqué se necesita el permiso
     * @param requestCode   Código de solicitud
     * @param actividad     La actividad que recogerá la respuesta
     */
    public static void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity actividad) {
        //antes de mostrar la explicacion, se verifica si se interesa mostrar esa información.
        //Si el usuario ha indicado que no da permiso y ah marcado la casilla NO VOLVER A PREGUNTAR, no se insistirá
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)) {
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //lo más importante: mostrar el cuadro de dialogo con la pregunta al usuario de conceder permiso o no.
                            //Luego de esto, se llamará al onRequestPermissionsResult, donde se procesa la respuesta
                            ActivityCompat.requestPermissions(actividad, new String[]{permiso}, requestCode);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad, new String[]{permiso}, requestCode);
        }
        //se llamará automaticamente al método onRequestPermissionsResult
    }

    /**
     * Será llamado cuando el usuario conteste a la solicitud de permiso.
     * Si nos dan el permiso, ya podemos obtener la ultima localizacion conocida
     * y activar los proveedores.
     * Por último, refrescamos el adaptador para que se muestren las distancias
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si nos han dado el permiso ya podemos obtener la última localización conocida
                // y activar los proveedores.
                ultimaLocalizacion();
                ;
                activarProveedores();
                //adaptador.notifyDataSetChanged();                                             //TODO. Esto debo agregar nuevamente
                //Refrescamos el adaptador del RecyclerView para que se muestren las distancias
                //SelectorFragment.adaptador.notifyDataSetChanged();
            }
        }
    }

    /**
     * Cuando la actividad pasa a activa, se llamará al método ActivarProveedores()
     * , si es que tenemos permiso, se programarán actualizaciones de los proveedores disponibles.
     */
    @Override
    protected void onResume() {
        super.onResume();
        activarProveedores();
    }

    /**
     * Si tenemos permiso a ACCESS_FINE_LOCATION,
     * Se programarán actualizaciones de los proveedores disponibles,
     * en periodos de 20 y 10 segundos, con una distancia de 5 o 10 metros
     * dependiendo del proveedor
     */
    private void activarProveedores() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manejador.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        20 * 1000, 5, this);
            }
            if (manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                manejador.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        10 * 1000, 10, this);
            }
        } else {
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION,
                    "Sin el permiso localización no puedo mostrar la distancia" +
                            " a los lugares de reclamos.", SOLICITUD_PERMISO_LOCALIZACION, this);
        }
    }

    /**
     * Cuando la actividad deje de estar activa,
     * Verificamos si tenemos permiso, y
     * eliminamos las actualizaciones
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            manejador.removeUpdates(this);
        }
    }

    public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }

    /**
     * Muestra el menu de opciones
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Acciones posibles entre las Opciones de menu seleccionadas
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }
        if (id == R.id.menu_mapa) {
            Intent intent = new Intent(this, MapaActivity.class);
            startActivity(intent);
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Cuando Se cambie la posición, activamos nuevos proveedores
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.d(Reclamos.TAG, "Nueva Localización: " + location);
        actualizaMejorLocaliz(location);
        //adaptador.notifyDataSetChanged();                             //TODO. Esto debo agregar nuevamente
    }

    /**
     * Cuando Se cambie el estado, activamos nuevos proveedores
     *
     * @param provider
     * @param status
     * @param extras
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(Reclamos.TAG, "Cambia estado: " + provider);
        activarProveedores();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(Reclamos.TAG, "Se habilita: " + provider);
        activarProveedores();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(Reclamos.TAG, "Se deshabilita: " + provider);
        activarProveedores();
    }

    /**
     * Este no se para que aún
     *
     * @param hasCapture
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //-----------------Retrofit Mejor localización----
    @Override
    public void onResponse(Call call, Response response) {
        if (response.isSuccessful()) {
            final Movil movil = (Movil) response.body();
            Log.d("onResponse MejorLocaliz", "numero de movil  => " + movil.getNumero());
            //Toast.makeText(this, "Enviando ubicación", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Falla al enviar ubicación al servidor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
//-----------------Fin de Retrofit - Mejor localización----

    private void fetchReclamosMovil() {
        Call<ArrayList<Reclamo>> call = MyApiAdapter.getApiService().reclamosMovil(String.valueOf(movilId));
        call.enqueue(new ReclamosCallback());
    }

    private class ReclamosCallback implements Callback<ArrayList<Reclamo>> {
        @Override
        public void onResponse(Call<ArrayList<Reclamo>> call, Response<ArrayList<Reclamo>> response) {
            if (response.isSuccessful()) {
                populateReclamosMovil(response);
            } else {
                Toast.makeText(getBaseContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Reclamo>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Enviar los datos al adaptador
     *
     * @param response<ArrayList<Reclamo>>
     */
    private void populateReclamosMovil(Response<ArrayList<Reclamo>> response) {
        ArrayList<Reclamo> reclamos = response.body();
        mAdapter.setDataSet(reclamos);
        Log.d("PopulateReclamosMovil", "Cantidad de reclamos: " + reclamos.size());
    }

    /**
     * Al verificar de que la actividad viene del activity VistaReclamoActivity, verifica por el código
     * CODE_VISTA_RECLAMO, y ejecutará el método de relleno fetchReclamosMovil a fin de presentar
     * la lista nueva de reclamos para el móvil asignado
     * de la activi
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_VISTA_RECLAMO) {
            fetchReclamosMovil();
        }
    }

}