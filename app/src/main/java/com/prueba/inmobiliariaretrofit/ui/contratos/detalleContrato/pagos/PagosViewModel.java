package com.prueba.inmobiliariaretrofit.ui.contratos.detalleContrato.pagos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prueba.inmobiliariaretrofit.request.ApiClient;
import java.util.List;

import modelos.InmuebleModel;
import modelos.PagoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<List<PagoModel>> Mpagos ;

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PagoModel>> getMpagos(){
        if (Mpagos==null){
            Mpagos=new MutableLiveData<>();
        }
        return Mpagos;
    }



    public void cargarPagos(int idContrato) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();
        Call<List<PagoModel>> call = api.obtenerPagosPorContrato("Bearer " + token, idContrato);
        call.enqueue(new Callback<List<PagoModel>>() {
            @Override
            public void onResponse(Call<List<PagoModel>> call, Response<List<PagoModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Mpagos.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<PagoModel>> call, Throwable t) { }
        });
    }
}