package com.prueba.inmobiliariaretrofit.ui.login;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prueba.inmobiliariaretrofit.MainActivity;

import Modelos.UserModel;

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



        if ( email.equals("tuqi") && password.equals("123")) {

            UserModel usuario = new UserModel();

            usuario.setEmail(email);

            usuario.setPassword(password);

            usuario.setNombre("tuqi");

            //usuario.setFoto(R.drawable.ic_menu_camera);



            Intent intent = new Intent(getApplication(), MainActivity.class);

            intent.putExtra("User", usuario);

            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

            getApplication().startActivity(intent);

        }else{
            mutableError.setValue("Usuario o contraseña incorrecto");

            return;
        }

    }


}
