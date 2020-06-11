package com.ande.reclamos.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ande.reclamos.R;
import com.ande.reclamos.io.MyApiAdapter;
import com.ande.reclamos.model.Movil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback {

    private EditText edtNumeroMovil;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNumeroMovil = (EditText) findViewById(R.id.edtNumeroMovil);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String numeroMovil = edtNumeroMovil.getText().toString();
                // validar formulario
                if(validateLogin(numeroMovil)){
                    //logear
                    doLogin(numeroMovil);
                }
            }
        });
    }

    private boolean validateLogin(String numeroMovil) {
        if(numeroMovil == null || numeroMovil.trim().length() == 0) {
            Toast.makeText(this, "Número de móvil es requerido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(String numeroMovil) {
        btnLogin.setEnabled(false);
        Call<Movil> call = MyApiAdapter.getApiService().login(numeroMovil);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful()) {
            final Movil movil = (Movil) response.body();
            Log.d("onResponse movil", "numero de movil  => " + movil.getNumero());
            Toast.makeText(this, "Sesión correcta. Filtrando reclamos del móvil " + movil.getNumero(), Toast.LENGTH_SHORT).show();
            login(movil);
        } else {
            Toast.makeText(this, "Los datos ingresados no coinciden con ningún usuario", Toast.LENGTH_SHORT).show();
        }
        btnLogin.setEnabled(true);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    private void login(Movil movil) {
        Intent intent = new Intent(LoginActivity.this, ReclamosActivity.class);
        intent.putExtra("movil_id", movil.getId());
        intent.putExtra("movil_numero", movil.getNumero());
        startActivity(intent);
    }
}
