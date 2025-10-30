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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentListaPagosBinding;
import com.prueba.inmobiliariaretrofit.ui.inmuebles.InmuebleAdapter;

import java.util.Collections;
import java.util.List;

import modelos.InmuebleModel;

public class pagosFragment extends Fragment {

    private PagosViewModel vm;
    private FragmentListaPagosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListaPagosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PagosViewModel.class);

        vm.getMpagos().observe(getViewLifecycleOwner(), new Observer<List<InmuebleModel>>() {
            @Override
            public void onChanged(List<InmuebleModel> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles,getContext(),getLayoutInflater());
                GridLayoutManager glm=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);

                binding.recyvlerViewPagos.setLayoutManager(glm);
                binding.recyvlerViewPagos.setAdapter(adapter);
            }
        });
       // vm.cargarPagos();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}