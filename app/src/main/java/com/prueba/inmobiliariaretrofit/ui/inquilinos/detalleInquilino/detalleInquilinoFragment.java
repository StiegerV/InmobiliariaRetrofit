package com.prueba.inmobiliariaretrofit.ui.inquilinos.detalleInquilino;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.inmobiliariaretrofit.databinding.FragmentDetalleInquilinoBinding;

import modelos.InmuebleModel;
import modelos.InquilinoModel;

public class detalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel vm;
    private FragmentDetalleInquilinoBinding binding;

    private InmuebleModel inm;

    public static detalleInquilinoFragment newInstance() {
        return new detalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        binding= FragmentDetalleInquilinoBinding.inflate(inflater,container,false);
        Bundle bundle = getArguments();
       int inmuebleId= bundle.getInt("inmueble");
        View view= binding.getRoot();

        vm.obtenerContrato(inmuebleId);


        vm.getMInquilino().observe(getViewLifecycleOwner(), new Observer<InquilinoModel>() {
            @Override
            public void onChanged(InquilinoModel i) {
                binding.etApellido.setText(i.getApellido());
                binding.etDni.setText(i.getDni());
                binding.etEmail.setText(i.getEmail());
                binding.etNombre.setText(i.getNombre());
                binding.etDni.setText(i.getDni());
                binding.etTelefono.setText(i.getTelefono());

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}