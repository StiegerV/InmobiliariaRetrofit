package com.prueba.inmobiliariaretrofit.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentInmueblesBinding;
import com.prueba.inmobiliariaretrofit.databinding.FragmentInquilinoBinding;
import com.prueba.inmobiliariaretrofit.ui.inmuebles.InmuebleAdapter;
import com.prueba.inmobiliariaretrofit.ui.inmuebles.InmueblesViewModel;

import java.util.List;

import modelos.InmuebleModel;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel vm;
    private FragmentInquilinoBinding binding;

    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(InquilinoViewModel.class);
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm.getListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<InmuebleModel>>() {
            @Override
            public void onChanged(List<InmuebleModel> inmuebles) {
                InquilinoAdapter adapter = new InquilinoAdapter(inmuebles,getContext(),getLayoutInflater());
                GridLayoutManager glm=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);

                binding.recyvlerViewInquilino.setLayoutManager(glm);
                binding.recyvlerViewInquilino.setAdapter(adapter);
            }
        });
        vm.obtenerListaInmuebles();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(InquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}