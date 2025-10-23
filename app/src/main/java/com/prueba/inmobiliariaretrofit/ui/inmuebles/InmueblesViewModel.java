package com.prueba.inmobiliariaretrofit.ui.inmuebles;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import modelos.InmuebleModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.prueba.inmobiliariaretrofit.request.ApiClient;

public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<List<InmuebleModel>> listaInmuebles = new MutableLiveData<>();
    public InmueblesViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<InmuebleModel>> getListaInmuebles(){
        return listaInmuebles;
    }



    public void obtenerListaInmuebles(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();
        Call <List<InmuebleModel>> call = api.getListaInmuebles("Bearer "+ token);

        call.enqueue(new Callback<List<InmuebleModel>>() {
            @Override
            public void onResponse(Call<List<InmuebleModel>> call, Response<List<InmuebleModel>> response) {
                if (response.isSuccessful()){
                    listaInmuebles.postValue(response.body());
                }else {
                    Toast.makeText(getApplication(),"no se obtuvieron Inmuebles",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<InmuebleModel>> call, Throwable throwable) {
                Log.d("errorInmueble",throwable.getMessage());

                Toast.makeText(getApplication(),"Error al obtener Inmuebles",Toast.LENGTH_LONG).show();
            }
        });
    }
}