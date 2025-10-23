package com.prueba.inmobiliariaretrofit.ui.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prueba.inmobiliariaretrofit.MainActivity;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import modelos.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<UserModel> mutableUser;
    private MutableLiveData<String> mutableError;

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


}
