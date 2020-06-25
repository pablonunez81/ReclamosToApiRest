package com.ande.reclamos.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ande.reclamos.R;
import com.ande.reclamos.io.MyApiAdapter;
import com.ande.reclamos.model.Averia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReparacionesActivity extends AppCompatActivity {

    private Spinner spinnerAveria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reparaciones);

        fetchAverias();

        spinnerAveria = (Spinner) findViewById(R.id.spinnerAveria);
    }

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

        }
    }

    private void populateAverias(Response<ArrayList<Averia>> response) {
        ArrayList<Averia> averias = response.body();
        List<String> list = new ArrayList<String>();
        for (Averia a: averias) {
            list.add(a.getAveria());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAveria.setAdapter(spinnerArrayAdapter);

    }


}