package com.prueba.inmobiliariaretrofit.ui.detalleInmueble;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentDetalleInmuebleBinding;

import modelos.InmuebleModel;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;

    private InmuebleModel inmueble;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding=FragmentDetalleInmuebleBinding.inflate(inflater,container,false);
        Bundle bundle = getArguments();
        inmueble=(InmuebleModel) bundle.getSerializable("inmueble");
        View view= binding.getRoot();

        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<InmuebleModel>() {
            @Override
            public void onChanged(InmuebleModel inmueble) {
                mv.buildInmueble(getContext(),binding,inmueble);
            }
        });


        binding.btInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inmueble.setDisponible(binding.cbDisponible.isChecked());
                mv.actualizarInmueble(inmueble);
            }
        });

        mv.recuperarInmueble(getArguments());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}