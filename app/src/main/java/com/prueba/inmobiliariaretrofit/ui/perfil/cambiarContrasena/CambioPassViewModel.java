package com.prueba.inmobiliariaretrofit.ui.perfil.cambiarContrasena;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.prueba.inmobiliariaretrofit.request.ApiClient;

import modelos.PropietarioModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioPassViewModel extends AndroidViewModel {

    public CambioPassViewModel(@NonNull Application application) {
        super(application);
    }


    public void cambiarContrasena(String pass1,String pass2,String actual){
        if (pass1.isEmpty() || pass2.isEmpty() || actual.isEmpty()){
            return;
        }else if (!pass1.equals(pass2)) {
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();
        Call<Void> call = api.cambiarContrasena("Bearer " + token,actual,pass1);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplication(), "ACTUALIZADO CON ÉXITO", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplication(), "ERROR AL ACTUALIZAR: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("respuesta", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getApplication(), "ERROR EN LA API: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}