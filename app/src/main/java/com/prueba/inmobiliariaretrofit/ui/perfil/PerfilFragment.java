package com.prueba.inmobiliariaretrofit.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentPerfilBinding;
import com.prueba.inmobiliariaretrofit.ui.inmuebles.agregar.AgregarInmuebleFragment;

import modelos.PropietarioModel;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        vm=new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etDni.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
                binding.etEmail.setEnabled(aBoolean);
            }
        });
        vm.getNombre().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btPerfil.setText(s);
            }
        });

        binding.btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cambioBoton(binding.btPerfil.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etDni.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etEmail.getText().toString());
            }
        });

        binding.btContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = NavHostFragment.findNavController(PerfilFragment.this);
                navController.navigate(R.id.action_nav_perfil_to_cambioPassFragment);
            }
        });

        vm.getPropietario().observe(getViewLifecycleOwner(), new Observer<PropietarioModel>() {
            @Override
            public void onChanged(PropietarioModel p) {
                binding.etNombre.setText(p.getNombre());
                binding.etApellido.setText(p.getApellido());
                binding.etDni.setText(p.getDni());
                binding.etTelefono.setText(p.getTelefono());
                binding.etEmail.setText(p.getEmail());
            }
        });

        vm.obtenerPerfil();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}