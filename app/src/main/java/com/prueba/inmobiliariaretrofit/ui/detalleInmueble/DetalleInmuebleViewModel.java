package com.prueba.inmobiliariaretrofit.ui.detalleInmueble;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentDetalleInmuebleBinding;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import modelos.InmuebleModel;
import modelos.PropietarioModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<InmuebleModel> mInmueble;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<InmuebleModel> getMInmueble(){
        if (mInmueble==null){
            mInmueble=new MutableLiveData<>();
        }
        return mInmueble;
    }

    public void recuperarInmueble(Bundle bundle){
        InmuebleModel inmueble=(InmuebleModel) bundle.get("inmueble");
        if (inmueble!=null){
            mInmueble.setValue(inmueble);
        }
    }

    public void actualizarInmueble(InmuebleModel inmueble) {
        ApiClient.InmoService api = ApiClient.getInmoService();
        String token = "Bearer " + ApiClient.leerToken(getApplication());
        Log.d("inmueble",inmueble.toString());

        Call<InmuebleModel> call = api.actualizarInmueble(token, inmueble);

        call.enqueue(new Callback<InmuebleModel>() {
            @Override
            public void onResponse(Call<InmuebleModel> call, Response<InmuebleModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente", Toast.LENGTH_SHORT).show();
                    Log.d("API", "Inmueble actualizado: " + response.body());
                } else {
                    Log.e("API", "Error al actualizar: " + response.code() + " - " + response.message());
                    Toast.makeText(getApplication(), "Error al actualizar (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InmuebleModel> call, Throwable t) {
                Log.e("API", "Error al hacer la llamada", t);
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void buildInmueble(Context c, FragmentDetalleInmuebleBinding binding, InmuebleModel inmueble){
        try{
        binding.etCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
        binding.etDireccion.setText(inmueble.getDireccion());
        binding.etUso.setText(inmueble.getUso());
        binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
        binding.etPrecio.setText(String.valueOf(inmueble.getValor()));
        binding.etTipo.setText(inmueble.getTipo());
        binding.cbDisponible.setChecked(inmueble.isDisponible());


        Glide.with(c)
                .load("https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/" + inmueble.getImagen())
                .placeholder(null)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivFoto);
    }catch (Exception e){
            Log.d("Excepcion", "error glide");
        }
    }

}