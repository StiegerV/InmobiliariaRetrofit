package com.prueba.inmobiliariaretrofit.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.navigation.NavigationView;
import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentHomeBinding;

import modelos.PropietarioModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentHomeBinding.inflate(inflater, container, false);
         vm=new ViewModelProvider(this).get(HomeViewModel.class);

        vm.getMapa().observe(getViewLifecycleOwner(), new Observer<HomeViewModel.Mapa>() {
            @Override
            public void onChanged(HomeViewModel.Mapa mapaActual) {
                ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapaActual);
            }
        });

        //ya que es la primera vista a la que entra cuando esta logeado vamos a setear el nav drawer con los datos del usr aca
        vm.getPropietario().observe(getViewLifecycleOwner(), new Observer<PropietarioModel>() {
            @Override
            public void onChanged(PropietarioModel propietarioModel) {
                NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

                    View headerView = navigationView.getHeaderView(0);
                    Log.d("propietario",propietarioModel.toString());
                    TextView title = headerView.findViewById(R.id.titulo);
                    TextView subtitle = headerView.findViewById(R.id.subtitulo);
                    ImageView image = headerView.findViewById(R.id.imageView);
                    title.setText(propietarioModel.getNombre());
                    subtitle.setText(propietarioModel.getEmail());
                   // image.setImageResource(R.drawable.profe);
            }
        });


        vm.obtenerPerfil();
        vm.obtenerMapas();
        return binding.getRoot();
    }


}