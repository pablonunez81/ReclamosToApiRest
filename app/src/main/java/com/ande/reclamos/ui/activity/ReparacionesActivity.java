package com.ande.reclamos.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ande.reclamos.R;
import com.ande.reclamos.io.MyApiAdapter;
import com.ande.reclamos.io.response.ReclamosDetalleResponse;
import com.ande.reclamos.model.Averia;
import com.ande.reclamos.model.ReclamosDetalle;
import com.ande.reclamos.ui.adapter.ReparacionesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReparacionesActivity extends AppCompatActivity {

    private Spinner spinnerAveria;
    private EditText metObservacion;
    private RecyclerView mRecyclerView;
    private static ReparacionesAdapter mAdapter;
    private static long reclamo_id;
    private ArrayList<Averia> averias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reparaciones);

        Bundle extras = getIntent().getExtras();
        reclamo_id = extras.getLong("id", -1);

        mRecyclerView = findViewById(R.id.recyclerViewReparaciones);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ReparacionesAdapter();
        mRecyclerView.setAdapter(mAdapter);

        fetchAverias();
        fetchAveriasXReclamo();
        spinnerAveria = (Spinner) findViewById(R.id.spinnerAveria);
        metObservacion = findViewById(R.id.etObservacion);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAddAveria);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnCreate", "Onclick. Spinner text: " + averias.get(spinnerAveria.getSelectedItemPosition()).getAveria());
                Log.d("OnCreate", "Onclick. Observa: " + metObservacion.getText());
                addReclamoDetalle();
            }
        });

    }

    private void addReclamoDetalle() {
        Call<ReclamosDetalleResponse> call = MyApiAdapter.getApiService().addReclamoDetalle(metObservacion.getText().toString(), averias.get(spinnerAveria.getSelectedItemPosition()).getId(), (int) reclamo_id);
        call.enqueue(new AddReclamoDetalleCallBack());
    }

    //---------------------------------------------LISTA DE AVERIAS---------------------------------
    private void fetchAverias() {
        Call<ArrayList<Averia>> call = MyApiAdapter.getApiService().averias();
        call.enqueue(new AveriasCallBack());
    }

    private class AveriasCallBack implements retrofit2.Callback<ArrayList<Averia>> {
        @Override
        public void onResponse(Call<ArrayList<Averia>> call, Response<ArrayList<Averia>> response) {
            if(response.isSuccessful()) {
                populateAverias(response);
            }else {
                Toast.makeText(getBaseContext(), "Error en el formato de respuesta de la lista de Averias", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(Call<ArrayList<Averia>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.d("AveriasCallBack", "onFailure: "+t.getLocalizedMessage());
        }
    }

    private void populateAverias(Response<ArrayList<Averia>> response) {
        averias = response.body();
        List<String> list = new ArrayList<String>();
        for (Averia a: averias) {
            list.add(a.getAveria());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAveria.setAdapter(spinnerArrayAdapter);
    }
    //---------------------------------------------AVERIAS POR RECLAMO------------------------------
    public static void fetchAveriasXReclamo() {
        Call<ArrayList<ReclamosDetalle>> call = MyApiAdapter.getApiService().averiasXReclamo(String.valueOf(reclamo_id));
        call.enqueue(new AveriasXReclamoCallBack());
    }

    private static class AveriasXReclamoCallBack implements Callback<ArrayList<ReclamosDetalle>> {
        @Override
        public void onResponse(Call<ArrayList<ReclamosDetalle>> call, Response<ArrayList<ReclamosDetalle>> response) {
            if(response.isSuccessful()) {
                populateAveriasXReclamo(response);
            }else {
                //Toast.makeText(getBaseContext(), "Error en el formato de respuesta de la lista de Averias por reclamo", Toast.LENGTH_SHORT).show();
            }
        }

        private void populateAveriasXReclamo(Response<ArrayList<ReclamosDetalle>> response) {
            ArrayList<ReclamosDetalle> reclamosDetalle = response.body();
            mAdapter.setDataSet(reclamosDetalle);
        }

        @Override
        public void onFailure(Call<ArrayList<ReclamosDetalle>> call, Throwable t) {
            //Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.d("AveriasXReclamoCallBack", "onFailure: "+t.getLocalizedMessage());
        }
    }
    //---------------------------------------Add ReclamoDetalle-------------------------------------
    private class AddReclamoDetalleCallBack implements Callback<ReclamosDetalleResponse> {

        @Override
        public void onResponse(Call<ReclamosDetalleResponse> call, Response<ReclamosDetalleResponse> response) {
            if(response.isSuccessful()) {
                Log.d("AddReclamoDetalleCallBa", "OnResponse: IsSuccessful");
                fetchAveriasXReclamo();
            }
        }

        @Override
        public void onFailure(Call<ReclamosDetalleResponse> call, Throwable t) {
            Log.d("AddReclamoDetalleCallBa", "onFailure: "+t.getLocalizedMessage());
        }
    }
}