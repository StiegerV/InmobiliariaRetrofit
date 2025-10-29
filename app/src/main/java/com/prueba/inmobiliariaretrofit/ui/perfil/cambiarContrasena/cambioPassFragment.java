package com.prueba.inmobiliariaretrofit.ui.perfil.cambiarContrasena;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentCambioPassBinding;
import com.prueba.inmobiliariaretrofit.databinding.FragmentPerfilBinding;
import com.prueba.inmobiliariaretrofit.ui.perfil.PerfilViewModel;

public class cambioPassFragment extends Fragment {
    private FragmentCambioPassBinding binding;
    private CambioPassViewModel vm;

    public static cambioPassFragment newInstance() {
        return new cambioPassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm=new ViewModelProvider(this).get(CambioPassViewModel.class);

        binding = FragmentCambioPassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1=binding.etContrasena.getText().toString();
                String pass2=binding.etRepetir.getText().toString();
                String actual=binding.etContrasenaActual.getText().toString();
                vm.cambiarContrasena(pass1,pass2,actual);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(CambioPassViewModel.class);
        // TODO: Use the ViewModel
    }

}