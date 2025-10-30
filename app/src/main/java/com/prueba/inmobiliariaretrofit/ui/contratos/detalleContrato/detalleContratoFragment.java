package com.prueba.inmobiliariaretrofit.ui.contratos.detalleContrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.inmobiliariaretrofit.databinding.FragmentContratosBinding;
import com.prueba.inmobiliariaretrofit.databinding.FragmentDetalleContratoBinding;
import com.prueba.inmobiliariaretrofit.databinding.FragmentDetalleInquilinoBinding;
import com.prueba.inmobiliariaretrofit.ui.contratos.ContratosViewModel;
import com.prueba.inmobiliariaretrofit.ui.inquilinos.ContratosAdapter;

import java.util.List;

import modelos.ContratoModel;
import modelos.InmuebleModel;
import modelos.InquilinoModel;
import modelos.PagoModel;

public class detalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;


    public static detalleContratoFragment newInstance() {
        return new detalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm.getMPagos().observe(getViewLifecycleOwner(), new Observer<ContratoModel>() {
            @Override
            public void onChanged(ContratoModel c) {
                binding.tvFecha.setText(c.getFechaInicio());
                binding.tvFechafin.setText(c.getFechaFinalizacion());
                binding.tvId.setText(c.getIdContrato()+"");
                binding.tvMontoTrato.setText(c.getMontoAlquiler()+"");
                binding.tvInquilino.setText(c.getInquilino().getNombre()+" "+c.getInquilino().getApellido());
                binding.tvInmueble.setText(c.getInmueble().getDireccion());
            }
        });
        Bundle bundle = getArguments();
        int inmuebleId= bundle.getInt("inmueble");
        vm.obtenerContrato(inmuebleId);

        binding.btnPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}