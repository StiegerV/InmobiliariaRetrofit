package com.prueba.inmobiliariaretrofit.ui.inquilinos.detalleInquilino;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prueba.inmobiliariaretrofit.request.ApiClient;

import java.util.List;

import modelos.ContratoModel;
import modelos.InmuebleModel;
import modelos.InquilinoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<InquilinoModel> mInquilino;
    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<InquilinoModel> getMInquilino(){
        if (mInquilino==null){
            mInquilino=new MutableLiveData<>();
        }
        return mInquilino;
    }


    public void obtenerContrato(int inmuebleId){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();
        Call<ContratoModel> call = api.obtenerContratoPorInmueble("Bearer "+ token,inmuebleId);

        call.enqueue(new Callback<ContratoModel>() {
            @Override
            public void onResponse(Call<ContratoModel> call, Response<ContratoModel> response) {
                if (response.isSuccessful()){

                    ContratoModel con= response.body();
                    Log.d("contrato",con.toString());
                    mInquilino.setValue(con.getInquilino());
                }else {
                    Toast.makeText(getApplication(),"no se obtuvieron inmuebles",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ContratoModel> call, Throwable throwable) {
                Log.d("errorInmueble",throwable.getMessage());

                Toast.makeText(getApplication(),"Error al obtener contratos",Toast.LENGTH_LONG).show();
            }
        });
    }


}