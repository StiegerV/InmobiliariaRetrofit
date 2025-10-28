package com.prueba.inmobiliariaretrofit.ui.inmuebles.agregar;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.provider.MediaStore;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentAgregarInmuebleBinding;

import modelos.InmuebleModel;

public class AgregarInmuebleFragment extends Fragment {

    private AgregarInmuebleViewModel mViewModel;
    private FragmentAgregarInmuebleBinding binding;

    private Uri imagenUri;

    private Intent intent;
    private ActivityResultLauncher<Intent> arl;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel=new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        abrirGaleria();
        binding.btnCargarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarInmueble();
            }
        });

        mViewModel.getUriMutable().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.imgPreview.setImageURI(uri);
            }
        });

        mViewModel.getNavegarADetalle().observe(getViewLifecycleOwner(), new Observer<InmuebleModel>() {
            @Override
            public void onChanged(InmuebleModel inmuebleModel) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmuebleModel);

                NavController navController = NavHostFragment.findNavController(AgregarInmuebleFragment.this);
                navController.navigate(R.id.action_nav_agregarInmueble_to_detalleInmuebleFragment, bundle);
                mViewModel.limpiarNavegacion();
            }
        });

        return binding.getRoot();
    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
    }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mViewModel.recibirFoto(result);
            }
        });
    }

    private void cargarInmueble(){
        String direccion=binding.etDireccion.getText().toString();
        String uso=binding.etUso.getText().toString();
        String tipo=binding.etTipo.getText().toString();
        double latitud=0;
        double longitud=0;
        String precio=binding.etValor.getText().toString();
        String ambientes= binding.etAmbientes.getText().toString();
        String superficie=binding.etSuperficie.getText().toString();
        mViewModel.guardarInmueble(direccion, uso, tipo, precio, ambientes, superficie);
    }

}