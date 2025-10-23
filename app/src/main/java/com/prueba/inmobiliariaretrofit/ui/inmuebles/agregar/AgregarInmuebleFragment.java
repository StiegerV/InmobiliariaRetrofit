package com.prueba.inmobiliariaretrofit.ui.inmuebles.agregar;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentAgregarInmuebleBinding;
import com.prueba.inmobiliariaretrofit.ui.perfil.PerfilViewModel;
import com.prueba.inmobiliariaretrofit.utils.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import modelos.InmuebleModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AgregarInmuebleFragment extends Fragment {

    private AgregarInmuebleViewModel mViewModel;
    private FragmentAgregarInmuebleBinding binding;

    private Uri imagenUri;

    private  ActivityResultLauncher<String> imagePickerLauncher;

    public static AgregarInmuebleFragment newInstance() {
        return new AgregarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel=new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                    if (uri != null) {
                        imagenUri = uri;
                    }
                });


        //para abrir galeria
        binding.btnCargarImg.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));


        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InmuebleModel nuevo = new InmuebleModel();
                nuevo.setDireccion(binding.etDireccion.getText().toString());
                nuevo.setTipo(binding.etTipo.getText().toString());
                nuevo.setUso(binding.etUso.getText().toString());


                try {
                    nuevo.setValor(Double.parseDouble(binding.etValor.getText().toString()));
                    nuevo.setSuperficie(Integer.parseInt(binding.etSuperficie.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Ingrese números válidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                mViewModel.guardarInmueble(nuevo,imagenUri);
            }
        });

        mViewModel.getInmueble().observe(getViewLifecycleOwner(), new Observer<InmuebleModel>() {
            @Override
            public void onChanged(InmuebleModel inmuebleModel) {
                Bundle bundle = new Bundle();
                    bundle.putSerializable("inmueble", inmuebleModel);

                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_nav_agregarInmueble_to_detalleInmuebleFragment, bundle);
            }
        });

        return root;
    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
    }


}