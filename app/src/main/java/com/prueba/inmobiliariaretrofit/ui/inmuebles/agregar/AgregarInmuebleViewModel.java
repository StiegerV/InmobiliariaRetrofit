package com.prueba.inmobiliariaretrofit.ui.inmuebles.agregar;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentAgregarInmuebleBinding;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import java.io.File;

import modelos.InmuebleModel;
import retrofit2.Call;
import okhttp3.MultipartBody.Part;

import com.prueba.inmobiliariaretrofit.ui.detalleInmueble.DetalleInmuebleFragment;
import com.prueba.inmobiliariaretrofit.utils.utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<InmuebleModel> InmuebleMutable = new MutableLiveData<>();
    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<InmuebleModel> getInmueble() {
        return InmuebleMutable;
    }

    public void guardarInmueble(InmuebleModel inmueble, Uri imagenUri) {


        boolean valido = true;
        if (inmueble==null) valido=false;
        if (inmueble.getDireccion() == null || inmueble.getDireccion().isEmpty()) valido = false;
        if (inmueble.getTipo() == null || inmueble.getTipo().isEmpty()) valido = false;
        if (inmueble.getUso() == null || inmueble.getUso().isEmpty()) valido = false;
        if (inmueble.getValor() <= 0) valido = false;
        if (inmueble.getSuperficie() <= 0) valido = false;
        if (imagenUri == null) valido = false;

        if (!valido) {
            Toast.makeText(getApplication(), "Complete todos los campos correctamente", Toast.LENGTH_SHORT).show();
            return;
        }


        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();




        Gson gson = new Gson();
        String inmuebleJson = gson.toJson(inmueble);
        MediaType jsonMediaType = MediaType.get("application/json");
        RequestBody inmuebleRequest = RequestBody.create(jsonMediaType, inmuebleJson);


        Part imagenPart = null;
        if (imagenUri!=null){
        File file = new File(utils.getPath(getApplication(), imagenUri));
        MediaType imageMediaType = MediaType.get("image/*");
        RequestBody requestFile = RequestBody.create(imageMediaType, file);
         imagenPart = Part.createFormData("imagen", file.getName(), requestFile);}


        Call<InmuebleModel> call = api.cargarInmueble("Bearer " + token, imagenPart, inmuebleRequest);
        call.enqueue(new Callback<InmuebleModel>() {
            @Override
            public void onResponse(Call<InmuebleModel> call, Response<InmuebleModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplication(), "Inmueble cargado con éxito", Toast.LENGTH_SHORT).show();
                    InmuebleModel inmuebleCreado= response.body();
                    InmuebleMutable.setValue(inmuebleCreado);


                } else {
                    Toast.makeText(getApplication(), "Error al cargar inmueble: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InmuebleModel> call, Throwable t) {
                Toast.makeText(getApplication(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public InmuebleModel validarInmueble(FragmentAgregarInmuebleBinding binding){
        InmuebleModel nuevo = new InmuebleModel();
        nuevo.setDireccion(binding.etDireccion.getText().toString());
        nuevo.setTipo(binding.etTipo.getText().toString());
        nuevo.setUso(binding.etUso.getText().toString());



        try {
            nuevo.setAmbientes(Integer.parseInt(binding.etAmbientes.getText().toString()));
            nuevo.setValor(Double.parseDouble(binding.etValor.getText().toString()));
            nuevo.setSuperficie(Integer.parseInt(binding.etSuperficie.getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Ingrese números válidos", Toast.LENGTH_SHORT).show();
            return null;
        }

        return nuevo;
    }
}