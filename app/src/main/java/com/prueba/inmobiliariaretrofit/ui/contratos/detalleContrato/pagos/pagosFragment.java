package com.prueba.inmobiliariaretrofit.ui.contratos.detalleContrato.pagos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentListaPagosBinding;
import com.prueba.inmobiliariaretrofit.ui.inmuebles.InmuebleAdapter;

import java.util.Collections;
import java.util.List;

import modelos.InmuebleModel;
import modelos.PagoModel;

public class pagosFragment extends Fragment {

    private PagosViewModel vm;
    private FragmentListaPagosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListaPagosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PagosViewModel.class);

        vm.getMpagos().observe(getViewLifecycleOwner(), new Observer<List<PagoModel>>() {
            @Override
            public void onChanged(List<PagoModel> p) {
                pagoAdapter adapter = new pagoAdapter(p,getContext(),getLayoutInflater());
                GridLayoutManager glm=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                Log.d("bugstatic", "onChanged: entro");
                binding.recyvlerViewPagos.setLayoutManager(glm);
                binding.recyvlerViewPagos.setAdapter(adapter);
            }
        });
        Bundle bundle = getArguments();
        int contratoId= bundle.getInt("contrato");
        Log.d("bugstatic", "ID de contrato recibido: " + contratoId);
        vm.cargarPagos(contratoId);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}