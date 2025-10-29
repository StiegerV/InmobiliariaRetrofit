package com.prueba.inmobiliariaretrofit.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prueba.inmobiliariaretrofit.request.ApiClient;

import modelos.PropietarioModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado=new MutableLiveData<>();

    private MutableLiveData<PropietarioModel> propietario=new MutableLiveData<>();
    private MutableLiveData<String> mNombre=new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getEstado(){
        return mEstado;
    }

    public LiveData<String> getNombre(){
        return mNombre;
    }

    public LiveData<PropietarioModel> getPropietario(){
        return propietario;
    }

    public void cambioBoton(String nombreboton, String nombre, String apellido, String dni, String telefono, String email) {
        if (nombreboton.equalsIgnoreCase("EDITAR")) {
            mEstado.setValue(true);
            mNombre.setValue("GUARDAR");
        } else {

            String error = validarCampos(nombre, apellido, dni, telefono, email);
            if (error != null) {
                Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
                return;
            }

            mEstado.setValue(false);
            mNombre.setValue("EDITAR");

            PropietarioModel actualizado = new PropietarioModel();
            actualizado.setIdPropietario(propietario.getValue().getIdPropietario());
            actualizado.setNombre(nombre.trim());
            actualizado.setApellido(apellido.trim());
            actualizado.setDni(dni.trim());
            actualizado.setTelefono(telefono.trim());
            actualizado.setEmail(email.trim());

            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoService api = ApiClient.getInmoService();
            Call<PropietarioModel> call = api.actualizarProp("Bearer " + token, actualizado);

            call.enqueue(new Callback<PropietarioModel>() {
                @Override
                public void onResponse(Call<PropietarioModel> call, Response<PropietarioModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "ACTUALIZADO CON ÉXITO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "ERROR AL ACTUALIZAR: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.d("respuesta", response.toString());
                        Log.d("token", token);
                    }
                }

                @Override
                public void onFailure(Call<PropietarioModel> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "ERROR EN LA API: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("api_error", throwable.getMessage(), throwable);
                }
            });
        }
    }
    public void obtenerPerfil(){

        String token = ApiClient.leerToken(getApplication());

                ApiClient.InmoService api=ApiClient.getInmoService();
               Call<PropietarioModel> call=api.getPropietario("Bearer "+token);

        call.enqueue(new Callback<PropietarioModel>() {
            @Override
            public void onResponse(Call<PropietarioModel> call, Response<PropietarioModel> response) {
                if (response.isSuccessful()){
                    propietario.postValue(response.body());
                }else{
                    Toast.makeText(getApplication(),response.toString(),Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<PropietarioModel> call, Throwable throwable) {
                Toast.makeText(getApplication(),throwable.getMessage(),Toast.LENGTH_SHORT);
            }
        });


    }


    private String validarCampos(String nombre, String apellido, String dni, String telefono, String email) {
        if (nombre == null || nombre.trim().isEmpty())
            return "El nombre no puede estar vacío";


        if (apellido == null || apellido.trim().isEmpty())
            return "El apellido no puede estar vacío";


        if (dni == null || !dni.matches("^\\d{7,10}$"))
            return "El DNI debe tener solo números ";


        if (telefono == null || !telefono.matches("^[+\\d][\\d\\-\\s]{6,15}$"))
            return "El teléfono no es válido";


        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            return "El correo electrónico no es válido";

        return null;
    }


}