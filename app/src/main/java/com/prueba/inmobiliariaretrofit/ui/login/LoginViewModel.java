package com.prueba.inmobiliariaretrofit.ui.login;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prueba.inmobiliariaretrofit.MainActivity;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import java.util.List;

import modelos.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<UserModel> mutableUser;
    private MutableLiveData<String> mutableError;

    private SensorManager manager;
    private List<Sensor> sensores;
    ManejaEventos maneja;

    private float accelerationCurrentValue = SensorManager.GRAVITY_EARTH;
    private float accelerationLastValue = SensorManager.GRAVITY_EARTH;
    private float shakeThreshold = 12f;
    private boolean isShaking = false;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<UserModel> getMutableUser(){
        if (mutableUser==null){
            mutableUser=new MutableLiveData<>();
        }

        return mutableUser;
    }

    public LiveData<String> getMutableError(){
        if (mutableError==null){
            mutableError=new MutableLiveData<>();
        }

        return mutableError;
    }

    public void validarUsuario(String email, String password){

        if (email.isEmpty() || password.isEmpty()) {

            mutableError.setValue("Todos los campos son obligatorios");

            return;

        }


       ApiClient.InmoService llamadas= ApiClient.getInmoService();

      Call<String> respuesta=llamadas.loginForm(email,password);
      respuesta.enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
              if (response.isSuccessful()){
                    String token=response.body();
                  Log.d("token",token);
                  ApiClient.guardarToken(getApplication(),token);

                  Intent intent = new Intent(getApplication(), MainActivity.class);

                  intent.putExtra("User", "KUS");

                  intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                  getApplication().startActivity(intent);

              }else{
                  Toast.makeText(getApplication(),"Error"+response.code(),Toast.LENGTH_LONG).show();
              }
          }

          @Override
          public void onFailure(Call<String> call, Throwable throwable) {
              Toast.makeText(getApplication(),"Error"+throwable.getMessage(),Toast.LENGTH_LONG).show();

          }
      });
    }


    public void resetearContraseña(String email){
        if (email.isEmpty()) {
            Toast.makeText(getApplication(),"Por favor ingrese su mail para resetear la contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        ApiClient.InmoService llamadas= ApiClient.getInmoService();
        Call<String> respuesta=llamadas.resetearContrasenia(email);

        respuesta.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplication(),"se ah enviado un mail",Toast.LENGTH_LONG).show();
                }else{
                Toast.makeText(getApplication(),response.message(),Toast.LENGTH_LONG).show();}

            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(getApplication(),throwable.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

        public void activaEscucha(){
            manager=(SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                sensores=manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            }
            if (sensores.size()>0){
                maneja=new ManejaEventos();
                manager.registerListener(maneja,sensores.get(0),SensorManager.SENSOR_DELAY_UI);
            }
        }

        public void desactivaEscucha(){
            manager.unregisterListener(maneja);
        }



        private class ManejaEventos implements SensorEventListener {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    accelerationLastValue = accelerationCurrentValue;
                    accelerationCurrentValue = (float) Math.sqrt((x * x + y * y + z * z));
                    float delta = accelerationCurrentValue - accelerationLastValue;

                    if (delta > shakeThreshold && !isShaking) {
                        isShaking = true;
                        llamarInmobiliaria();
                    }

                }

            }

            private void llamarInmobiliaria() {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:2664553747"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplication(), "No hay permiso para llamadas", Toast.LENGTH_SHORT).show();
                    isShaking = false;
                    return;
                }

                getApplication().startActivity(intent);
                Toast.makeText(getApplication(), "Llamando a la inmobiliaria...", Toast.LENGTH_SHORT).show();

                // evita múltiples llamadas seguidas
                //new android.os.Handler().postDelayed(() -> isShaking = false, 3000);
            }
    }

}