package com.ande.reclamos.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.ande.reclamos.GeoPunto;
import com.ande.reclamos.R;
import com.ande.reclamos.model.Reclamo;
import com.ande.reclamos.ui.activity.ReclamosActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    /**
     * El centro del mapa lo situamos (moveCamera) en el primer reclamo
     * que encuentre si es que encuentra por lo menos un reclamo.
     * Realizamos un bucle donde añadimos un marcador por cada reclamo.
     * Para utilizar los iconos de la aplicación de reclamos,
     * leemos como Drawables el recurso, lo convertimos a Bitmap, y
     * los escalamos dividiendo su anchura y altura por 7.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }
        // TODO: si el primer reclamo no tiene posición, ver como resolverlo
        /*if (MainActivity.reclamos.tamanyo() > 0 ) {
            GeoPunto p = MainActivity.reclamos.elemento(0).getPosicion();*/
        /*if (ReclamosActivity.adaptador.getItemCount() > 0 ) {
            GeoPunto p = ReclamosActivity.adaptador.reclamoPosicion(0).getPosicion();*/
        if (ReclamosActivity.mAdapter.getItemCount() > 0 ) {
            GeoPunto p = ReclamosActivity.mAdapter.reclamoPosicion(0).getPosicion();
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(p.getLatitud(), p.getLongitud()), 12
            ));
        }

        /*for (int n=0; n<MainActivity.reclamos.tamanyo(); n++){
            Reclamo reclamo = MainActivity.reclamos.elemento(n);*/
        for (int n = 0; n< ReclamosActivity.mAdapter.getItemCount(); n++){
            final Reclamo reclamo = ReclamosActivity.mAdapter.reclamoPosicion(n);
            final GeoPunto p = reclamo.getPosicion();

            if(p != null && p.getLatitud() != 0){
                //esto era para objetos drawable guardados como recursos en el telefono
                /*BitmapDrawable iconoDrawable = (BitmapDrawable) getResources().getDrawable(reclamo.getTipoReclamo().getRecurso());
                Bitmap iGrande = iconoDrawable.getBitmap();
                Bitmap icono = Bitmap.createScaledBitmap(
                        iGrande,
                        iGrande.getWidth() / 7, iGrande.getHeight() / 7, false
                );
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(p.getLatitud(), p.getLongitud()))
                        .title(reclamo.getNombreCliente()).snippet(reclamo.getDireccion())
                        .icon(BitmapDescriptorFactory.fromBitmap(icono))
                );*/

                //Traemos los iconos del server
                Picasso.get().load(reclamo.getTiporeclamoid().getImageTipoReclamoUrl()).into(new Target() {
                   @Override
                   public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                       Bitmap icono = Bitmap.createScaledBitmap(
                               bitmap,
                               bitmap.getWidth() / 7, bitmap.getHeight() / 7, false);
                       Marker marker = mapa.addMarker(new MarkerOptions()
                               .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                               .icon(BitmapDescriptorFactory.fromBitmap(icono))
                               .title(reclamo.getNombre()).snippet(reclamo.getDireccion())
                               .position(new LatLng(p.getLatitud(), p.getLongitud())));
                   }

                   @Override
                   public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                   }

                   /*@Override
                   public void onBitmapFailed(Drawable drawable) {
                   }*/

                   @Override
                   public void onPrepareLoad(Drawable drawable) {
                   }
                });
            }
        }
        mapa.setOnInfoWindowClickListener(this);
    }


    /**
     * Se llamará a este método cuando se pulse sobre cualquier ventana de información.
     * El objeto marker se utiliza para averiguar el marcador pulsado.
     * Falta depurar esta parte, ya que si hay varios reclamos con el mismo nombre de cliente,
     * enviará al primero que encuentre, pudiendo ser incorrecto esto.
     * Quizas agregar el número de identificación de reclamo, ya que será único
     * TODO: mejorar busqueda de vistaReclamoActivity
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {

        /*for (int id = 0; id< ReclamosActivity.adaptador.getItemCount(); id++){                    //TODO. Esto debo agregar nuevamente

            if (ReclamosActivity.adaptador.reclamoPosicion(id). getNombreCliente()
                    .equals(marker.getTitle())){
                Intent intent = new Intent(this, VistaReclamoActivity.class);
                intent.putExtra("id", (long) id);
                startActivity(intent);
                break;
            }
        }*/
    }
}
