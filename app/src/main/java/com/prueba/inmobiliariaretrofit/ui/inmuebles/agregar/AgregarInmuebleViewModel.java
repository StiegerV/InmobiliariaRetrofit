package com.prueba.inmobiliariaretrofit.ui.inmuebles.agregar;



import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import modelos.InmuebleModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;
import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


public class AgregarInmuebleViewModel extends AndroidViewModel {

    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<InmuebleModel> mInmueble;
    private static InmuebleModel inmueblelleno;

    public MutableLiveData<InmuebleModel> navegarADetalle=new MutableLiveData<>();

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;

    }

    public LiveData<InmuebleModel> getNavegarADetalle(){
        return navegarADetalle;
    }

    public LiveData<InmuebleModel> getmInmueble() {
        if (mInmueble == null) {
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            uriMutableLiveData.setValue(uri);
        }
    }

    private byte[] transformarImagen() {
        try {
            Uri uri = uriMutableLiveData.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (
                FileNotFoundException er) {
            Toast.makeText(getApplication(), "No ha seleccinado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }

    }

    public void guardarInmueble(String direccion, String uso, String tipo, String precio, String ambientes, String superficie) {
        try {

            byte[] imagen=transformarImagen();
            validarCampos(direccion,uso,tipo, ambientes,superficie,precio,imagen);


            int amb = Integer.parseInt(ambientes);
            int superf = Integer.parseInt(superficie);
            double prec = Double.parseDouble(precio);
            InmuebleModel inmueble = new InmuebleModel();
            inmueble.setDireccion(direccion);
            inmueble.setUso(uso);
            inmueble.setTipo(tipo);
            inmueble.setValor(prec);
            inmueble.setSuperficie(superf);
            inmueble.setAmbientes(amb);

            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
            ApiClient.InmoService api = ApiClient.getInmoService();
            String token=ApiClient.leerToken(getApplication());
            Call<InmuebleModel> llamada=api.cargarInmueble("Bearer " + token, imagenPart, inmuebleBody);
            llamada.enqueue(new Callback<InmuebleModel>() {
                @Override
                public void onResponse(Call<InmuebleModel> call, Response<InmuebleModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble guardado correctamente", Toast.LENGTH_SHORT).show();

                        InmuebleModel inmueble = response.body();
                        navegarADetalle.setValue(inmueble);
                    }
                }

                @Override
                public void onFailure(Call<InmuebleModel> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error al guardar inmueble", Toast.LENGTH_SHORT).show();
                }
            });



        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Error, debe ingresar un numero", Toast.LENGTH_SHORT).show();

        }


    }


    private boolean validarCampos(String direccion, String uso, String tipo,
                                  String ambientes, String superficie, String precio,
                                  byte[] imagen) {

        if (direccion == null || direccion.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Debe ingresar una dirección", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (uso == null || uso.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Debe seleccionar el uso", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Debe seleccionar el tipo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ambientes == null || ambientes.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Debe ingresar la cantidad de ambientes", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (superficie == null || superficie.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Debe ingresar la superficie", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (precio == null || precio.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Debe ingresar el precio", Toast.LENGTH_SHORT).show();
            return false;
        }


        try {
            int amb = Integer.parseInt(ambientes);
            if (amb <= 0) {
                Toast.makeText(getApplication(), "Los ambientes deben ser mayores a 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Los ambientes deben ser un número válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int superf = Integer.parseInt(superficie);
            if (superf <= 0) {
                Toast.makeText(getApplication(), "La superficie debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "La superficie debe ser un número válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double prec = Double.parseDouble(precio);
            if (prec <= 0) {
                Toast.makeText(getApplication(), "El precio debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "El precio debe ser un número válido", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (imagen == null || imagen.length == 0) {
            Toast.makeText(getApplication(), "Debe ingresar una imagen", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void limpiarNavegacion(){
        navegarADetalle=new MutableLiveData<>();
    }
}