package com.prueba.inmobiliariaretrofit.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.prueba.inmobiliariaretrofit.databinding.ActivityLoginBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());



        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=binding.etEmail.getText().toString();
                String pass=binding.etPassword.getText().toString();
                viewModel.validarUsuario(mail,pass);
            }
        });

        binding.btResetear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=binding.etEmail.getText().toString();
                viewModel.resetearContraseña(mail);
            }
        });
        binding.etEmail.setText("luisprofessor@gmail.com");
        binding.etPassword.setText("DEEKQW");


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.activaEscucha();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.desactivaEscucha();
    }

}
